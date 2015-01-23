import java.io.FileInputStream
import java.util.Properties

import scala.tools.cmd.Property

/**
 * Created by King on 1/23/2015.
 */
object test {

  def main (args: Array[String]) {
    val props=new Properties()
    props.load(new FileInputStream("api.properties"))

    val Zookeeper_port=props.getProperty("Zookeeper_port")
    val Kafka_port=props.getProperty("Kafka_port")

    println(Zookeeper_port+","+Kafka_port)
  }

}
