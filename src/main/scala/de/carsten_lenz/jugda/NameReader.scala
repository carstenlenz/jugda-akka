package de.carsten_lenz.jugda

import org.supercsv.io.{ICsvMapReader, CsvMapReader}
import org.supercsv.prefs.CsvPreference
import collection.mutable
import scala.io.Source

/**
 * @author Carsten Lenz, AOE
 */
object NameReader {

  def read(path: String): Set[String] = {
    val source = Source.fromFile(path, "UTF-8")
    val reader = new CsvMapReader(source.bufferedReader(), CsvPreference.STANDARD_PREFERENCE)
    val csvIterator = new CsvMapIterator(reader)

    val names = csvIterator.map { row =>
      row("Name")
    }.toSet
    println(names)

    names
  }
}

class CsvMapIterator(val mapReader: ICsvMapReader) extends Iterator[mutable.Map[String, String]] {
  import scala.collection.JavaConversions._
  import java.{util => ju}

  if (mapReader.getRowNumber > 0) {
    throw new RuntimeException("Map Reader was already read from")
  }

  val header = mapReader.getHeader(true)
  var nextRow: ju.Map[String, String] = mapReader.read(header:_*)

  override def hasNext: Boolean =  nextRow != null

  override def next(): mutable.Map[String, String] = {
    if (nextRow == null) {
      throw new IllegalStateException("No next row available")
    }
    val result = nextRow
    nextRow = mapReader.read(header:_*)
    result
  }
}