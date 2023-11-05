import fs2._

val s: Stream[Pure, Int] = Stream(1, 2, 3)
val s2: Stream[Pure, Int] = Stream.empty
val s3 = Stream.emit(43)
val s4 = Stream.emits(Vector(1, 3, 5))

s.toList
s2.toList
s3.toList
s4.toList
s4.toVector
