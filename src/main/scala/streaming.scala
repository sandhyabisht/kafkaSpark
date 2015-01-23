import home.ReadContent
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.sys.process._


object streaming{
  def main(args: Array[String]) {
    println("Streaming")
    val jars:Array[String] = new Array[String](1)
    //jars(0) = "/home/ubuntu/jep.jar"
    // Create a local StreamingContext with two working thread and batch interval of 1 second
    val conf = new SparkConf().setMaster("local[2]").setAppName("cybox").setJars(jars)
    val ssc = new StreamingContext(conf, Seconds(10))
    // val zkQuorum = System.getenv("zkQuorum")
    val zkQuorum = "localhost:2181"

    print("zk = " + zkQuorum)
    val kafka_group = "cybox_group"

    val kafkaStream = KafkaUtils.createStream(ssc, zkQuorum, kafka_group, Map("logs" -> 1))
    kafkaStream.print()

    kafkaStream.foreachRDD(data => {
      println("------+++++++++-------------------------")

      data.foreach(d => {

        try {

          var rd=new ReadContent()
          rd.main1(d._2);

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
