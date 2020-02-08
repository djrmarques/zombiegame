package ZombieGame

import scala.math._

class Zombie(val id: Int, startingX: Int, startingY: Int)  extends Movement  {
  private var _x = startingX
  private var _y = startingY
  private var _isDead = false
  private var _distanceToTarget = 0.0
  private var _distanceToAsh = 0.0
  private var _targetAsh: Boolean = false
  private var _targetHuman: Human = new Human(-1, 0, 0)
  private var _target = (0, 0)

  def x: Int = _x
  def y: Int = _y

  def distanceToAsh: Double = _distanceToAsh

  def targetAsh: Boolean = _targetAsh

  def distanceToTarget: Double = _distanceToTarget

  def isDead: Boolean = _isDead

  def target: (Int, Int) = _target

  val stepSize = 400

  // Setter methods for the positions
  def x_(x: Int) = _x = x

  def y_(y: Int) = _y = y

  // Turn the isDead attribute to true
  def kill = _isDead = true

  // Get current position
  def location: (Int, Int) = (x, y)

  // Changes the target based on the nearest human position
  def getNearestHuman(humanList: List[Human]) = {
    val distances = (humanList filter (!_.isDead) map (h => getDistance((h.location)))) zip humanList
    val minDistance = (distances minBy (_._1))

    if (_distanceToAsh < minDistance._1) {
      _target = Ash.location
      _distanceToTarget = _distanceToAsh
      _targetAsh = true
    } else {
      _target = minDistance._2.location // Assign the nearest human to the target
      _distanceToTarget = minDistance._1 // Save the distance to target
      _targetAsh = false
      _targetHuman = minDistance._2
    }
  }


  // If less than 2000 units from Ash, die
  def getDistanceToAsh = {
    _distanceToAsh = getDistance(Ash.location)
  }

  // Run the update on the distances for the human list and Ash
  def updateDistancesAndTarget(humanList: List[Human]): Unit = {
    getDistanceToAsh
    getNearestHuman(humanList)
  }

}


class Human(val id: Int, val x: Int, val y: Int) {
  private var _isDead = false

  def isDead = _isDead

  def location = (x, y)

  def kill = _isDead = true
}
