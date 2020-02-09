package ZombieGame

import scala.math.{sqrt, round}
import scala.util.Random

/* Movement trait, Used by each zombie and by Ash */
// Holds methods related to movement calculation
trait Movement {

  def x: Int
  def y: Int
  def target: (Int, Int)
  def distanceToTarget: Double
  def x_(p :Int)
  def y_(p :Int)
  def setLocation(newX: Int, newY: Int) = {x_(newX); y_(newY)}
  val stepSize: Int

  def getDistance(p: (Int, Int)): Double = {
    val dX: Int = x - p._1
    val dY: Int = y - p._2
    sqrt(dX * dX + dY * dY)
  }

  // Move in direction of the nearest target
  def moveToTarget = {
    // Check if distance is near enough to the target
    if (distanceToTarget <= stepSize) {
      x_(target._1)
      y_(target._2)
    }
    else if (distanceToTarget > stepSize) {
      val distanceRatio = stepSize/distanceToTarget
      x_(round((1-distanceRatio)*x + distanceRatio*target._1).toInt)
      y_(round((1-distanceRatio)*y + distanceRatio*target._2).toInt)
    }
    else {
      throw new Exception("An error occured. This is not suposed to happen")
    }
  }
}
/* NPC trait. Trait with the attributes shared between the zombies and humans*/
class NPC(val id: Int, _x: Int, _y: Int) {
  private var _isDead = false
  def isDead: Boolean =  _isDead
  def x: Int = _x
  def y: Int = _y
  def kill = _isDead = true
  // Get current position
  def location: (Int, Int) = (x, y)
}

/* Population Generation Trait, Used by each ZombieHord and HumanPopulation */
trait generation {
  var population: List[NPC]
  def nDead: Int =  (population filter (_.isDead)).length
  def nAlive: Int =  (population filter (!_.isDead)).length
  def anyAlive: Boolean = nAlive > 0

  val r = new Random(32) // Remove seed later
  def randomGenerateCoordinates(n: Int): List[(Int, Int, Int)] = {
    ((0 until n) map ((_, r.between(0, mapWidth), r.between(0, mapHeight)))).toList
  }

  def customGenerateCoordinates(coordsList: List[(Int, Int)]): List[(Int, Int, Int)] ={
    (for (i <- 0 until coordsList.length; (x, y) <- coordsList) yield (i, x, y)).toList
  }
}
