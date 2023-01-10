package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value // finding val in list
	*/
	public static boolean contains(int[] array, int value) {
		for (int num:array) {
			if (num == value)
				return true;
		}
		return false;
	}
	
	// there is intentionally no @post condition here 
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int unknown(int[] array) {
		if (array.length <= 2)
			return 0;
		int[] sortedArr = Arrays.copyOfRange(array,0,array.length);
		Arrays.sort(sortedArr);
		if (!Arrays.equals(sortedArr, array))
			return 0;
		return 1;

	}





	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] < $ret
	*/
	public static int max(int[] array) { 

		return array[array.length-1];//sorted
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min(int[] array) { 
		int minSoFar = 0;
		for (int contender:array) {
			if (contender < minSoFar)
				minSoFar = contender;
		}
		return minSoFar;
	}

	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(a.length() - i - 1)

	*/
	public static String reverse(String word) {
		int a = word.length();
		StringBuilder reversedWord = new StringBuilder();
		for (int j = 0; j<a; j++){
			reversedWord.append(word.charAt(a-j-1));
		}

		return String.valueOf(reversedWord);
	}


	
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre exist i,j such that: array[i] != array[j]
	* @post !Arrays.equals($ret, Arrays.sort($ret)) // returned array is not sorted
	* @post for any x: contains(prev(array),x) == true iff contains($ret, x) == true //same items
	*/
	public static int[] guess(int[] array) {

		int temp = array[0];
		array[0] = array[array.length-1];
		array[array.length-1] = temp;
		int first = array[0];


		return array;
	}


}
