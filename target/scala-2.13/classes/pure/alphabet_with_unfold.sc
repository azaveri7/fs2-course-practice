import fs2._

// Exercise 2
def lettersUnfold: Stream[Pure, Char] = Stream.unfold('a')(s => if(s == 'z' + 1) None else Some((s, (s+1).toChar)))

lettersUnfold.toList