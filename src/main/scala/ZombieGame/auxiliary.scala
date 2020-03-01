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

  def updateDistanceToTarget

  def getDistance(p: (Int, Int)): Double = {
    val dX: Int = x - p._1
    val dY: Int = y - p._2
    sqrt(dX * dX + dY * dY)
  }

  def moveStepDistance: (Int, Int) = {
    val v = (target._1-x, target._2-y)
    val normV = sqrt(v._1*v._1 + v._2*v._2)
    val u = (v._1/normV, v._2/normV)

    val newX = (x + u._1*stepSize).toInt
    val newY = (y + u._2*stepSize).toInt

    (newX, newY)
  }

  // Move in direction of the nearest target
  def moveToTarget = {
    // Check if distance is near enough to the target
    val oldX = x
    val oldY = y

    if (distanceToTarget <= stepSize) {
      x_(target._1)
      y_(target._2)
    }
    else if (distanceToTarget > stepSize) {
      val newCoords = moveStepDistance
      x_(newCoords._1)
      y_(newCoords._2)
    }
    else {
      throw new Exception("An error occurred. This is not supposed to happen")
    }

    // Assert that the movement distance is not longer than the step size
    assert(round(getDistance((oldX, oldY))) <= stepSize+1, s"Distance Traveled: ${getDistance((oldX, oldY))}  | stepSize: $stepSize")
  }
}
/* NPC trait. Trait with the attributes shared between the zombies and humans*/
class NPC(val id: Int, startingX: Int, startingY: Int) {
  private var _x: Int = startingX
  private var _y: Int = startingY
  private var _isDead = false
  def isDead: Boolean =  _isDead
  def isDeadInt: Int = if(isDead) 1 else 0
  def x: Int = _x
  def y: Int = _y
  def kill = _isDead = true
  def x_(newX: Int) = _x = newX
  def y_(newY: Int) = _y = newY
  // Get current position
  def location: (Int, Int) = (x, y)
  def descriptor: (Int, Int ,Int) = (id, x, y)

  def status = Map("id" -> id, "posX" -> x, "posY" -> y, "isDead" -> isDeadInt)
}

/* Population Generation Trait, Used by each ZombieHorde and HumanPopulation */
class Generation {
  private var _population: List[NPC] = List()
  def population: List[NPC] = _population
  def nDead: Int =  (population filter (_.isDead)).length
  def nAlive: Int =  (population filter (!_.isDead)).length
  def anyAlive: Boolean = (population filter (!_.isDead)).length > 0

  def allCords: List[(Int, Int, Int)] = population filter (!_.isDead) map (_.descriptor)

  val r = new Random() // Remove seed later
  def randomGenerateCoordinates(n: Int): List[(Int, Int, Int)] = {
    ((0 until n) map ((_, r.between(0, mapWidth), r.between(0, mapHeight)))).toList
  }

  def customGenerateCoordinates(coordsList: List[(Int, Int)]): List[(Int, Int, Int)] ={
    ((0 until coordsList.length) zip coordsList).toList map (v => (v._1, v._2._1, v._2._2))
  }

  def popStatus = population map (_.status)

  def locationList = population filter (!_.isDead) map (_.location)
}
