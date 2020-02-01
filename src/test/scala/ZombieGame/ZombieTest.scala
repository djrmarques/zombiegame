package ZombieGame

import org.scalatest.FunSuite

class ZombieTest extends FunSuite {

  val humansList: List[(Int, Int)] = List((1, 3), (0, 0))

  test("testGetNearestHuman1") {
    val zombie = new Zombie(0, 1, 1)
    zombie.getNearestHuman(humansList)
    assert(zombie.target == (0, 0))
  }

  test("testGetNearestHuman2") {
    val zombie = new Zombie(0, 1, 2)
    zombie.getNearestHuman(humansList)
    assert(zombie.target == (1, 3))
  }

  test("testGetNearestHuman3") {
    val zombie = new Zombie(0, 3, 5)
    zombie.getNearestHuman(humansList)
    assert(zombie.target == (1, 3))
  }

}
