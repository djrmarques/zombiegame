package Kmeans

import org.scalatest.FunSuite

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

  test("testGetFitness"){
  }

  test("testFit"){
    val cluster = new Cluster(listPoints, 2)
    cluster.fit
    println(cluster.clusterPoints)
    println(cluster.fitness)
  }
}
