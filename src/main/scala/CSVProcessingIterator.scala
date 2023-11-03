import cats.effect.{IO, IOApp}
import scala.io.Source
import scala.util.{Try, Using}

object CSVProcessingIterator extends IOApp.Simple {

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

  def readLegoSetIterator(fileName: String, p: LegoSet => Boolean, limit: Int): List[LegoSet] = {
    Using(Source.fromFile(fileName)) {
      source =>
        source
          .getLines()
          .flatMap(parseLegoSet)
          .filter(p)
          .take(limit)
          .toList
    }.get
  }

  override def run: IO[Unit] = {
    val fileName = "sets.csv"
    IO(readLegoSetIterator(fileName, _.year > 1970, 10))
      .flatMap(IO.println)
  }

}
