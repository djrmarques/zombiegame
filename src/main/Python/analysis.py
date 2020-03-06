import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np


class EvalScore:

    def __init__(self, n_max_zombies_killed: int, n_max_alive_humans: int):
        self.n_max_zombies_killed = n_max_zombies_killed
        self.n_max_alive_humans = n_max_alive_humans
        self.fibonnaci = self.gen_fibonnaci(40)
        self.result_matrix = self.build_score_matrix()

    @staticmethod
    def gen_fibonnaci(n: int) -> list:
        """ Generates a list with the first n fibbonaci numbers"""
        fibbonaci = np.ones(n, dtype=int)

        for i in range(2, n):
            fibbonaci[i] = fibbonaci[i - 2] + fibbonaci[i - 1]

        return fibbonaci

    def score(self, zombies_killed: int, n_humans_alive: int) -> int:
        """ Calculates the score based on the number of zombies killed and the number of alive humans"""
        killed_zombies_array = np.arange(zombies_killed)
        killed_zombies_array[1:] += 2
        fib_multiplier = self.fibonnaci[killed_zombies_array]
        return (fib_multiplier * (n_humans_alive ** 2 * 10)).sum()

    def build_score_matrix(self) -> np.ndarray:
        """ Build the score matrix"""
        matrix = np.zeros((self.n_max_zombies_killed, self.n_max_alive_humans))
        for i in range(self.n_max_zombies_killed):
            for j in range(self.n_max_alive_humans):
                matrix[i, j] = self.score(i, j)

        return matrix

    def visualize(self) -> None:
        """ Create the heatmap """
        plt.figure(figsize=(10, 10))
        sns.heatmap(self.result_matrix, cmap=sns.color_palette("coolwarm", 100))
        plt.ylabel("# Zombies Killed")
        plt.xlabel("# Alive Humans")
        plt.savefig("images/scores.png")
        plt.show()


if __name__ == "__main__":
    EvalScore(10, 10).visualize()
