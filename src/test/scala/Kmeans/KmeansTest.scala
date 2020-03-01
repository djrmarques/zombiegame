package Kmeans

import org.scalatest.FunSuite
import scala.math.pow

class KmeansTest extends FunSuite {

  val listPoints: List[(Int, Int)] = List(
    (1, 2),
    (2, 1),
    (3, 2),
    (10, 3),
    (13, 7),
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
    val cluster = new Cluster(listPoints, 5)
    val t0 = System.nanoTime()
    cluster.fit
    val t1 = System.nanoTime()
    val elapsedTime = (t1 - t0)/pow(10, 9)
    println("Elapsed time: " + elapsedTime + "s")
    println(cluster.clusterPoints)
    println(cluster.fitness)
    assert(elapsedTime < 0.05)
  }
}
