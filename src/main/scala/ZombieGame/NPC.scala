package ZombieGame

import scala.math.sqrt

trait NPC {

  def x: Int
  def y: Int
  def id: Int
  def p: (Int, Int)

  def getDistance(p: (Int, Int)): Int = {
    val dX: Int = x - p._1
    val dY: Int = y - p._2
    sqrt(dX*dX + dY*dY).toInt
  }

  def distance: Int = getDistance(p)
}

