package Trainer

import ZombieGame.ZombieGame.runGame
object Trainer extends App{

  def runGameInstance(nZombies: Int, nHumans: Int, nTurns: Int): Int = {
    runGame(nZombies, nHumans, nTurns)
  }

  val nZombies = 5
  val nHumans = 5
  val nTurns = 50

  val fitness = runGameInstance(nZombies, nHumans, nTurns)
  println(fitness)

}
