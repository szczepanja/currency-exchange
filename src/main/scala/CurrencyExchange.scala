object CurrencyExchange extends App {

  import org.apache.kafka.streams.scala.kstream.{KStream, KTable}
  import org.apache.kafka.streams.scala.StreamsBuilder
  import org.apache.kafka.streams.scala.ImplicitConversions._
  import org.apache.kafka.streams.scala.serialization.Serdes._

  val builder = new StreamsBuilder

  import org.apache.kafka.streams.StreamsConfig

  import java.util.Properties
  val props = new Properties()
  val appId = CurrencyExchange.getClass.getSimpleName.replace("$", "")
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, appId)
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ":9092")
  import org.apache.kafka.common.serialization.Serdes
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass)

  val amounts: KStream[String, String] = builder.stream[String, String]("amounts")
  val rates: KTable[String, String] = builder.table[String, String]("rates")

  import org.apache.kafka.streams.kstream.Printed
  amounts.print(Printed.toSysOut[String,String].withLabel("[Amounts]"))

  val solution = amounts.join(rates) { (amt: String, rate: String) =>
    (amt.toDouble * rate.toDouble).toString
  }

  solution.to("out")

  val topology = builder.build()
  topology.describe()

  import org.apache.kafka.streams.KafkaStreams
  val ks = new KafkaStreams(topology, props)
  ks.start()

}