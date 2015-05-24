import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class HungryPuppies {
	public static void main(String[] args){
		/* Read from stdin */
		Scanner scan = new Scanner(System.in);
		String[] lineArgs = scan.nextLine().split("\\s+");
		int[] treats = new int[lineArgs.length];
		for (int i = 0; i < lineArgs.length; i++){
			treats[i] = Integer.parseInt(lineArgs[i]);
		}

		/* Brute force attempt */
		int maxScore = 0;
		Integer[] bestPerm = new Integer[treats.length];
		ArrayList<Integer[]> combos = getPermutations(treats);
		for (Integer[] combo : combos){
			int score = getScore(combo);
			if (score > maxScore){
				maxScore = score;
				bestPerm = combo;
			}
		}

		/* Print results */
		System.out.println(maxScore);
		for (Integer i : bestPerm){
			System.out.print(i + " ");
		}
		System.out.println("");
	}

	/**
	 * Get the score for a given order of treats
	 * @param  dogs the order of dogs/treats
	 * @return      the score for this specific order
	 */
	private static int getScore(Integer[] dogs){
		int score = 0;
		for (int i = 0; i < dogs.length; i++){
			if (i == 0){
				score += mood(dogs[i], null, dogs[i+1]);
			}
			else if (i == dogs.length-1){
				score += mood(dogs[i], dogs[i-1], null);
			}
			else {
				score += mood(dogs[i], dogs[i-1], dogs[i+1]);
			}
		}
		return score;
	}

	/**
	 * The mood of a given dog based on its neighbors
	 * @param  self          the current dog
	 * @param  neighborLeft  left dog
	 * @param  neighborRight right dog
	 * @return               either -1, 0, or 1
	 */
	private static int mood(Integer self, Integer neighborLeft, Integer neighborRight){
		if (neighborLeft == null){
			if (self > neighborRight)
				return 1;
			else if (self == neighborRight)
				return 0;
			else
				return -1;
		}
		else if (neighborRight == null){
			if (self > neighborLeft)
				return 1;
			else if (self == neighborLeft)
				return 0;
			else
				return -1;
		}
		else {
			if (self > neighborLeft && self > neighborRight)
				return 1;
			else if (self < neighborLeft && self < neighborRight)
				return -1;
			else
				return 0;
		}
	}

	/**
	 * Utility function for getting all permutations of an int array (recursively)
	 */
	private static ArrayList<Integer[]> getPermutations(int[] arr) {
		ArrayList<Integer[]> perms = new ArrayList<>();
		if (arr == null) {
			return null;
		}
		else if (arr.length == 0) {
			Integer[] emptyArr = {};
			perms.add(emptyArr);
			return perms;
		}
 
		int firstInt = arr[0];
		int[] subArr = Arrays.copyOfRange(arr, 1, arr.length);
		ArrayList<Integer[]> morePerms = getPermutations(subArr);
		for (Integer[] p : morePerms){
			for (int i = 0; i <= p.length; i++){
				perms.add(intAdd(p, firstInt, i));
			}
		}
		return perms;
	}

	/**
	 * Insert a new int into a new permutaion of an array
	 * @param  arr the array to add an int to
	 * @param  i   the int to add
	 * @param  pos position to place the int
	 * @return     the new array with the additional int
	 */
	private static Integer[] intAdd(Integer[] arr, int i, int pos){
		Integer[] subArr1 = Arrays.copyOfRange(arr, 0, pos);
		Integer[] subArr2 = Arrays.copyOfRange(arr, pos, arr.length);
		Integer[] nextArr = new Integer[arr.length+1];
		for (int j = 0; j < nextArr.length; j++){
			if (j < pos){
				nextArr[j] = subArr1[j];
			}
			else if (j == pos){
				nextArr[j] = i;
			}
			else {
				nextArr[j] = subArr2[j-subArr1.length-1];
			}
		}
		return nextArr;
	}
}