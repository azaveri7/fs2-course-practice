import fs2._

val s6 = Stream.unfold(1)(s => Some((s.toString, s+1)))
val s7 = Stream.unfold(1)(s => if(s == 3) None else Some((s.toString, s+1)))

s6.take(5).toList
s7.toList