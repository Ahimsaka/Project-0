import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
// The "Image" DSL is the easiest way to create images
import doodle.image._
// Colors and other useful stuff
import doodle.core._


object Weather_App {

  // ingests csv data into a 2d arraybuffer
  def process_csv(source: String): Array[Map[String, Any]] = {
    val bufferedSource = Source.fromFile(source)
    var output = new ArrayBuffer[Map[String, Any]]()

    val lines = bufferedSource.getLines
    val labels = lines.next().split(",").map(_.trim)

    for (line <- bufferedSource.getLines) {
      val cols = line.split(",").map(_.trim)
      output += Map(
        labels(0) -> cols(0),
        labels(1) -> cols(1),
        labels(2) -> cols(2),
        labels(3) -> cols(3),
        labels(4) -> cols(4),
        labels(5) -> cols(5),
        labels(6) -> cols(6),
        labels(7) -> cols(7),
        labels(8) -> cols(8),
        labels(9) -> cols(9),
        labels(10) -> cols(10),
        labels(11) -> cols(11),
        labels(12) -> cols(12)
      )
    }
    bufferedSource.close
    return output.toArray
  }

  // define variable to hold data read from csv
  def main(args: Array[String]): Unit = {
    // read in csv
    val weather_data = Map(
      "charlotte" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kclt.csv"),
      "los angeles" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kcqt.csv"),
      "houston" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\khou.csv"),
      "indianapolis" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kind.csv"),
      "jacksonville" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kjax.csv"),
      "chicago" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kmdw.csv"),
      "new york" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\knyc.csv"),
      "philadelphia" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kphl.csv"),
      "phoenix" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kphx.csv"),
      "seattle" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\ksea.csv")
    )

    // trigger to exit do/while loop
    var cont = true

    do {
      print("Input command:>  ")
      var command = readLine()

      if (command.toLowerCase.startsWith("quit")) cont = false
      else if (weather_data.contains(command)) {
        /* DO SOMETHING HERE */



val blackSquare = Image.rectangle(30, 30).fillColor(Color.black)
val redSquare = Image.rectangle(30, 30).fillColor(Color.red)

// A chessboard, broken into steps showing the recursive construction
val twoByTwo =
  (redSquare.beside(blackSquare))
    .above(blackSquare.beside(redSquare))

val fourByFour =
  (twoByTwo.beside(twoByTwo))
    .above(twoByTwo.beside(twoByTwo))

val chessboard =
  (fourByFour.beside(fourByFour))
    .above(fourByFour.beside(fourByFour))




      }
      else println("Command not recognized")
      
    } while (cont)
    println("Goodbye")
    //bufferedSource.close
  }
}