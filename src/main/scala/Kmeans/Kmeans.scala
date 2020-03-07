package Kmeans

import scala.annotation.tailrec
import scala.math.{abs, sqrt}

class ClusterResult(val clusterPoints: List[(Int, Int)], val fitness: Int, nPointsPerCluster: Map[(Int, Int), Int]){
  val nClusters = clusterPoints.length
  val nPoinsPerCluster: List[Int] = clusterPoints map (nPointsPerCluster(_))
  val clusterMorePoints: (Int, Int) = ((clusterPoints zip nPoinsPerCluster) maxBy (_._2))._1
}

class Cluster(val pointsList: List[(Int, Int)], val maxNClusters: Int) {

  private val _intitialFitness = 10000000
  val improvementRate = 1  // Minimum  fitness improvement
  val fitStopCriteria = 0.01  // Minimum  fitness improvement

  // These will be set after the fit method
  private var _fitness = 0
  private var _clusterPoints: List[(Int, Int)] = List()
  private var _nClusters: Int = 0

  /* Compute distance between two points*/
  def distance(x1: Int, x2: Int, y1: Int, y2: Int): Double = {
    val dx = x1-x2
    val dy = y1-y2
    sqrt(dx*dx+dy*dy)
  }

  /* Mean of the cluster points */
  def meanPoint(points: List[(Int, Int)]): (Int, Int) = {
    val summed = points reduce ((acc, v) => (acc._1 + v._1, acc._2 + v._2))
    (summed._1/points.length, summed._2/points.length)
  }

  /* Calculate the fitness of the current cluster */
  def getFitness(listPoints: List[(Int, Int)], clusterAssignment: List[(Int, Int)]): Int = {
    ((listPoints zip clusterAssignment) map (p => distance(p._1._1, p._2._1, p._1._2, p._2._2)) reduce (_ + _)).toInt
  }

  /* Compute the nearest cluster of any point */
  def nearestCluster(point: (Int, Int), clusterPoints: List[(Int, Int)]): (Int, Int) ={
    clusterPoints minBy ((cPoint: (Int, Int)) => distance(point._1, cPoint._1, point._2, cPoint._2))
  }

  def improvementRate(intList: Seq[Int]): Seq[Int] = {
    val diffedList = for (i <- 1 until intList.length) yield { 1 - (intList(i) / intList(i - 1)) }
    intList.head +: diffedList
  }

  def getBestFit(results: Seq[ClusterResult]): ClusterResult = {
    val diffedFitness = improvementRate(results sortBy  (_.fitness) map (_.fitness))
    val zipped = diffedFitness zip results
    val filtered = zipped filter (_._1 < fitStopCriteria)
    if (filtered.isEmpty) results.head
    else (filtered minBy (_._1))._2
  }

  /*  Calls fit cluster with a number of different  cluster Number*/
  def fit: ClusterResult = {
    val results: Seq[ClusterResult] =
      (1 to maxNClusters) map ((nClusters: Int) => fitCluster(pointsList.take(nClusters), _intitialFitness))
    val bestFit: ClusterResult = getBestFit(results)

    _fitness = bestFit.fitness
    _clusterPoints = bestFit.clusterPoints
    _nClusters = _clusterPoints.length

    bestFit
  }

  /* Fit the cluster with a given number of cluster */
  @tailrec
  final def fitCluster(clusterPoints: List[(Int, Int)], bestFitness: Int): ClusterResult =  {
    //Assign points to the nearest cluster
    val assignedClusters: List[(Int, Int)] = pointsList map ((p: (Int, Int)) => nearestCluster(p, clusterPoints))

    // Calculate the fitness(And stop criteria)
    val newFitness = getFitness(pointsList, assignedClusters)
    val zippedAssignedClusters = assignedClusters zip pointsList
    if (abs(newFitness - bestFitness) > fitStopCriteria) {

      // Create new clusters centers
      val newClusters: List[(Int, Int)] = for (clusterPoint <- clusterPoints) yield {
        meanPoint(zippedAssignedClusters filter (_._1 == clusterPoint) map (_._2))
      }

      // Call fitCluster again
      fitCluster(newClusters, newFitness)
    }
    else {
      val nPointsPerCluster: Map[(Int, Int), Int] = zippedAssignedClusters groupBy (_._1) map {case (k, v) => k -> (v.length-1)}
      new ClusterResult(clusterPoints, newFitness, nPointsPerCluster)
    }
  }

  // Getter methods
  def fitness: Int = _fitness
  def clusterPoints: List[(Int, Int)] = _clusterPoints
  def nClusters: Int = _nClusters
}

object Kmeans {
  def solve(pointsList: List[(Int, Int)], maxNClusters: Int): ClusterResult = {
    val cluster: Cluster = new Cluster(pointsList, maxNClusters)
    cluster.fit
  }
}
