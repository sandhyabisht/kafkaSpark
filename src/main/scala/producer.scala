import java.util.Properties

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{Bucket, GetObjectRequest}
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

import scala.collection.JavaConversions._
import util.control.Breaks._

object step {

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
    //println(s3)
    val bucket = new Bucket("logsdatatest")
    println("AWS Bucket")
    //var req = new GetObjectMetadataRequest

    //println(bucket)



   // for(bucket <- s3.listBuckets) {
    println(" - " + bucket.getName)
      var b = s3.listObjects(bucket.getName)
      val bs = b.getObjectSummaries



      //println("----" + b)
      //println(bs)
     // var count=0
      bs.foreach(file => {
       // count=count +1
       // if(count!=1) break
        println(file.getKey)
        val req = new GetObjectRequest(bucket.getName, file.getKey  )
        val f_content = s3.getObject(req).getObjectContent
        //println("--------------file content")
        //println(f_content)
        val f_string = scala.io.Source.fromInputStream(f_content).mkString
        //println("----- file string ----------" + f_string)
        var bm = s3.getObjectMetadata(bucket.getName,file.getKey)
        //println("BM------------------")
        //println(bm)


       val data = new KeyedMessage[AnyRef,AnyRef]("logs", f_string)
      producer.send(data)

      })


   // }
  }
}

