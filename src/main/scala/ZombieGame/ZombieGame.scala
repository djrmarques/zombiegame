package ZombieGame

object ZombieGame extends App {

  // Initialize Ash position
  Ash.setLocation(0, 0)

  // Initialize the Human and Zombie population
  ZombieHorde.generatePopulation(5)
  HumanPopulation.generatePopulation(5)

  // Start game loop
  var nTurn = 1
  while (nTurn < 10) {
    println("Turn: ", nTurn)

    println("Ashe Location")
    println(Ash.location)

    println("Human Locations")
    println(HumanPopulation.allCords)

    println("Zombie Locations")
    println(ZombieHorde.allCords)
    nTurn +=1
  }
}
