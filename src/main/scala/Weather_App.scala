import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.sql.{Connection,DriverManager}

object Weather_App {


  def process_csv(source: String, city_name:String, city_id:Integer, connection:Connection): Array[Map[String, String]] = {
    val bufferedSource = Source.fromFile(source)
    val lines = bufferedSource.getLines
    val labels = lines.next().split(",").map(_.trim)
    var output = new ArrayBuffer[Map[String, String]]()
    var statement = connection.prepareStatement("INSERT INTO cities (city_id, name) VALUES (?, ?)")
    statement.setInt(1, city_id)
    statement.setString(2, city_name)
    statement.execute()

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

      statement = connection.prepareStatement("INSERT INTO weather(city_id, date, actual_mean_temp, actual_min_temp, actual_max_temp," +
        "average_min_temp, average_max_temp, record_min_temp, record_max_temp, record_min_temp_year, record_max_temp_year, " +
        "actual_precipitation, average_precipitation, record_precipitation) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")

      statement.setInt(1, city_id)
      statement.setString(2, cols(0))
      if (cols(1) == "") statement.setNull(3, java.sql.Types.INTEGER) else statement.setInt(3, cols(1).toInt)
      if (cols(2) == "") statement.setNull(4, java.sql.Types.INTEGER) else statement.setInt(4, cols(2).toInt)
      if (cols(3) == "") statement.setNull(5, java.sql.Types.INTEGER) else statement.setInt(5, cols(3).toInt)
      if (cols(4) == "") statement.setNull(6, java.sql.Types.INTEGER) else statement.setInt(6, cols(4).toInt)
      if (cols(5) == "") statement.setNull(7, java.sql.Types.INTEGER) else statement.setInt(7, cols(5).toInt)
      if (cols(6) == "") statement.setNull(8, java.sql.Types.INTEGER) else statement.setInt(8, cols(6).toInt)
      if (cols(7) == "") statement.setNull(9, java.sql.Types.INTEGER) else statement.setInt(9, cols(7).toInt)
      if (cols(8) == "") statement.setNull(10, java.sql.Types.INTEGER) else statement.setInt(10, cols(8).toInt)
      if (cols(9) == "") statement.setNull(11, java.sql.Types.INTEGER) else statement.setInt(11, cols(9).toInt)
      if (cols(10) == "") statement.setNull(12, java.sql.Types.FLOAT) else statement.setFloat(12, cols(10).toFloat)
      if (cols(11) == "") statement.setNull(13, java.sql.Types.FLOAT) else statement.setFloat(13, cols(11).toFloat)
      if (cols(12) == "") statement.setNull(14, java.sql.Types.FLOAT) else statement.setFloat(14, cols(12).toFloat)
      statement.execute()

    }
    bufferedSource.close
    return output.toArray
  }

  // define process for selected data set
  def explore_data(selection: String,data_set: Array[Map[String, String]]) = {
    var cont = true

    do {
      println(selection + " selected.\n\n")
      println("Available data:")
      data_set(0).keys.foreach(println)
      print("Select data (or type 'back' to return)>: ")
      var command = readLine()

      if (command.toLowerCase().startsWith("back")) cont = false;
      else if (data_set(0).contains(command)) {
        data_set.foreach(row => println(row.get("date").get + " " + row.get(command).get))
      }
      else println("Command not recognized.")

    } while (cont)

  }

  // define variable to hold data read from csv
  def main(args: Array[String]): Unit = {

    val url = "jdbc:mysql://localhost:3306/weather"
    val username = "weather"
    val password = "pass"
    var connection:Connection = null
    try {
        connection = DriverManager.getConnection(url, username, password)
        val statement = connection.createStatement
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS cities (" +
          "city_id int," +
          "name varchar(55)," +
          "PRIMARY KEY(city_id)" +
          "); ")

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS weather (" +
          "record_id int AUTO_INCREMENT," +
          "city_id int," +
          "date date," +
          "actual_mean_temp int," +
          "actual_min_temp int," +
          "actual_max_temp int," +
          "average_min_temp int," +
          "average_max_temp int," +
          "record_min_temp int," +
          "record_max_temp int," +
          "record_min_temp_year int," +
          "record_max_temp_year int," +
          "actual_precipitation numeric," +
          "average_precipitation numeric," +
          "record_precipitation numeric," +
          "PRIMARY KEY(record_id)," +
          "FOREIGN KEY(city_id) REFERENCES cities(city_id) ON DELETE CASCADE" +
          ");")


          // read in csv
          val weather_data = Map(
            "charlotte" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kclt.csv", "charlotte", 1, connection),
            "los angeles" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kcqt.csv", "los angeles", 2 , connection),
            "houston" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\khou.csv", "houston", 3, connection),
            "indianapolis" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kind.csv", "indianapolis", 4, connection),
            "jacksonville" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kjax.csv","jacksonville", 5, connection),
            "chicago" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kmdw.csv", "chicago", 6, connection),
            "new york" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\knyc.csv", "new york", 7, connection),
            "philadelphia" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kphl.csv", "philadelphia", 8, connection),
            "phoenix" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\kphx.csv", "phoenix", 9, connection),
            "seattle" -> process_csv(raw"C:\Users\Devin\Documents\Revature\P0\static\ksea.csv", "seattle", 10, connection)
          )


          // trigger to exit do/while loop
          var cont = true

          do {
            println("Available data sets:")

            weather_data.keys.foreach(println)

            print("\n\nSelect a data set (or type 'quit' to quit):>  ")
            var command = readLine()

            if (command.toLowerCase.startsWith("quit")) cont = false
            else if (weather_data.contains(command)) {
              explore_data(command, weather_data.get(command).get)
            }
            else println("Command not recognized")
          } while (cont)
    
          println("Goodbye")
    } catch {
        case e: Exception => e.printStackTrace
    } finally {    
      connection.createStatement.executeUpdate("DROP TABLE weather;")
      connection.createStatement.executeUpdate("DROP TABLE cities;")
      connection.close
    }
  }
}