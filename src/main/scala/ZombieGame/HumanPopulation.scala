package ZombieGame

// Human class
class Human(id: Int, startingX: Int, startingY: Int) extends NPC(id: Int, startingX: Int, startingY: Int){
  override def x_(newX: Int): Unit = println("Cant change location on humans")
  override def y_(newY: Int): Unit = println("Cant change location on humans")
}

// Generate the Human population
object HumanPopulation extends Generation {
  private var _population: List[Human] = List()
  override def population: List[Human] = _population
  def setPopulation(newPopulation: List[Human]) = {_population = newPopulation}

  def generatePopulation(n: Int)= {
    setPopulation(randomGenerateCoordinates(n) map ((p: (Int, Int, Int)) => new Human(p._1, p._2, p._3)))
  }
}
