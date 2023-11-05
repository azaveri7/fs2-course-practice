import fs2._

// Exercise 1
// Stream['a','b',....'z']
// iterate + take
def lettersIter: Stream[Pure, Char] = Stream.iterate('a')(s => (s+1).toChar).take(26)

lettersIter.toList