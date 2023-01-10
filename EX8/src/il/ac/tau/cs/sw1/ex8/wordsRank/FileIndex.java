package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IllegalKValueException;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 30;

	public List<HashMapHistogram> tokenHistogramsFolder = new ArrayList<>();
	public int r = 0;

	public List<String> fileNamesInFOLDer = new ArrayList<>();

	public Set<String> wordsSet = new HashSet<>();




	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {//TODO TRY AND CATCH
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				fileNamesInFOLDer.add(file.getName());
				//add(r,file.getName());//TODO CHECK
				try {
					HashMapHistogram<String> tokensHistogram = new HashMapHistogram<String>();
					tokensHistogram.addAll(FileUtils.readAllTokens(file));
					wordsSet.addAll(tokensHistogram.getItemsSet());
					//wordsSet.addAll((Collection<? extends String>) tokensHistogram);
					this.tokenHistogramsFolder.add(r++, tokensHistogram);//adds in the same order as in folder

				} catch (IOException ignored) {
				}
			}
		}
  	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{//TODO ERROR MSG
		word = word.toLowerCase();
		int loc = fileNamesInFOLDer.indexOf(filename);

		if (!this.fileNamesInFOLDer.contains(filename))
			throw new FileIndexException("Sorry, File does not exist in folder");
		return tokenHistogramsFolder.get(loc).getCountForItem(word);//TODO FILE LOCTAION
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		word = word.toLowerCase();
		int rank = 1;
		boolean wordFound = false;
		int loc = fileNamesInFOLDer.indexOf(filename);

		if (loc == -1)
			throw new FileIndexException("File does not exist in Folder");// TOdo cHECK
		Iterator it = this.tokenHistogramsFolder.get(loc).iterator();
		while (it.hasNext()) {

			if (it.next().equals(word)) {
				wordFound = true;
				break;
			}
			else
				rank++;
		}
		if (wordFound)
			return rank;
		return rank + UNRANKED_CONST -1;//rank here will be the number of unique words

	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		Map<String, Integer> ranksForEachFile = new HashMap<>();
		for (String aFile : fileNamesInFOLDer){
			try {
				ranksForEachFile.put(aFile,getRankForWordInFile(aFile, word));
			} catch (FileIndexException e) { }
		}

		RankedWord ranked = new RankedWord(word, ranksForEachFile);//TODO FILE LOCTAION and list of file with ranks
		return ranked.getRankByType(rankType.average);
	}






	public List<String> getBetterRankedWords(int k, rankType type1) {

		Map<String, Integer> ranksForEachFile1 = new HashMap<>();

		ArrayList<String> result = new ArrayList<>();
		List<RankedWord> list1 = new ArrayList<>();

		for (String word3: wordsSet){
		for (String aFile : fileNamesInFOLDer) {
			try {
				ranksForEachFile1.put(aFile, getRankForWordInFile(aFile,word3));
			} catch (FileIndexException e) { }
		}
			list1.add(new RankedWord(word3, ranksForEachFile1));
		}


		list1.sort(new RankedWordComparator(type1));//TYpe


		for (int i = 0; i<list1.size(); i++){
			if (list1.get(i).getRankByType(type1) < k)
				result.add(list1.get(i).getWord());
			else
				break;
		}
		return result;
	}


	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		return getBetterRankedWords(k,rankType.average);
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		return getBetterRankedWords(k,rankType.min);
	}
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		return getBetterRankedWords(k,rankType.max);
	}

}
