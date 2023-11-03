import cats.effect.{IO, IOApp}
import fs2.io.file._
import fs2.text

import scala.util.Try
import scala.concurrent.duration._

object CSVProcessingStreamsSlowly extends IOApp.Simple {

  case class LegoSet(id: String, name: String, year: Int, themeId: Int, numParts: Int)

  def parseLegoSet(line: String): Option[LegoSet] = {
    val splitted = line.split(",")
    Try (
      LegoSet(
        id = splitted(0),
        name = splitted(1),
        year = splitted(2).toInt,
        themeId = splitted(3).toInt,
        numParts = splitted(4).toInt
      )
    ).toOption
  }

  def readLegoSetStreamsParallely(fileName: String, p: LegoSet => Boolean, limit: Int): IO[List[LegoSet]] = {
    Files[IO]
      .readAll(Path(fileName)) // readAll returns bytes
      .through(text.utf8.decode)
      .through(text.lines)
      .map(parseLegoSet)
      .evalTap(IO.println)
      .metered(1.second)
      .unNone
      .filter(p)
      .take(limit)
      .compile
      .toList
  }

  override def run: IO[Unit] = {
    val fileName = "sets.csv"
    readLegoSetStreamsParallely(fileName, _.year > 1970, 5)
      .flatMap(IO.println)
  }

}
