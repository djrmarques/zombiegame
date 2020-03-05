package ZombieGame
import java.io.{File, PrintWriter}
import java.util.Calendar
import java.text.SimpleDateFormat

import io.circe.syntax._


object ZombieGame {

  Ash.setLocation(0, 0)
  Ash.setTarget(mapWidth/2, mapHeight/2)

  def decisionInput = {
    val zombieList: List[(Int, Int)] = ZombieHorde.locationList
    val humanList: List[(Int, Int)] = HumanPopulation.locationList
    val (newX, newY) = DecisionMaker.makeDecision(Ash.location, zombieList, humanList)
    Ash.setTarget(newX, newY)
  }


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

  def runGame(nHumans: Int, nZombies: Int, turnLim: Int): Int =  {

    /* Check if exists and create the folder to save the output */
    val outputFileFolder = new File(logOutputPath)
    if (!outputFileFolder.exists()) outputFileFolder.mkdir()
    val now = Calendar.getInstance.getTime
    val dateFormat = "yyyy-MM-dd_hh-mm-ss"
    val timeFormater = new SimpleDateFormat(dateFormat)
    val outputFileName = timeFormater.format(now)
    val outputFilePath = outputFileFolder + "/" + outputFileName + ".json"

    // Initialize Populations
    randomInitializeInstance(nHumans, nZombies)

    var nTurn = 0
    // Start turn 0

    var statusPerTurn = List(currentStatus)

    var endGameFlag = false

    // Start game loop
    while (nTurn < turnLim && !endGameFlag) {
      nTurn += 1
      ZombieHorde.moveZombies

      // Move Ash
      decisionInput
      Ash.moveToTarget
      Ash.updateDistanceToTarget

      // Kill all zombies and get score
      Ash.getScore

      // Zombies kill humans in range
      ZombieHorde.killHumans

      statusPerTurn = currentStatus :: statusPerTurn

      // End game criteria
      if (!HumanPopulation.anyAlive) {
        Ash.zeroScore; endGameFlag = true
      }
      else if (!ZombieHorde.anyAlive) {
        endGameFlag = true
      }
    }
    val outputFileWriter = new PrintWriter(new File(outputFilePath))
    outputFileWriter.write(statusPerTurn.reverse.asJson.toString())
    outputFileWriter.close()

    Ash.score
  }
}
