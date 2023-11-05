import fs2._

val s1 = Stream(1, 2, 3)
val s2 = Stream(4, 5, 6)

(s1 ++ s2).toList

val nats = Stream.iterate(1)(_ + 1)

(s1 ++ nats).take(10).toList

(nats ++ s1).take(10).toList