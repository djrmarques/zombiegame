package ZombieGame

import scala.math.floor

object Field {
  def width: Int =  16000
  def height: Int = 9000

  def convertCoords(x: Int, y: Int): Int = y*width + x

  def reverseConvertCoords(pos: Int) : (Int, Int) = {
    val y: Int = floor(pos/width).toInt
    val x: Int = pos - (y*width)
    (x, y)
  }


}
