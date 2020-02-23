package ZombieGame

// Zombie class
class Zombie(id: Int, _x: Int, _y: Int) extends NPC (id: Int, _x: Int, _y: Int) with Movement {
  private var _distanceToAsh = 0.0
  private var _distanceToTarget: Double = 0.0
  private var _targetAsh: Boolean = false
  private var _targetHuman: NPC = new Human(-1, 0, 0)
  private var _target = (0, 0)

  def distanceToAsh: Double = _distanceToAsh
  def targetAsh: Boolean = _targetAsh
  def distanceToTarget: Double = _distanceToTarget
  def target: (Int, Int) = _target
  def targetHuman: NPC = _targetHuman

  def setHumanTarget(h: Human)  = {_targetHuman = h; updateDistanceToTarget}

  val stepSize = 400

  // Changes the target based on the nearest human position
  def getNearestHuman = {
    val d = (HumanPopulation.population filter (!_.isDead) map (h => getDistance((h.location)))) zip HumanPopulation.population
    if (d.isEmpty){throw new Exception("No alive targets")}
    val minDistance = (d minBy (_._1))

    if (_distanceToAsh < minDistance._1) {
      _target = Ash.location
      _distanceToTarget = _distanceToAsh
      _targetAsh = true
    } else {
      _target = minDistance._2.location // Assign the nearest human to the target
      _distanceToTarget = minDistance._1 // Save the distance to target
      _targetAsh = false
      _targetHuman = minDistance._2
    }

  }

  def updateDistanceToTarget = {_distanceToTarget = getDistance(_target)}

  // If less than 2000 units from Ash, die
  def getDistanceToAsh = {
    _distanceToAsh = getDistance(Ash.location)
  }

  // Is killed?
  def isKilled: Int = {
    getDistanceToAsh
    if (distanceToAsh <= Ash.attackRange){kill; 1}
    else 0
  }

  // Run the update on the distances for the human list and Ash
  def updateDistancesAndTarget: Unit = {
    getDistanceToAsh
    getNearestHuman
  }

  def killTarget = {
    updateDistanceToTarget
    if (distanceToTarget < stepSize){_targetHuman.kill}
  }

  override def status = Map("id" -> id, "posX" -> x, "posY" -> y, "targetX" -> target._1, "targetY" ->target._2, "isDead" -> isDeadInt)
}

// The horde of zombies. Contains methods for generating the zombies and manipulating all the zombies
object ZombieHorde extends Generation{
  private var _population: List[Zombie] = List()
  override def population: List[Zombie] = _population
  def setPopulation(newPopulation: List[Zombie]) = {_population = newPopulation}

  def generatePopulation(n: Int)= {
    setPopulation(randomGenerateCoordinates(n) map ((p: (Int, Int, Int)) => new Zombie(p._1, p._2, p._3)))
  }

  def customGeneratePopulation(coords: List[(Int, Int)])= {
    setPopulation(customGenerateCoordinates(coords) map ((p: (Int, Int, Int)) => new Zombie(p._1, p._2, p._3)))
  }

  // Make all zombies move
  def moveZombies = for (z:Zombie <- population) {z.updateDistancesAndTarget; z.moveToTarget}

  // Check which zombies are killed. Return how many zombies were killed
  def killZombies: Int = {population map (_.isKilled) reduce (_ + _)}

  def killHumans = {population foreach (_.killTarget)}
}
