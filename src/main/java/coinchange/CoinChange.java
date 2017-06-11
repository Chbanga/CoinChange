/**
 * 
 */
package coinchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dragos STOICA
 *
 */
public class CoinChange {

	/**
	 * solution will contain for each amount a list of possible change
	 * combinations. each change combination will be counted once
	 */
	static Map<Integer, ArrayList<ArrayList<Integer>>> solution = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();

	static boolean DEBUG = false;

	private static void printSolution() {

		System.out.println("===================================");
		solution.forEach((key, value) -> {
			System.out.println("Amount " + key + ":");
			System.out.println("Possible solutions: " + value.size());

			for (int i = 0; i < value.size(); i++) {
				System.out.print((i + 1) + ". ");
				System.out.println(value.get(i) + " ");
			}

			System.out.println();
		});
		System.out.println("===================================");
	}

	private static void printArrayList(ArrayList<Integer> obj, String msg) {
		System.out.print(msg);
		obj.forEach((elm) -> {
			System.out.print(elm + " ");
		});
		System.out.println();
	}

	private static void addSolution(ArrayList<Integer> sol, int partialAmount) {
		int amount = sol.stream().mapToInt(coinValue -> coinValue.intValue()).sum() + partialAmount;
		ArrayList<ArrayList<Integer>> existingSolutions = solution.get(amount);
		if (!existingSolutions.contains(sol)) {
			existingSolutions.add(sol);
			solution.put(amount, existingSolutions);
		}
	}

	public static void change(int amount, ArrayList<Integer> coins, ArrayList<Integer> partialSolution) {

		if (DEBUG) {
			System.out.println("Amount = " + amount);
			printArrayList(coins, "Coins >>> ");
			printArrayList(partialSolution, "Partial solution: ");
			System.out.println("___________________________________");
		}

		// Check preconditions
		if (coins.size() == 0) {
			System.out.println("No coins!");
			return;
		}
		if (amount == 0) {
			System.out.println("No amount to change!");
			return;
		}
		if (amount > coins.stream().mapToInt(coinValue -> coinValue.intValue()).sum()) {
			System.out.println("Can not make change. The amount is greater than the sum of all coins!");
			return;
		}
		if (amount < coins.stream().min(Integer::compare).get()) {
			System.out.println("Can not make change. The amount is less than the smallest coin value!");
			return;
		}

		// Use Dynamic Programming to solve the problem

		ArrayList<ArrayList<Integer>> existingSolutions = solution.get(amount);
		// Initialize solutions array if null
		if (existingSolutions == null && partialSolution.size() == 0) {
			existingSolutions = new ArrayList<ArrayList<Integer>>();
			solution.put(amount, existingSolutions);
		}

		// Try to build a solution adding each of the coins to the partial
		// solution
		for (Integer coin : coins) {
			ArrayList<Integer> currentSolution = new ArrayList<Integer>(partialSolution);
			currentSolution.add(coin);
			currentSolution.sort(Integer::compare);

			// Build a solution using the each of the coins
			ArrayList<Integer> coinsSublist = new ArrayList<Integer>(coins);
			coinsSublist.remove(coin);
			ArrayList<Integer> partialSolutionAugmented = new ArrayList<Integer>(partialSolution);

			if (amount == coin) {
				// This is the last coin for the solution
				// Add this solution to top level
				addSolution(currentSolution, 0);
				continue;
			}

			if (amount > coin) {
				// Use the coin for the solution
				partialSolutionAugmented.add(coin);
				change(amount - coin, coinsSublist, partialSolutionAugmented);
				continue;
			}
			if (amount < coin) {
				// Do not use this coin for the solution
				change(amount, coinsSublist, partialSolution);
				continue;
			}

		}

	}

	/**
	 * @param args
	 *            - not used
	 */
	public static void main(String[] args) {
		int amount = 4;
		ArrayList<Integer> coins = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 2, 3));
		ArrayList<Integer> partialSolution = new ArrayList<Integer>();
		System.out.println("Solving using Dynamic Programming ");

		change(amount, coins, partialSolution);
		printSolution();
	}
}
