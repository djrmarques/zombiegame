package ZombieGame

import Kmeans.{Kmeans}

import scala.math.{abs, ceil, min}

object DecisionMaker {

  val ashStepSize = 1000
  val zombieStepSize = 400
  val ashAttackRange = 2000
  val zombieAttackRange = 400

  var moveCoords: (Int, Int) = (0, 0) // Just to initialize the variable

  /* Compute distance between two points*/
  def distance(x1: Int, x2: Int, y1: Int, y2: Int): Int = {
    val dx = abs(x1-x2)
    val dy = abs(y1-y2)
    dx+dy
  }

  def nearestZombie(hLocation: (Int, Int), zombies: List[(Int, Int)]): Int = {
    (zombies map (zLoc => distance(hLocation._1, zLoc._1, hLocation._2, zLoc._2))).min
  }

  def makeDecision(ashPos: (Int, Int), zombies: List[(Int, Int)], humans: List[(Int, Int)]): (Int, Int) =  {

    /* Zombies clusters */
    val nZombies = zombies.distinct.length
    val nClusters = min(nZombies, 4)

    // Only run clustering with more than one zombie
    var zombieCoords: (Int, Int) = zombies.head
    // Cluster point with more zombies
    if (nZombies > 1) zombieCoords = Kmeans.solve(zombies, nClusters).clusterMorePoints

    // For each Human calculate the nearest zombie distance
    val humanTurnsToZombie: List[Int] = humans map (nearestZombie(_, zombies)/zombieStepSize*2) map (ceil(_).toInt)
    val humanTurnsToAsh: List[Int] = humans map (hLoc => distance(hLoc._1, ashPos._1, hLoc._2, ashPos._2)/ashStepSize)
    val humanWeights = (0 until humans.length) map (i => abs(humanTurnsToZombie(i) - humanTurnsToAsh(i)))

    // Check if there is a human at risk
    if ((humanWeights filter (_ >= 0)).min <= 2) {moveCoords = ((humans zip humanTurnsToZombie) minBy (_._2))._1}
    else moveCoords = zombieCoords

    (moveCoords._1, moveCoords._2)
  }

}
