package ZombieGame
import scala.util.Random

object ZombieGame extends App {

  // Generate random object
  val r = new Random(32)

  val mapWidth = 16000
  val mapHeight = 9000

  // Initialize Ash position
  Ash.setLocation(0, 0)

  var nTurn = 1
  while (nTurn < 10) {

    nTurn += 1
  }
}
