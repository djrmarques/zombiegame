package ZombieGame

object Ash {
  private var _x = 0
  private var _y = 0

  def moveSpeed: Int = 1000
  def attackRange: Int = 2000

  private var _obX = 0
  private var _obY = 0

  def x: Int = _x
  def x_= (newValue: Int) = _x = newValue
  def obX: Int = _obX
  def obX_= (newValue: Int) = _obX = newValue

  def y: Int = _y
  def y_= (newValue: Int) = _y = newValue
  def obY: Int = _obY
  def obY_= (newValue: Int) = _obY = newValue


  def objective: String = s"$obX $obY"
}
