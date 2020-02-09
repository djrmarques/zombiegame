package ZombieGame

object ZombieGame extends App {

  // Initialize Ash position
  Ash.setLocation(0, 0)

  // Initialize the Human and Zombie population
  ZombieHorde.generatePopulation(5)
  HumanPopulation.generatePopulation(5)

  var nTurn = 1
  while (nTurn < 10) {
    nTurn += 1
  }
}
