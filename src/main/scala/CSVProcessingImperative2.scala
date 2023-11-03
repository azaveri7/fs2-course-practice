import cats.effect.{IO, IOApp}

import java.io.{BufferedReader, FileReader}
import java.nio.file.{Files, Paths}
import scala.collection.mutable.ListBuffer
import scala.util.Try
import scala.jdk.CollectionConverters._

object CSVProcessingImperative2 extends IOApp.Simple {

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

  def readLegoSetImperative(fileName: String, p: LegoSet => Boolean, limit: Int): List[LegoSet] = {
    var reader: BufferedReader = null
    val legoSets: ListBuffer[LegoSet] = ListBuffer.empty
    var counter = 0
    try {
      reader = new BufferedReader(new FileReader(fileName))
      var line: String = reader.readLine()
      while(line != null && counter < limit) {
        val legoSet = parseLegoSet(line)
        legoSet.filter(p).foreach {
          l => legoSets.append(l)
          counter+=1
        }
        line = reader.readLine()
      }
    } finally {
      reader.close()
    }
    legoSets.toList
  }

  def readLegoSetImperativeInFunctionalWay(fileName: String, p: LegoSet => Boolean, limit: Int): List[LegoSet] = {
    Files
      .readAllLines(Paths.get(fileName))
      .asScala
      .flatMap(parseLegoSet)
      .filter(p)
      .take(limit)
      .toList
  }

  override def run: IO[Unit] = {
    val fileName = "sets.csv"
    /*IO(readLegoSetImperative(fileName, _.year > 1970, 5))
      .flatMap(IO.println)*/

    IO.println("========")

    IO(readLegoSetImperativeInFunctionalWay(fileName, _.year > 1970, 8))
      .flatMap(IO.println)
  }

}
