package ZombieGame

import Kmeans.{ClusterResult, Kmeans}

import scala.math.{abs, ceil, max, sqrt}

object DecisionMaker {

  /* Compute distance between two points*/
  def distance(x1: Int, x2: Int, y1: Int, y2: Int): Double = {
    val dx = x1-x2
    val dy = y1-y2
    sqrt(dx*dx+dy*dy)
  }

  def nearestZombie(hLocation: (Int, Int), zombies: List[(Int, Int)]): Int = {
    (zombies map (zLoc => distance(hLocation._1, zLoc._1, hLocation._2, zLoc._2))).min.toInt
  }

  def makeDecision(ashPos: (Int, Int), zombies: List[(Int, Int)], humans: List[(Int, Int)]): (Int, Int) =  {

    /* Zombies clusters */
    val nZombies = zombies.distinct.length
    val nClusters = max(nZombies-1, 1)
    var zombieCoords: (Int, Int) = zombies.head
    if (nZombies > 1) {
      val clusterResult: ClusterResult = Kmeans.solve(zombies, nClusters)
      val clusterPoints = clusterResult.clusterPoints
      val clusterTurnsToAsh: List[Int] = (clusterPoints map (cPos => distance(cPos._1, ashPos._1, cPos._2, ashPos._2) / 2000)) map (ceil(_).toInt)
      val clusterWeights: Seq[Double] = (0 until clusterTurnsToAsh.length) map (p => clusterResult.nPoinsPerCluster(p).toDouble / clusterTurnsToAsh(p).toDouble)
      val totalWeight: Double = clusterWeights reduce (_ + _)
      val ZNormalizedWeights = clusterWeights map (_ / totalWeight)
      zombieCoords = (0 until clusterPoints.length) map (i => {
        val x = clusterPoints(i)._1
        val y = clusterPoints(i)._2
        val weight = ZNormalizedWeights(i)
        ((x * weight).toInt, (y * weight).toInt)
      }) reduce ((acc, v) => (acc._1 + v._1, acc._2 + v._2))
    }

    // Calculate the nearest zombie distance
    val humanTurnsToZombie: List[Int] = humans map (nearestZombie(_, zombies)/400) map (ceil(_).toInt)
    val humanTurnsToAsh = humans map (hLoc => distance(hLoc._1, ashPos._1, hLoc._2, ashPos._2)/2000) map (ceil(_).toInt)
    val humanWeights = (0 until humans.length) map (i => abs(humanTurnsToAsh(i) - humanTurnsToZombie(i)))
    val totalHumanWeight = humanWeights map (max(0, _)) reduce (_ + _)
    val HNormalizedWeights = humanWeights map (_/max(totalHumanWeight.toDouble, 1.0))
    val humanCoords: (Double, Double) = (0 until humans.length) map (i => {
      val x = humans(i)._1.toDouble
      val y = humans(i)._2.toDouble
      val weight = HNormalizedWeights(i)
      (x*weight, y*weight)
    }) reduce ((acc, v) => (acc._1 + v._1, acc._2 + v._2))

    val humanFactor: Double = {
      val minFactor: Int = humanWeights.min
      if (minFactor < 0 || minFactor > 1) 1.0
      else {99.0}
    }

    val zombieFactor = 50.0
    val totalWeight = zombieFactor + humanFactor

    val newX: Double= (zombieCoords._1.toDouble*zombieFactor+humanCoords._1.toDouble*humanFactor)/totalWeight
    val newY: Double = (zombieCoords._2.toDouble*zombieFactor+humanCoords._2.toDouble*humanFactor)/totalWeight

    (newX.toInt, newY.toInt)
  }

}
