import fs2._

// Exercise 3

def myIterate[A](initial: A)(next: A => A): Stream[Pure, A] = {
  Stream.unfold(initial)(a => Some((a, next(a))))
}

myIterate('a')(c => (c+1).toChar).take(26).toList