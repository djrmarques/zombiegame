package ZombieGame

import org.scalatest.FunSuite

class HumanTest extends FunSuite {
  test("test1"){
    val nPopulation = 5
    HumanPopulation.generatePopulation(nPopulation)
    assert(HumanPopulation.population.length == nPopulation)
    assert(HumanPopulation.anyAlive)
    assert(HumanPopulation.nDead == 0)
    assert(HumanPopulation.nAlive == nPopulation)
  }

  test("test2"){
    val nPopulation = 5
    HumanPopulation.generatePopulation(nPopulation)
    HumanPopulation.population(0).kill
    assert(HumanPopulation.anyAlive)
    assert(HumanPopulation.nDead == 1)
    assert(HumanPopulation.nAlive == nPopulation-1)
  }

  test("test3"){
    val nPopulation = 5
    HumanPopulation.generatePopulation(nPopulation)
    for (h <- HumanPopulation.population) {h.kill}
    assert(!HumanPopulation.anyAlive)
    assert(HumanPopulation.nDead == nPopulation)
    assert(HumanPopulation.nAlive == 0)
  }

  test("popStatus"){
    val humanPopulation = List(new Human(0, 1, 1), new Human(1, 2, 3))
    HumanPopulation.setPopulation(humanPopulation)

    println(HumanPopulation.popStatus)
    assert(Map(0 -> Map("location" -> (1,1)), 1 -> Map("location" -> (2,3))) == HumanPopulation.popStatus)

  }
}
