package ZombieGame
import java.io.File
import java.util.Calendar
import java.text.SimpleDateFormat

object ZombieGame extends App {

  /* Check if exists and create the folder to save the output */
  val outputFileFolder = new File(logOutputPath)
  if (!outputFileFolder.exists()) outputFileFolder.mkdir()
  val now = Calendar.getInstance.getTime
  val dateFormat = "yyyy-MM-dd_hh-mm-ss"
  val timeFormater = new SimpleDateFormat(dateFormat)
  val outputFileName = timeFormater.format(now)
  val outputFilePath = outputFileFolder + "/" + outputFileName + ".txt"

  /* Initialize the game instance randomly */
  def randomInitializeInstance(nHumans: Int, nZombies: Int): Unit ={
    // Initialize the Human and Zombie population
    ZombieHorde.generatePopulation(nHumans)
    HumanPopulation.generatePopulation(nZombies)
  }

  // Start game loop
  var nTurn = 1
  while (nTurn < 10) {
    println("Turn: ", nTurn)

    println("Ashe Location")
    println(Ash.location)

    println("Zombie Locations")
    println(ZombieHorde.allCords)
    ZombieHorde.moveZombies
    nTurn +=1
  }
}
