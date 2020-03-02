package ZombieGame

import org.scalatest.FunSuite
import scala.math.pow

class ZombieGameTest extends FunSuite {

  test("testMakeDecision1") {
    val ashPos = (0, 0)
    val zombies =List(
      (13, 12),
    )

    val humans = List(
      (10,10)
    )

    val t0 = System.nanoTime()
    val res = DecisionMaker.makeDecision(ashPos, zombies, humans)
    val t1 = System.nanoTime()
    val elapsedTime = (t1 - t0)/pow(10, 9)
    println("Elapsed time: " + elapsedTime + "s")
    assert(elapsedTime < 0.4)
    println(res)
  }

  test("testMakeDecision2") {
    val ashPos = (0, 0)
    val zombies =List(
      (13, 11),
      (14, 12),
      (15, 12),
      (16, 13),
      (12, 12),
      (10, 11),
    )

    val humans = List(
      (10,10),
      (0, 8),
      (0, 9),
      (0, 10),
      (0, 11),
      (0, 12),
      (0, 13),
    )

    val t0 = System.nanoTime()
    val res = DecisionMaker.makeDecision(ashPos, zombies, humans)
    val t1 = System.nanoTime()
    val elapsedTime = (t1 - t0)/pow(10, 9)
    println("Elapsed time: " + elapsedTime + "s")
    assert(elapsedTime < 0.4)
    println(res)
  }

}
