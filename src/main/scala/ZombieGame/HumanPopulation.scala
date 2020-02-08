package ZombieGame

// Human class
class Human(val id: Int, val x: Int, val y: Int) {
  private var _isDead = false

  def isDead = _isDead

  def location = (x, y)

  def kill = _isDead = true
}

// Generate the Human population
object HumanPopulation {
  ???
}
