import fs2._

val s1 = Stream(1, 2, 3)
val nats = Stream.iterate(1)(_ + 1)


val doubled = s1.map(_ + 2)
doubled.toList

val infiniteDoubled = nats.map(_ + 2).take(5)
infiniteDoubled.toList