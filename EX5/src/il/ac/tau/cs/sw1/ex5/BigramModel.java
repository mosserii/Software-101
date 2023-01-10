package il.ac.tau.cs.sw1.ex5;



import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;

	String[] mVocabulary = new String[MAX_VOCABULARY_SIZE];    //TODO CHECK (= new String[MAX_VOCABULARY_SIZE])
	int[][] mBigramCounts = new int[mVocabulary.length][mVocabulary.length];

	// DO NOT CHANGE THIS !!!
	public void initModel(String fileName) throws IOException {
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);

	}


	/*
	 * @post: mVocabulary = prev(mVocabulary)//TODO CHECK
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		//String[] copyVocab = Arrays.copyOfRange(mVocabulary,0,mVocabulary.length);//TODO problEms
		String[] copyVocab = new String[MAX_VOCABULARY_SIZE];
		int cnt = 0;//index of vocabulary.
		boolean alreadyInVocab = false;
		boolean illegalCheckString = false;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

		String line;
		while ((line = bufferedReader.readLine()) != null && cnt < MAX_VOCABULARY_SIZE) {
			String[] words = line.split(" ");

			for (String checkString : words) {
				alreadyInVocab = false;
				for (int j = 0; j < checkString.length(); j++) {
					char c = checkString.charAt(j);

					if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {//english letter
						checkString = checkString.toLowerCase();


						for (int q = 0; q<cnt; q++){
							if (copyVocab[q].equals(checkString)) {//check if word in vocab
								alreadyInVocab = true;
								break;
							}

						}
						if (!alreadyInVocab) {
							copyVocab[cnt] = checkString;
							cnt++;
						}
						break;// go to next word

					}//end of if statment of english letter


					else if (!Character.isDigit(c)) {
						illegalCheckString = true;
						break;//illegal, go to next word

					} else if ((j == checkString.length() - 1) && Character.isDigit(c)) {
						copyVocab[cnt] = SOME_NUM;
						cnt++;
						break;
					}
				}
			}
		}
		copyVocab = Arrays.copyOfRange(copyVocab,0,cnt);

		bufferedReader.close();
		//System.out.println(Arrays.toString(copyVocab));

		return copyVocab;
	}


	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException { // Q - 2

		//int n = mBigramCounts.length;
		int vocLen = vocabulary.length;
		int[][] copyBG = new int[vocLen][vocLen];
		BufferedReader bufferedReader2 = new BufferedReader(new FileReader(new File(fileName)));

		String line;
		while ((line = bufferedReader2.readLine()) != null) {

			String[] wordsIN = line.split(" ");
			for (int loc = 0; loc < wordsIN.length; loc++)
				wordsIN[loc] = wordsIN[loc].toLowerCase();

			int row;
			int column;


			for (int i = 0; i < wordsIN.length - 1; i++) {//every couple
				row = getWordIndex(wordsIN[i], vocabulary);
				column = getWordIndex(wordsIN[i + 1], vocabulary);
				if (row != -1 && column != -1)
					copyBG[row][column]++;

			}
		}

		bufferedReader2.close();
		return copyBG;

	}


	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException { // Q-3 check TODO if read before writing is needed

		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		int n = mVocabulary.length;

		BufferedWriter vocWriter = new BufferedWriter(new FileWriter(vocFile));

		//FileWriter vocWriter = new FileWriter(fileName + VOC_FILE_SUFFIX);
		vocWriter.write(n + " words" + "\n");//TODO check


		for (int i = 0; i < n; i++) {
			vocWriter.write(i + "," + mVocabulary[i] + "\n");
		}
		vocWriter.close();

		///////////////////////////////////////////////

		File countsFile = new File(fileName + COUNTS_FILE_SUFFIX);
		//FileWriter countsWriter = new FileWriter(fileName + COUNTS_FILE_SUFFIX);

		BufferedWriter countsWriter = new BufferedWriter(new FileWriter(countsFile));

		for (int row = 0; row < mBigramCounts.length; row++) {
			for (int column = 0; column < mBigramCounts.length; column++) {
				if (mBigramCounts[row][column] != 0)
					countsWriter.write(row + "," + column + ":" + mBigramCounts[row][column] + "\n");
			}
		}
		countsWriter.close();

	}

	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException { // Q - 4 TODO if read before writing is needed
		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		BufferedReader vocBufferedReader = new BufferedReader(new FileReader(vocFile));

		String firstRow = vocBufferedReader.readLine();
		char firstChar = firstRow.charAt(0);
		int vocSize = Character.getNumericValue(firstChar);

		mVocabulary = new String[vocSize];//perfect size - initialized

		for (int i = 0; i < vocSize; i++) {
			String nextLn = vocBufferedReader.readLine();
			mVocabulary[i] = nextLn.substring(2);//after comma, till the end of the line
		}
		vocBufferedReader.close();

		//////////////////////////////////////////////////////

		int cnt = 0;
		mBigramCounts = new int[vocSize][vocSize];//initialized
		File countsFile = new File(fileName + COUNTS_FILE_SUFFIX);
		BufferedReader countsBufferedReader = new BufferedReader(new FileReader(countsFile));

		String line;
		while ((line = countsBufferedReader.readLine()) != null) {

			int row = Character.getNumericValue(line.charAt(0));
			int column = Character.getNumericValue(line.charAt(2));
			int val = Character.getNumericValue(line.charAt(4));

			mBigramCounts[row][column] = val;

		}
		countsBufferedReader.close();
	}


	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word) {  // Q - 5
		for (int i = 0; i < mVocabulary.length; i++) {
			if ((mVocabulary[i]).equals(word))
				return i;
		}
		return ELEMENT_NOT_FOUND;
	}

	public int getWordIndex(String word, String[] vocabulary ) {  // Q - 5
		for (int i = 0; i < vocabulary.length; i++) {
			if ((vocabulary[i]).equals(word))
				return i;
		}
		return ELEMENT_NOT_FOUND;
	}



	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2) { //  Q - 6
		int word1Loc = getWordIndex(word1);
		int word21Loc = getWordIndex(word2);
		if (word1Loc == -1 || word21Loc == -1)
			return 0;
		else
			return mBigramCounts[word1Loc][word21Loc];
	}


	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word) { //  Q - 7
		int wordLoc = getWordIndex(word);
		int maxOcc = 0;
		String winner = null;//if mVocabulary.length is 1, we wont go over the loop and return null.

		for (int j = mVocabulary.length - 1; j >= 0; j--) {
			if (maxOcc <= mBigramCounts[wordLoc][j]) {
				maxOcc = mBigramCounts[wordLoc][j];
				winner = mVocabulary[j];
			}
		}
		return winner;
	}


	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence) {  //  Q - 8
		String[] words = sentence.split(" ");
		if (words.length == 0)
			return true;
		if (words.length == 1)
			if (getWordIndex(words[0]) == -1)
				return false;
			else
				return true;

		for (int i = 0; i < words.length - 1; i++) {//couples
			int word1Loc = getWordIndex(words[i]);
			int word2Loc = getWordIndex(words[i + 1]);
			if (word1Loc == -1 || word2Loc == -1)
				return false;
			if (mBigramCounts[word1Loc][word2Loc] == 0)
				return false;
		}
		return true;
	}


	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2) { //  Q - 9
		double mone = 0.0;
		double AMechaneRoot = 0.0;
		double BMechaneRoot = 0.0;
		double mechane = 0.0;

		for (int i = 0; i < arr2.length; i++) { mone += arr1[i] * arr2[i]; }

		for (int num : arr1) { AMechaneRoot += (num * num); }
		for (int num : arr2) { BMechaneRoot += (num * num); }

		if (AMechaneRoot == 0.0 || BMechaneRoot == 0.0)
			return -1;

		mechane = Math.sqrt(AMechaneRoot) * Math.sqrt(BMechaneRoot);

		return (mone / mechane);
	}


	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized),
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word) { //  Q - 10
		int size = mVocabulary.length;
		int wordLoc = getWordIndex(word);
		int[] wordVector = new int[size];
		double match = 0.0;
		double res = 0.0;
		int rWinner = 0;

		if (size == 1)
			return mVocabulary[0];

		wordVector = mBigramCounts[wordLoc];


		for (int w = 0; w<mBigramCounts.length; w++){
			if (w != wordLoc) {
				res = calcCosineSim(wordVector, mBigramCounts[w]);
				if (match < res) {
					match = res;
					rWinner = w;
				}
			}
		}


		return mVocabulary[rWinner];
	}




}


