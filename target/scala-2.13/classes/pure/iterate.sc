import fs2._

val s5 = Stream.iterate(1)(x => x + 1)

s5.take(10).toList