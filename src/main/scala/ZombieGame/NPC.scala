package ZombieGame

import scala.math.sqrt

// Definition of Zombie and Human classes
trait NPC {

  val x: Int
  val y: Int
  val id: Int

  def getDistance(p: (Int, Int)): Int = {
    val dX: Int = x - p._1
    val dY: Int = y - p._2
    sqrt(dX*dX + dY*dY).toInt
  }

  protected val distance: Int = getDistance((Ash.x, Ash.y))
}

class Zombie(val id: Int, val x: Int, val y: Int, humans: List[(Int, Int)]) extends NPC {}
class Human(val id: Int, val x: Int, val y: Int, ) extends NPC {}


