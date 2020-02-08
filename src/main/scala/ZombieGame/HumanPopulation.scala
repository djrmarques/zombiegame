package ZombieGame

// Human class
class Human(val id: Int, val x: Int, val y: Int) {
  private var _isDead = false

  def isDead = _isDead

  def location = (x, y)

  def kill = _isDead = true
}

// Generate the Human population
object HumanPopulation extends generation {
  val nPopulation = 5
  def generatePopulation : List[Human]= {
    val coords = randomGenerateCoordinates(nPopulation)
    coords map ((p: (Int, Int, Int)) => new Human(p._1, p._2, p._3))
  }
  val population = generatePopulation
  def nDead: Int =  (population filter (_.isDead)).length
  def nAlive: Int =  (population filter (!_.isDead)).length
  def anyAlive: Boolean = nAlive > 0
}
