package Content;

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
import java.util.Properties;
import java.util.zip.GZIPInputStream;

/**
 * send zipped content as kafka  msg
 */
public class ContentZipProducer {

    public static void main(String[] args) throws IOException
    {
        Properties apiConfig=new Properties();
        apiConfig.load(new FileInputStream("api.properties"));

        int kafka_port=Integer.valueOf(apiConfig.getProperty("Kafka_port"));
        int Zookeeper_port=Integer.valueOf(apiConfig.getProperty("Zookeeper_port"));

        Properties props = new Properties();
        props.put("metadata.broker.list","localhost:"+Zookeeper_port);
        props.put("serializer.class", "kafka.serializer.DefaultEncoder");
        props.put("request.required.acks", "1");
        props.put("compression.codec", "gzip");

        //props.put("message.max.bytes", "" + 1024 * 1024 * 1024 * 1000);

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, byte[]> producer = new Producer<String, byte[]>(config);
        KeyedMessage<String, byte[]> keymesaage = null;

        AmazonS3Client client = new AmazonS3Client();
        Bucket bucket = new Bucket("testkafkaforlogsize");
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
//////////
                    /*val fileInTempDirInputStream1: FileInputStream = new FileInputStream(tempDirectory + "/" + fileName)

                    val gzip: GZIPInputStream = new GZIPInputStream(fileInTempDirInputStream1)

                    var length2: Int = 0
                    while ((({
                            length2 = gzip.read(bytes);
                    length2
                    })) >= 0) {
                        outStreamForFilePathInInput.write(bytes, 0, length2)
                    }
                    outStreamForFilePathInInput.close()
                    gzip.close()*/

                            ///
                   /* GZIPInputStream gzis =new GZIPInputStream(new ByteArrayInputStream(byteArray));
                    FileOutputStream out = new FileOutputStream("C:\\MyFile1.log");

                    int len;byte[] buffer = new byte[1024];
                    while ((len = gzis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }

                    gzis.close();
                    out.close();*/


                    keymesaage = new KeyedMessage("logs12",byteArray);
                    producer.send(keymesaage);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }


            objects = client.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());




    }
}
