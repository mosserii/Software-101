import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {






	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;

	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		char[] puzz = new char[word.length()];
		for (int i = 0; i < template.length; i++){
			if (template[i])//true -> hidden
				puzz[i] = HIDDEN_CHAR;
			else
				puzz[i] = word.charAt(i);
		}
		return puzz;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2

		int n = template.length;
		if (word.length() != template.length)
			return false;

		char[] checkPuzz = new char[word.length()];
		checkPuzz = createPuzzleFromTemplate(word, template);
		String checkString = new String(checkPuzz);// a puzz made by the word

		int trueCounter = 0;
		int occCount = 0;

		for (int j = 0; j < n; j++) {
			if (template[j]) {
				trueCounter++;
				occCount = 0;

				for (int i = 0; i < n; i++) {
					if (checkString.charAt(i) == word.charAt(j))
						occCount++;
					if (occCount > 0)//the hidden letters is also not hidden in another occurrence//
						return false;
				}
			}
		}
		if (Math.abs(trueCounter - n) == 0 || Math.abs(trueCounter - n) == n)
			return false; //all true or all false -> false
		return true;
	}


	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k) {  // Q - 3

		int n = word.length();
		int choose = NchooseK(n, k);
		int r = 0; // template no. : index
		boolean[][] templates = new boolean[choose][n];//max of nchoosek templates, each one at size n.

		int q = (int) Math.pow(2, n);
		String[] binaryTemplate = new String[n];
		String s;
		int ones = 0;
		boolean[] boolTemplate = new boolean[n];


		for (int i = 0; i <= q; i++) {
			s = Integer.toBinaryString(i);//f.e."1101"
			if (s.length() == n+1)
				continue;
			while (s.length() != n) {
				s = "0" + s;
			}
			if (s.length() == n)
				binaryTemplate = s.split(""); // f.e : {"1","1","0","1"}
			else
				continue;
			ones = 0;

			for (int j = 0; j < binaryTemplate.length; j++) {
				if (binaryTemplate[j].equals("1"))
					ones++;
			}

			if (ones == k) {//goood
				for (int d = 0; d < binaryTemplate.length; d++) {
					if (binaryTemplate[d].equals("1"))
						boolTemplate[d] = true;
					else
						boolTemplate[d] = false;


				}
				if (checkLegalTemplate(word, boolTemplate)) {
					boolean[] copyOfBoolTemplate = Arrays.copyOfRange(boolTemplate, 0, boolTemplate.length);
					templates[r] = copyOfBoolTemplate;
					r ++;
				}
			}
		}

		return Arrays.copyOfRange(templates, 0, r);
	}


	public static int NchooseK(int intn, int intk){
		if (intk == 0)
			return 1;
		return (intn * NchooseK(intn - 1, intk - 1)) / intk;
	}



	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int changedCounter = 0;
		for (int i = 0; i < word.length(); i++) {
			if (puzzle[i] == HIDDEN_CHAR) {
				if (guess == word.charAt(i)) {
					puzzle[i] = guess;
					changedCounter++;
				}
			}
		}
			return changedCounter;

	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		Random rand = new Random();
		//rand.setSeed(2);
		char[] goodGuess = new char[word.length()];
		char[] badGuess = new char[26];//abc
		char letter;
		int r = 0; //good guess dummy
		int a = 0;//bad guess dummy
		boolean[] already_guessedCopy = Arrays.copyOfRange(already_guessed, 0, already_guessed.length);

		for (int j = 0; j < puzzle.length; j++) {
			letter = word.charAt(j);
			if (puzzle[j] == HIDDEN_CHAR) {
				goodGuess[r] = letter; // usefullll guesses array
				r++;
			}
			already_guessedCopy[(int) letter - 97] = true;//so the useless guesses array wont take this letter
		}

		goodGuess = Arrays.copyOfRange(goodGuess, 0, r);

		for (int i = 0; i<26; i++) {
			if (!already_guessedCopy[i]) {//is_false
				badGuess[a] = (char)(i+97);
				a++;
			}
		}
		badGuess = Arrays.copyOfRange(badGuess, 0, a);

		int randomNumber1 = rand.nextInt(goodGuess.length);
		int randomNumber2 = rand.nextInt(badGuess.length);
		char pickgood = (goodGuess[randomNumber1]);
		char pickbad = (badGuess[randomNumber2]);

		char[] res = {pickgood,pickbad};
		Arrays.sort(res);

		return res;
	}






		public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6

			printSettingsMessage();//a


			while (true) {//until we find good template
				printSelectTemplate();//b(i)
				String templateChosen = inputScanner.next();// b(ii) TODO String or int

				if (templateChosen.equals("1")) {
					printSelectNumberOfHiddenChars();//b(iii,1)
					String numOfHiddenChars = inputScanner.next();//b(iii,2)
					Random randForTemplate = new Random();

					boolean[][] b = getAllLegalTemplates(word, Integer.parseInt(numOfHiddenChars));
					if (b.length > 0) {//there's at least 1 legal template!
						int randomNumber = randForTemplate.nextInt(b.length);
						return createPuzzleFromTemplate(word, b[randomNumber]);
					}
					else {//cannot generate puzzle
						printWrongTemplateParameters();
						continue;
					}
				}
				if (templateChosen.equals("2")){
					printEnterPuzzleTemplate();
					String userT = inputScanner.next();
					String[] userTChar = userT.split(",");
					boolean[] userTemplate = new boolean[userTChar.length];
					for (int j = 0; j< userTChar.length; j++){
						if (!userTChar[j].equals("X"))//if not X,
							userTemplate[j] = true;//itll be HIDDEN
					}
					if (checkLegalTemplate(word, userTemplate))
						return createPuzzleFromTemplate(word,userTemplate);
					else
						printWrongTemplateParameters();
				}
			}
			//inputScanner.close();
		}




	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		printGameStageMessage();//7a
		int remainingGuesses = 3;
		int remainingHiddenChars = 0;
		boolean[] ABC = new boolean[26];//abc
		for (char c: puzzle){
			if (c == HIDDEN_CHAR){
				remainingGuesses++;
				remainingHiddenChars++;//checkkk
			}
		}
		while (remainingGuesses > 0) {// TODO (remaining guesses >=0)
			printPuzzle(puzzle);//7c
			printEnterYourGuessMessage();//7c
			String userGuess = inputScanner.next();
			if (userGuess.equals("H")) {
				printHint(getHint(word, puzzle,ABC));
			}
			else {
				char userGuessChar = userGuess.charAt(0);
				ABC[(int) userGuessChar - 97] = true;
				int afterGuess = applyGuess(userGuessChar,word,puzzle);
				if (afterGuess > 0){//good guess!!
					remainingHiddenChars -= afterGuess;
					if (remainingHiddenChars == 0) {
						printWinMessage();
						break;
					}

					else {
						remainingGuesses--;
						printCorrectGuess(remainingGuesses);
						if (remainingGuesses == 0) {//oh no
							printGameOver();
							break;
						}
					}
				}
				else { //not good guess, afterguess == 0
					remainingGuesses--;
					printWrongGuess(remainingGuesses);
					if (remainingGuesses == 0) {
						printGameOver();
						break;
					}
				}
			}
		}
	}
				
				


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();

	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
