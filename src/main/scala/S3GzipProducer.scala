import java.util.Properties

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{S3ObjectInputStream, GetObjectRequest, Bucket}
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import scala.collection.JavaConversions._
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream
import java.io.FileInputStream

/**
 * Created by rahul on 20/01/15.
 */

object S3GzipProducer {

  val s3 = new AmazonS3Client
  val bucket = new Bucket
  val props = new Properties()
  props.put("zk.connect","localhost:2181")
  props.put("metadata.broker.list","localhost:9092")
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  val config = new ProducerConfig(props)
  val producer = new Producer[AnyRef,AnyRef](config)

  def main(args: Array[String]) {


    val s3 = new AmazonS3Client
    val bucket = new Bucket("logsdatatest")

    //var req = new GetObjectMetadataRequest

    //println(bucket)

    // for(bucket <- s3.listBuckets) {
    println(" - " + bucket.getName)
    val b = s3.listObjects(bucket.getName)
    val bs = b.getObjectSummaries

    println("----" + b)

    bs.foreach(file => {

      val req = new GetObjectRequest(bucket.getName, file.getKey)
      val S3Objcontent = s3.getObject(req).getObjectContent

      //val f_string = scala.io.Source.fromInputStream(f_content)
      val gzipInputStream = new java.util.zip.GZIPInputStream(S3Objcontent);

      println(file.getKey)

      val in = new BufferedReader(new InputStreamReader(gzipInputStream))

      val str = Stream.continually(in.readLine()).takeWhile(_ != null).mkString("\n")

      println(str.length)
      //println(gzipInputStream)
      //val msg = new MyMessage

      val data = new KeyedMessage[AnyRef,AnyRef]("gzipfiles",str)
      producer.send(data)
    })


  }


}