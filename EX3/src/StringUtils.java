import java.util.Arrays;

public class StringUtils {

	public static String findSortedSequence(String str) {

		String[] words = str.split(" ");//all the words in a list
		StringBuilder curr = new StringBuilder();
		String maxSeq = "";


		for (int i = 0; i < (words.length) - 1; i++) {//for every couple of words
			String[] maxSeqArray = maxSeq.split(" ");
			int check = words[i].compareTo(words[i + 1]);
			if (check <= 0) {//s1 before s2
				if (curr.isEmpty())
					curr.append(words[i]).append(" ").append(words[i + 1]);
				else
					curr.append(" ").append(words[i + 1]);
				String[] currArray = curr.toString().split(" ");

				if (currArray.length >= maxSeqArray.length)
					maxSeq = curr.toString();
			} else {//s1 not before s2
				curr = new StringBuilder();

			}
		}

			return maxSeq;
	}


	public static boolean isAnagram(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();
		String aString;
		String bString;


		String[] wordsa = a.split(" ");
		String[] wordsb = b.split(" ");

		aString = String.join("",wordsa);
		char[] aChar = aString.toCharArray();

		bString = String.join("",wordsb);
		char[] bChar = bString.toCharArray();

		Arrays.sort(aChar);
		Arrays.sort(bChar);

		return Arrays.equals(aChar, bChar);
	}


	public static boolean isEditDistanceOne(String a, String b){
		int aLength = a.length();
		int bLength = b.length();
		if (Math.abs(aLength - bLength) > 1)
			return false;
		int count = 0;
		int i = 0;
		int j = 0;

		while (i < aLength && j < bLength) {
			if (a.charAt(i) != b.charAt(j)) {
				if (count == 1)
					return false;
				if (aLength > bLength)
					i++;
				else if (aLength< bLength)
					j++;
				else {
					i++;
					j++;
				}
				count++;
			}

			else //no fixes needed
			{
				i++;
				j++;
			}
		}
		if (i < aLength || j < bLength)
			count++;
		return count <= 1;
	}

}
	

