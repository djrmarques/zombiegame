package ZombieGame

import scala.math.{sqrt, round}

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

