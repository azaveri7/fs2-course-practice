import fs2._

val s7 = Stream.range(1, 7)
val s8 = Stream.constant(40)

s7.toList
s8.take(5).toList