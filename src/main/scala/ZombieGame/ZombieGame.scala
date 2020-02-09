package ZombieGame
import java.io.File

object ZombieGame extends App {

  /* Check if exists and create the folder to save the output */
  val outputFile = new File(logOutputPath)
  if (!outputFile.exists()) outputFile.mkdir()
  outputFile.clo

  // Initialize Ash position
  Ash.setLocation(0, 0)

  // Initialize the Human and Zombie population
  ZombieHorde.generatePopulation(5)
  HumanPopulation.generatePopulation(5)

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
