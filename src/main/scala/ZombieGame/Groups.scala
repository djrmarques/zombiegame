package ZombieGame

trait Groups {
  val nMembers: Int
}

class HumanSurvivors(humans: List[Human]) extends Groups {
  val nMembers = humans.length

}


class ZombieHord(zombies: List[Zombie]) extends Groups {
  val nMembers = zombies.length
}
