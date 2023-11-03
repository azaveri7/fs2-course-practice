import cats.effect.{IO, IOApp}

import java.io.{BufferedReader, FileReader}
import scala.collection.mutable.ListBuffer
import scala.util.Try

object CSVProcessingImperative1 extends IOApp.Simple {

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

  def readLegoSetImperative(fileName: String): List[LegoSet] = {
    var reader: BufferedReader = null
    val legoSets: ListBuffer[LegoSet] = ListBuffer.empty
    try {
      reader = new BufferedReader(new FileReader(fileName))
      var line: String = reader.readLine()
      while(line != null) {
        val legoSet = parseLegoSet(line)
        legoSet.foreach(l => legoSets.append(l))
        line = reader.readLine()
      }
    } finally {
      reader.close()
    }
    legoSets.toList
  }

  override def run: IO[Unit] = {
    val fileName = "sets.csv"
    IO(readLegoSetImperative(fileName))
      .flatMap(IO.println)
  }

}
