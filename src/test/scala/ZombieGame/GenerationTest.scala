package ZombieGame

import org.scalatest.{BeforeAndAfter, FunSuite}

class GenerationTest extends FunSuite with BeforeAndAfter {
  val gen = new Generation

  test("testRandomGenerateCoordinates") {
    assert(gen.randomGenerateCoordinates(4).length == 4)
  }

  test("testCustomGenerateCoordinates") {
    val coordsList = List((5, 50), (10, 10), (6, 7))
    assert(gen.customGenerateCoordinates(coordsList).length == 3)
  }

}
