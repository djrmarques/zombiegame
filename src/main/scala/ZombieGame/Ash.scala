package ZombieGame

object Ash extends Movement {
  private var _x = 0
  private var _y = 0
  private var _target = (0, 0)
  private var _distanceToTarget = 0.0
  private var _score: Int = 0

  def score: Int = _score

  def incrementScore(v: Int) = {_score += v}

  val stepSize: Int = 1000
  val attackRange: Int = 2000

  def distanceToTarget: Double = _distanceToTarget
  def x: Int = _x
  def y: Int = _y
  def target: (Int, Int) = _target

  def setTarget(targetX: Int, targetY: Int) = {
    _target = (targetX, targetY); _distanceToTarget = getDistance(_target)
  }

  def updateDistanceToTarget = {_distanceToTarget = getDistance(_target)}

  // Setter methods for the positions
  def x_(x: Int) = _x = x
  def y_(y: Int) = _y = y

  def location: (Int, Int)  = (x, y)

  def status = List(Map("posX" -> x, "posY" -> y,  "targetX" -> target._1, "targetY" -> target._2, "score" -> score))

  def getScore = {
    val nAliveHuman = HumanPopulation.nAlive
    val nDeadZombies = ZombieHorde.killZombies
    val score = nDeadZombies * (nAliveHuman * nAliveHuman)
    _score += score
  }

  def zeroScore = _score = 0
}
