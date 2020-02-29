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

  test("testGetNearestHuman5") {
    val zombie = new Zombie(4, 4364, 5697)
    val humans = List(
      new Human(8, 4249, 4695),
      new Human(2, 8344, 2717),
      new Human(6, 6253, 1575),
    )
    Ash.setLocation(8000, 4500)

    HumanPopulation.setPopulation(humans)
    zombie.updateDistancesAndTarget
    zombie.moveToTarget
    assert(zombie.target == (4249, 4695))
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

  test("killHumanFromZombie"){
    val zombie = new Zombie(0, 5, 5)
    val targetHuman = new Human(0, 5, 5)
    zombie.setHumanTarget(targetHuman)

    assert(!zombie.targetHuman.isDead)
    zombie.killTarget
    assert(zombie.targetHuman.isDead)
  }

  test("killHumans"){
    ZombieHorde.setPopulation(List(new Zombie(0, 10, 100)))
    HumanPopulation.setPopulation(List(new Human(0, 5, 5)))
    ZombieHorde.moveZombies
    assert(HumanPopulation.nAlive == 1)
    ZombieHorde.killHumans
    assert(HumanPopulation.nAlive == 0)
  }

  /* Test the distance method on the Zombies*/
  test("testMoveStepDistance"){
    val z = new Zombie(0, 0, 0)
    val h1 = new Human(0, 0, z.stepSize*2)
    z.setHumanTarget(h1)
    val newCoords = z.moveStepDistance
    print(newCoords)
    assert(newCoords._2 == z.stepSize)
  }

  test("testMoveStep1"){
    val z = new Zombie(0, 0, 0)
    val h1 = new Human(0, 0, z.stepSize*2)
    z.setHumanTarget(h1)
    assert(z.target == (0, z.stepSize*2))
    z.moveToTarget
    assert(z.target == (0, z.stepSize*2))
    assert(z.y == z.stepSize)
  }

  test("testMoveStep2"){
    val z = new Zombie(0, 0, 0)
    val h1 = new Human(0, 0, -z.stepSize*2)
    z.setHumanTarget(h1)
    assert(z.target == (0, -z.stepSize*2))
    z.moveToTarget
    assert(z.target == (0, -z.stepSize*2))
    assert(z.y == -z.stepSize)
  }
}
