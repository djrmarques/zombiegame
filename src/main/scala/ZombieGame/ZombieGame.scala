package ZombieGame
import java.io.File
import java.util.Calendar
import java.text.SimpleDateFormat

object ZombieGame extends App {

  /* Initialize the game instance randomly */
  def randomInitializeInstance(nHumans: Int, nZombies: Int): Unit ={
    // Initialize the Human and Zombie population
    ZombieHorde.generatePopulation(nHumans)
    HumanPopulation.generatePopulation(nZombies)
  }

  def main() {
    /* Check if exists and create the folder to save the output */
    val outputFileFolder = new File(logOutputPath)
    if (!outputFileFolder.exists()) outputFileFolder.mkdir()
    val now = Calendar.getInstance.getTime
    val dateFormat = "yyyy-MM-dd_hh-mm-ss"
    val timeFormater = new SimpleDateFormat(dateFormat)
    val outputFileName = timeFormater.format(now)
    val outputFilePath = outputFileFolder + "/" + outputFileName + ".txt"


    // Holder for the status of the game for each turn, in csv format
    var statusPerTurn = List()

    var nTurn = 0
    // Start turn 0

    // Start game loop
    while (nTurn < 10) {
      nTurn += 1
      ZombieHorde.moveZombies

      // Save the Game status into the log file
    }
  }
}
