package Trainer

import ZombieGame.ZombieGame.runGame
object Trainer extends App{

  def runGameInstance(nZombies: Int, nHumans: Int, nTurns: Int, gameParams: Map[String, Double]): Int = {
    runGame(nHumans, nZombies, nTurns)
  }

  val nZombies = 10
  val nHumans = 5
  val nTurns = 50
  val gameParams = Map("Something" -> 1.0)

  val fitness = runGameInstance(nZombies, nHumans, nTurns, gameParams)
  println(fitness)

}
