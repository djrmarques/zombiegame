package Trainer

import ZombieGame.ZombieGame.runGame
object Trainer extends App{

  def main: Unit = {
    val nZombies = 5
    val nHumans = 5
    val nTurns = 50

    runGameInstance(nZombies, nHumans, nTurns)

  }

  def runGameInstance(nZombies: Int, nHumans: Int, nTurns: Int): Int = {
    runGame(nZombies, nHumans, nTurns)
  }
}
