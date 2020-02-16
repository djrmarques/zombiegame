package ZombieGame

import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.math._

class ZombieTest extends FunSuite with BeforeAndAfter {

    val humansList: List[Human] = List(
      new Human(0, 1, 1),
      new Human(1, 2, 3),
      new Human(2, 4, 5),
    )
  before { HumanPopulation.setPopulation(humansList)}

  test("testGetAsheDistance1") {
    val zombie = new Zombie(0, 0, 0)
    Ash.setLocation(10, 10)
    zombie.getDistanceToAsh
    assert(zombie.distanceToAsh == sqrt(200))
  }

  test("testGetNearestHuman1") {
    val zombie = new Zombie(0, 1, 1)
    zombie.updateDistancesAndTarget
    assert(zombie.target == (1, 1))
  }

  test("testGetNearestHuman2") {
    val zombie = new Zombie(0, 2, 2)
    zombie.updateDistancesAndTarget
    println("Distance to Ash: ", zombie.distanceToAsh)
    println("Distance to target: ", zombie.distanceToTarget)
    assert(zombie.target == (2, 3))
  }

  test("testGetNearestHuman3") {
    val zombie = new Zombie(0, 3, 5)
    zombie.updateDistancesAndTarget
    assert(zombie.target == (4, 5))
  }

  test("testGetNearestHuman4") {
    val zombie = new Zombie(0, 4, 5)
    zombie.updateDistancesAndTarget
    assert(zombie.target == (4, 5))
  }

  test("testMoveToTarget1"){
    val zombie = new Zombie(0, 4, 4)
    zombie.updateDistancesAndTarget
    zombie.moveToTarget

    // Assert that the location changed
    assert(zombie.location == (4, 5))
  }

  test("testMoveToTarget2"){
    val initialX = 200
    val initialY = 1000
    val zombie = new Zombie(0, initialX, initialY)
    Ash.setLocation(100, 50)
    zombie.updateDistancesAndTarget
    zombie.moveToTarget
    assert(zombie.targetAsh)

    val dx = initialX-zombie.x
    val dy = initialY-zombie.y
    val distanceTraveled = sqrt(dx*dx + dy*dy)
    assert(abs(distanceTraveled - zombie.stepSize) < 1)
  }

  test("moveAllZombies"){
    ZombieHorde.customGeneratePopulation(List((10, 10), (15, 15), (5, 5)))
    val oldLocation = ZombieHorde.allCords
    ZombieHorde.moveZombies
    assert(ZombieHorde.allCords != oldLocation)
  }
}
