package ZombieGame

import org.scalatest.FunSuite

class AshTest extends FunSuite {
  test("setLocation"){
    Ash.setLocation(20, 20)
    assert(Ash.location == (20, 20))
  }

}
