package ZombieGame

object Ash {
  private var _x = 0
  private var _y = 0

  val moveSpeed: Int = 1000
  val attackRange: Int = 2000

  def x: Int = _x
  def y: Int= _y

  def setLocation(newX: Int, newY: Int) = {_x = newX; _y = newY}

  def location: (Int, Int)  = (x, y)

}
