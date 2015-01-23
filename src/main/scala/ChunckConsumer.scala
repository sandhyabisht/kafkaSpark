import java.io.FileInputStream
import java.util.Properties

import home.ReadContent
import kafka.serializer.DefaultDecoder
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import unzip.UnzipContent

/**
 * Created by King on 1/23/2015.
 */
object ChunckConsumer {

  def main(args: Array[String]) {

    //load properties
    val props=new Properties()
    props.load(new FileInputStream("api.properties"))
    val Zookeeper_port=props.getProperty("Zookeeper_port")
    val Kafka_port=props.getProperty("Kafka_port")
    val topic: String = props.getProperty("Kafka_topic")

    println(Zookeeper_port+","+Kafka_port)
    println("Streaming")

    val conf = new SparkConf().setMaster("local[2]").setAppName("cybox")
    val ssc = new StreamingContext(conf, Seconds(10))
    val zkQuorum = "localhost:"+Zookeeper_port

    print("zk = " + zkQuorum)
    val kafka_group = "cybox_group"

    //KafkaUtils.createStream();
     val kafkaStream = KafkaUtils.createStream(ssc, zkQuorum, kafka_group, Map(topic -> 1))
    val kafkaParams = Map("zookeeper.connect" -> zkQuorum, "group.id" -> kafka_group)
    val topics_map = Map(topic -> 1)

    //to read bytes
    //val kafkaStream = KafkaUtils.createStream[Array[Byte], Array[Byte], DefaultDecoder, DefaultDecoder](ssc, kafkaParams, topics_map, StorageLevel.MEMORY_AND_DISK_SER_2)

    kafkaStream.print()

    kafkaStream.foreachRDD(data => {
      println("------+++++++++-------------------------")

      data.foreach(d => {

        try {

          println(d._2)

          var rd=new ReadContent()
          rd.main1(d._2)

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
