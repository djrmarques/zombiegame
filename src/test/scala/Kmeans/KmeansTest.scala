package Kmeans

import org.scalatest.FunSuite
import scala.math.pow

class KmeansTest extends FunSuite {

  val listPoints: List[(Int, Int)] = List(
    (1, 2),
    (2, 1),
    (2, 8),
    (5, 5),
    (2, 1),
    (2, 5),
    (4, 7),
    (5, 2),
    (1, 3),
    (2, 1),
    (4, 3),
    (10, 3),
    (13, 9),
    (14, 6)
  )

  val NClusters = 2

  test("testMeanPoints"){
    val cluster = new Cluster(listPoints, 2)
    val res = cluster.meanPoint(List((1, 2), (3, 1), (5, 6)))
    assert(res == (3, 3))
  }

  test("testDistance"){
    val cluster = new Cluster(listPoints, 2)
    val res = cluster.distance(0, 100, 0, 0)
    assert(res == 100.0)
  }

  test("testFit"){
    val cluster = new Cluster(listPoints, 10)
    val t0 = System.nanoTime()
    cluster.fit
    val t1 = System.nanoTime()
    val elapsedTime = (t1 - t0)/pow(10, 9)
    println("Elapsed time: " + elapsedTime + "s")
    println(cluster.clusterPoints)
    println(cluster.fitness)
    assert(elapsedTime < 0.2)
  }

  test("testFitCluster"){
    val listPoints: List[(Int, Int)] = List((1000,8400), (2000,7800), (3000,7200), (4000,6600), (5000,6000), (6000,5400), (9000,3600), (10000,3000), (11000,2400), (12000,1800), (13000,1200), (14000,600), (14000,8400), (13000,7800), (12000,7200), (11000,6600), (10000,6000), (9000,5400), (6000,3600), (5000,3000), (4000,2400), (3000,1800), (2000,1200))
    val cluster = new Cluster(listPoints, listPoints.length-1)
    cluster.fit
  }

}
