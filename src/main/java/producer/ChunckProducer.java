package producer;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

/**
 * Created by King on 1/23/2015.
 */
public class ChunckProducer {

    public static void main(String[] args) throws IOException
    {
    Properties apiConfig=new Properties();
    apiConfig.load(new FileInputStream("api.properties"));

    int kafka_port=Integer.valueOf(apiConfig.getProperty("Kafka_port"));
    int Zookeeper_port=Integer.valueOf(apiConfig.getProperty("Zookeeper_port"));
        String topic=apiConfig.getProperty("Kafka_topic");

    Properties props = new Properties();
    props.put("zk.connect","localhost:"+Zookeeper_port);
    props.put("metadata.broker.list","localhost:"+kafka_port);
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("request.required.acks", "1");
   // props.put("compression.codec", "gzip");

    ProducerConfig config = new ProducerConfig(props);
    //Producer<String, byte[]> producer = new Producer<String, byte[]>(config);
    Producer<String, String> producer = new Producer<String, String>(config);
    KeyedMessage<String, String> keymesaage = null;

    AmazonS3Client client = new AmazonS3Client();
    Bucket bucket = new Bucket("logsdatatest");
    //Bucket bucket = new Bucket("testkafkaforlogsize");
    System.out.println(" - " + bucket.getName());

    ObjectListing objects = client.listObjects(bucket.getName());

    do {
        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
            System.out.println(objectSummary.getKey() + "\t" +
                    objectSummary.getSize() + "\t" +
                    StringUtils.fromDate(objectSummary.getLastModified()));

            GetObjectRequest req = new GetObjectRequest(bucket.getName(), objectSummary.getKey());
            try {
                byte[] byteArray = IOUtils.toByteArray(client.getObject(req).getObjectContent());

                GZIPInputStream gzis =new GZIPInputStream(new ByteArrayInputStream(byteArray));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int len;byte[] buffer = new byte[1024];

                while ((len = gzis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);

                }
                InputStream is = new ByteArrayInputStream(out.toByteArray());
                String content= org.apache.commons.io.IOUtils.toString(is);

                try {
                    //CSVReader r=new CSVReader(new StringReader(content),' ');
                    String[] contentArray=content.split("\n");

                    int i=1;
                    String message="";
                    int chunkSize=5;
                    int contentLength=contentArray.length;
                    System.out.println("$$$$$$$$$$$$$$  "+contentLength);
                    if(contentArray.length>chunkSize)
                    {
                        for(String tuple : contentArray)
                        {
                            if(i==1)
                            {
                                message=message.concat(tuple);
                            }else{
                                message=message.concat("\n").concat(tuple);
                            }

                            contentLength=contentLength-1;
                            if(i==chunkSize)
                            {
                                System.out.print(message);
                                keymesaage = new KeyedMessage(topic,message);
                                producer.send(keymesaage);
                                System.out.print("\n\n-----------------------\n\n");
                                message="";
                                i=0;
                            }else if(contentLength==0)
                            {

                                System.out.println("**********WITHIN CONTENT ");
                                System.out.print(message);

                                keymesaage = new KeyedMessage(topic,message);
                                producer.send(keymesaage);
                            }
                            i++;


                           // if(contentArray.length)

                        }

                    }else
                    {

                        keymesaage = new KeyedMessage(topic,content);
                        producer.send(keymesaage);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                //System.out.print(content);

                gzis.close();
                out.close();

               /* keymesaage = new KeyedMessage("logs12",byteArray);
                producer.send(keymesaage);*/
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }


        objects = client.listNextBatchOfObjects(objects);
    } while (objects.isTruncated());




}
}
