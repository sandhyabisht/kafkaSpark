import java.io.FileInputStream
import java.util.Properties

import org.apache.spark.storage.StorageLevel
import home.ReadContent
import kafka.serializer._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.elasticsearch.search.aggregations.support.ValuesSource.Bytes
import unzip.UnzipContent
import scala.Predef._
import scala.reflect.ClassTag

/**
 * Created by King on 1/20/2015.
 */
object ContentZipConsumer1 {

  def main(args: Array[String]) {

    //load properties
    val props=new Properties()
    props.load(new FileInputStream("api.properties"))
    val Zookeeper_port=props.getProperty("Zookeeper_port")
    val Kafka_port=props.getProperty("Kafka_port")

    println(Zookeeper_port+","+Kafka_port)
    println("Streaming")

    val conf = new SparkConf().setMaster("local[2]").setAppName("cybox")
    val ssc = new StreamingContext(conf, Seconds(10))
    val zkQuorum = "localhost:"+Zookeeper_port

    print("zk = " + zkQuorum)
    val kafka_group = "cybox_group"

    //KafkaUtils.createStream();
   // val kafkaStream = KafkaUtils.createStream(ssc, zkQuorum, kafka_group, Map("logs" -> 1))
    val kafkaParams = Map("zookeeper.connect" -> zkQuorum, "group.id" -> kafka_group)
    val topics_map = Map("logs" -> 1)

    //to read bytes
    val kafkaStream = KafkaUtils.createStream[Array[Byte], Array[Byte], DefaultDecoder, DefaultDecoder](ssc, kafkaParams, topics_map, StorageLevel.MEMORY_AND_DISK_SER_2)

    kafkaStream.print()
    //val messages = kafkaStream.map(_._2)

    /*    val unzip=new UnzipContent()
        val log=unzip.unzip(kafkaStream.map(_._2));
        println(log)
        println(messages)*/
    kafkaStream.foreachRDD(data => {
      println("------+++++++++-------------------------")

      data.foreach(d => {

        try {

          println(d._2)
          //Thread sleep 1000
         val unzip=new UnzipContent()
         // val log=unzip.unzip(d._2);
         val log=unzip.unzipContent(d._2);
         // println(log)

          var rd=new ReadContent()
          rd.main1(log);

        }
        catch {
          case e : Exception => println("exception caught: " + e)
        }
      })

    })
    //val parse = jep.invoke("main",)



    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate

  }

}
