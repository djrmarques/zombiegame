package ZombieGame

// Human class
class Human(id: Int, _x: Int, _y: Int) extends NPC(id: Int, _x: Int, _y: Int){}

// Generate the Human population
object HumanPopulation extends generation {
  var population: List[Human] = List()

  def generatePopulation(n: Int)= {
    val coords = randomGenerateCoordinates(n)
    population = coords map ((p: (Int, Int, Int)) => new Human(p._1, p._2, p._3))
  }
}
