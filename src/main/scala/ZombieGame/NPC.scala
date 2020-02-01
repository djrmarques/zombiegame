package ZombieGame

import scala.math.sqrt

class Zombie(val id: Int, startingX: Int, startingY: Int) {
  private var _x = startingX
  private var _y = startingY
  private var _isDead = false
  private var _target = (0, 0)

  def x: Int  = _x
  def y: Int  = _y
  def isDead: Boolean  = _isDead
  def target: (Int, Int)  = _target

  val stepSize = 400

  // Setter methods for the positions
  def x_(x: Int) = _x = x
  def y_(y: Int) = _y = y

  // Turn the isDead attribute to true
  def kill = _isDead = true

  // Get current position
  def getPost: (Int, Int) = (x, y)

  // Changes the target based on the nearest human position
  def getNearestHuman(humanList: List[(Int, Int)]) = {
    val distances = (humanList map (getDistance(_))) zip humanList
    _target = (distances minBy (_._1))._2 // Assign the nearest human to the target
  }

  // Move in direction of the nearest target
  def moveToTarget = {
    ???
  }

  // Move towards the nearest human and change the coordinates accordingly
  def move = {
    ???
  }

  def getDistance(p: (Int, Int)): Int = {
    val dX: Int = x - p._1
    val dY: Int = y - p._2
    sqrt(dX*dX + dY*dY).toInt
  }

  // If less than 2000 units from Ash, die
  def distanceToAsh = {
    ???
  }
}


class Human(val id: Int, val x: Int, val y: Int) {
  var isDead = false
}
