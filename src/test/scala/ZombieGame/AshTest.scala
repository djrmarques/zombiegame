package ZombieGame

import org.scalatest.FunSuite
import scala.math._

class AshTest extends FunSuite {
  test("setLocation"){
    Ash.setLocation(20, 20)
    assert(Ash.location == (20, 20))
  }

  test("testMoveToTarget1"){
    val initialX = 0
    val initialY = 0
    Ash.setLocation(initialX, initialY)
    Ash.setTarget(1000, 1000)
    Ash.moveToTarget

    val dx = initialX-Ash.x
    val dy = initialY-Ash.y
    val distanceTraveled = sqrt(dx*dx + dy*dy)
    assert(abs(distanceTraveled - Ash.stepSize) < 1)
  }

  test("testMoveToTarget2"){
    val initialX = 1000
    val initialY = 800
    Ash.setLocation(initialX, initialY)
    Ash.setTarget(1000, 1000)
    Ash.moveToTarget
    assert(Ash.location == (1000, 1000))
  }

}
