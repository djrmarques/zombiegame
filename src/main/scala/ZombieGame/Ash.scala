package ZombieGame

object Ash {
  private var _x = 0
  private var _y = 0

  val moveSpeed: Int = 1000
  val attackRange: Int = 2000

  def x = _x
  def y = _y

  def location  = (x, y)

}
