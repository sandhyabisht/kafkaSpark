package producer;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by King on 1/20/2015.
 */
public class producer {

    public static void main(String[] args) {

        Properties props = new Properties();

//        props.put("metadata.broker.list", args[3]); // , 10.149.51.75:9092
        props.put("zk.connect","localhost:2181");
        props.put("metadata.broker.list","localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        props.put("request.required.acks", "1");

       // props.put("message.max.bytes", "" + 1024 * 1024 * 1024 * 100);

        ProducerConfig config = new ProducerConfig(props);

       // Producer<String, byte[]> producer = new Producer<String, byte[]>(config);
        Producer<String, String> producer = new Producer<String, String>(config);

        KeyedMessage<String, String> keymesaage = null;// = new KeyedMessage<String, String>("test", "test_msg");

        AmazonS3Client client = new AmazonS3Client();
        //println(s3)
        Bucket bucket = new Bucket("logsdatatest");
        //println("AWS Bucket")

        System.out.println(" - " + bucket.getName());

        ObjectListing objects = client.listObjects(bucket.getName());

        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println(objectSummary.getKey() + "\t" +
                        objectSummary.getSize() + "\t" +
                        StringUtils.fromDate(objectSummary.getLastModified()));

                GetObjectRequest req = new GetObjectRequest(bucket.getName(), objectSummary.getKey());

                //Download File
                client.getObject(req,new File("f:\\test.log.gz"));


                String path="f:\\test.log.gz";
                keymesaage = new KeyedMessage("logs",path);
                producer.send(keymesaage);
              }


            objects = client.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());



    }

}
