import cats.effect.{IO, IOApp}

import scala.util.{Try, Using}
import fs2.io.file._
import fs2.text

object CSVProcessingStreams extends IOApp.Simple {

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

  def readLegoSetStreams(fileName: String, p: LegoSet => Boolean, limit: Int): IO[List[LegoSet]] = {
    Files[IO]
      .readAll(Path(fileName)) // readAll returns bytes
      .through(text.utf8.decode)
      .through(text.lines)
      .map(parseLegoSet)
      .unNone
      .filter(p)
      .take(limit)
      .compile
      .toList
  }

  override def run: IO[Unit] = {
    val fileName = "sets.csv"
    readLegoSetStreams(fileName, _.year > 1970, 10)
      .flatMap(IO.println)
  }

}
