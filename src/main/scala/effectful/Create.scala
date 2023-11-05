package effectful

import fs2._
import cats.effect._

object Create extends IOApp.Simple {

  override def run: IO[Unit] = {
    val s: Stream[IO, Unit] = Stream.eval(IO.println("anand"))
    s.compile.toList.flatMap(IO.println)
    s.compile.drain

    val s2: Stream[IO, Nothing] = Stream.exec(IO.println("anand!!"))
    s2.compile.drain

    val fromPure: Stream[IO, Int] = Stream(1, 2, 3).covary[IO]
    fromPure.compile.toList.flatMap(IO.println)

  }
}
