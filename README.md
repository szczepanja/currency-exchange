# currency-exchange

## Exercise: [Joining KStream with KTable (“Currency Exchange”)](https://jaceklaskowski.github.io/kafka-workshop/exercises/kafka-streams/kstream-join-ktable.html)

Develop a `Kafka Streams` application that simulate a **Currency Exchange((.

- Use the high-level `Streams DSL`
- Use `StreamsBuilder.stream` to create a `KStream` for amounts to convert 
- Use `StreamsBuilder.table` to create a `KTable` for rates
- Use `KStream.join` to join amounts with rates
