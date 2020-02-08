package ZombieGame

import org.scalatest.FunSuite
import scala.math._

class ZombieTest extends FunSuite {

  val humansList: List[Human] = List(
    new Human(0, 1, 1),
    new Human(1, 2, 3),
    new Human(2, 4, 5),
  )

  test("testGetAsheDistance1") {
    val zombie = new Zombie(0, 0, 0)
    Ash.setLocation(10, 10)
    zombie.getDistanceToAsh
    assert(zombie.distanceToAsh == sqrt(200))
  }

  test("testGetNearestHuman1") {
    val zombie = new Zombie(0, 1, 1)
    zombie.updateDistancesAndTarget(humansList)
    assert(zombie.target == (1, 1))
  }

  test("testGetNearestHuman2") {
    val zombie = new Zombie(0, 2, 2)
    zombie.updateDistancesAndTarget(humansList)
    println("Distance to Ash: ", zombie.distanceToAsh)
    println("Distance to target: ", zombie.distanceToTarget)
    assert(zombie.target == (2, 3))
  }

  test("testGetNearestHuman3") {
    val zombie = new Zombie(0, 3, 5)
    zombie.updateDistancesAndTarget(humansList)
    assert(zombie.target == (4, 5))
  }

  test("testGetNearestHuman4") {
    val zombie = new Zombie(0, 4, 5)
    zombie.updateDistancesAndTarget(humansList)
    assert(zombie.target == (4, 5))
  }
}
