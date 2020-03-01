package ZombieGame
import java.io.{File, PrintWriter}
import java.util.Calendar
import java.text.SimpleDateFormat
import io.circe.syntax._

object ZombieGame extends App {

  Ash.setLocation(0, 0)
  Ash.setTarget(mapWidth/2, mapHeight/2)

  val t0 = System.nanoTime()
  val coordsList: Seq[(Int, Int)] = for (i <- 0 until mapWidth; l <- 0 until mapHeight) yield {(i, l)}
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")

  def decisionInput: Map[String, List[(Int, Int)]] = {
    val zombieList: List[(Int, Int)] = ZombieHorde.locationList
    val humanList: List[(Int, Int)] = HumanPopulation.locationList
    Map("zombies"->zombieList, "humans"-> humanList)
  }

//  def makeDecision(infoMap: Map[String, List[(Int, Int)]]): Unit ={
//    val zList = infoMap["zombies"]
//    val hList = infoMap["humans"]
//  }

  /* Initialize the game instance randomly */
  def randomInitializeInstance(nHumans: Int, nZombies: Int): Unit ={
    // Initialize the Human and Zombie population
    HumanPopulation.generatePopulation(nHumans)
    ZombieHorde.generatePopulation(nZombies)
  }

  // Returns a dictionary with the current game status
  def currentStatus =  {
    val ashStatus = Map("Ash" -> Ash.status)
    val humanStatus = Map("humans" -> HumanPopulation.popStatus)
    val zombieStatus = Map("zombies" -> ZombieHorde.popStatus)

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
    randomInitializeInstance(5, 5)

    var nTurn = 0
    // Start turn 0

    var statusPerTurn = List(currentStatus)

    var endGameFlag = false

    // Start game loop
    while (nTurn < 50 && !endGameFlag) {
      nTurn += 1
      ZombieHorde.moveZombies

      // Move Ash
      Ash.moveToTarget
      Ash.updateDistanceToTarget

      // Kill all zombies and get score
      Ash.getScore

      // Zombies kill humans in range
      ZombieHorde.killHumans

      statusPerTurn = currentStatus :: statusPerTurn

      // End game criteria
      if (!HumanPopulation.anyAlive){Ash.zeroScore; endGameFlag=true}
      else if (!ZombieHorde.anyAlive){endGameFlag=true}
    }
  val outputFileWriter = new PrintWriter(new File(outputFilePath))
  outputFileWriter.write(statusPerTurn.reverse.asJson.toString())
  outputFileWriter.close()
}
