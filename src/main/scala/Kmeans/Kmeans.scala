package Kmeans

import scala.annotation.tailrec
import scala.math.{abs, sqrt}

class Cluster(val pointsList: List[(Int, Int)], val maxNClusters: Int) {

  class ClusterResult(val clusterPoints: List[(Int, Int)], val fitness: Int)

  private val _intitialFitness = 10000000
  val improvementRate = 1  // Minimum  fitness improvement
  val fitStopCriteria = 10  // Minimum  fitness improvement

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

  /*  Calls fit cluster with a number of different  cluster Number*/
  def fit = {
    val results: Seq[ClusterResult] =
      (1 to maxNClusters) map ((nClusters: Int) => fitCluster(pointsList.take(nClusters), _intitialFitness))
    val bestFit: ClusterResult = results minBy (_.fitness)

    _fitness = bestFit.fitness
    _clusterPoints = bestFit.clusterPoints
    _nClusters = _clusterPoints.length
  }

  /* Fit the cluster with a given number of cluster */
  @tailrec
  final def fitCluster(clusterPoints: List[(Int, Int)], bestFitness: Int): ClusterResult =  {
    //Assign points to the nearest cluster
    val assignedClusters: List[(Int, Int)] = pointsList map ((p: (Int, Int)) => nearestCluster(p, clusterPoints))

    // Calculate the fitness(And stop criteria)
    val newFitness = getFitness(pointsList, assignedClusters)
    if (abs(newFitness - bestFitness) > fitStopCriteria) {

      // Create new clusters centers
      val zippedAssignedClusters = assignedClusters zip pointsList
      val newClusters: List[(Int, Int)] = for (clusterPoint <- clusterPoints) yield {
        meanPoint(zippedAssignedClusters filter (_._1 == clusterPoint) map (_._2))
      }

      // Call fitCluster again
      fitCluster(newClusters, newFitness)
    }
    else new ClusterResult(clusterPoints, newFitness)
  }

  // Getter methods
  def fitness: Int = _fitness
  def clusterPoints: List[(Int, Int)] = _clusterPoints
  def nClusters: Int = _nClusters
}

object Kmeans {



  def solve(pointsList: List[(Int, Int)], maxNClusters: Int): List[(Int, Int)] = {
    val cluster: Cluster = new Cluster(pointsList, maxNClusters)
    cluster.fit
    cluster.clusterPoints
  }
}
