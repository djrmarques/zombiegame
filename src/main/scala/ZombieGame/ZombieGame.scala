package ZombieGame
import java.io.{File, PrintWriter}
import java.util.Calendar
import java.text.SimpleDateFormat
import io.circe.syntax._

object ZombieGame extends App {

  /* Initialize the game instance randomly */
  def randomInitializeInstance(nHumans: Int, nZombies: Int): Unit ={
    // Initialize the Human and Zombie population
    ZombieHorde.generatePopulation(nZombies)
    HumanPopulation.generatePopulation(nHumans)
  }

  // Returns a dictionary with the current game status
  def currentStatus =  {
    val ashStatus = Map("Ash" -> Ash.status)
    val zombieStatus = Map("zombies" -> ZombieHorde.popStatus)
    val humanStatus = Map("humans" -> HumanPopulation.popStatus)

    ashStatus ++ zombieStatus ++ humanStatus
  }

    /* Check if exists and create the folder to save the output */
    val outputFileFolder = new File(logOutputPath)
    if (!outputFileFolder.exists()) outputFileFolder.mkdir()
    val now = Calendar.getInstance.getTime
    val dateFormat = "yyyy-MM-dd_hh-mm-ss"
    val timeFormater = new SimpleDateFormat(dateFormat)
    val outputFileName = timeFormater.format(now)
    val outputFilePath = outputFileFolder + "/" + outputFileName + ".json"

    // Initialize Populations
    randomInitializeInstance(1, 2)

    var nTurn = 0
    // Start turn 0

    var statusPerTurn = List(currentStatus)

    // Start game loop
    while (nTurn < 3) {
      nTurn += 1
      ZombieHorde.moveZombies
      statusPerTurn = currentStatus :: statusPerTurn
    }

  println(statusPerTurn)
  val outputFileWriter = new PrintWriter(new File(outputFilePath))
  outputFileWriter.write(statusPerTurn.asJson.toString())
  outputFileWriter.close()
}
