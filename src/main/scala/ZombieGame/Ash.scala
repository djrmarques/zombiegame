package ZombieGame

object Ash {
  private var _x = 0
  private var _y = 0

  val moveSpeed: Integer = 1000
  val attackRange: Integer = 2000

  def x: Integer = _x
  def y: Integer= _y

  def setLocation(newX: Integer, newY: Integer) = {_x = newX; _y = newY}

  def location  = (x, y)

}
