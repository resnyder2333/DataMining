import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class testData {
	private Scanner input = null;
	Vector<Integer> countVector = new Vector<Integer>(); // counts of words; index matches 'terms'
	boolean isSpam = false;
	boolean prediction;

	testData(){
		//empty constructor
	}
	
	testData(File testRecord, Vector<String> termVector){
		pad(termVector);
		readRecord(testRecord, termVector);
	}
	
	//Get words from file, preprocess and store counts in vector
	public void readRecord(File testRecord, Vector<String> termVector){
		//get true value for record
		if(testRecord.getName().contains("spm")) {
			isSpam = true;
		}
		try{
			//target a new file with the scanner, if there is none by the name given: give up
			input =new Scanner(testRecord);
			String line;
			//as long as there is still a line to read in the file
			while((line = input.nextLine())!=null){
				//tokenize the line, and process each token to be compared later
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					//Make a placeholder to store the next Token for processing
					String currentToken = st.nextToken();
					//Words must be 2 characters or more
					if(currentToken.matches("\\w{2,}")) {
					//If the token has punctuation attached to the end,
					if(currentToken.matches("\\w+\\W")) {
						//remove the last character (the punctuation)
						currentToken = currentToken.substring(0, currentToken.length()-1);
					}
					//convert all the characters to lowercase, to count words, regardless of Caps
					currentToken = currentToken.toLowerCase();
					//Stem words by removing suffixes
					currentToken = currentToken.replaceAll("(ed|s|ing)$", " ");
					
					//add word to the set of words in this email
					
					int index = termVector.indexOf(currentToken);
					if (index != -1) { // currentToken exists at 'index'
							//increment value stored at index of the given word
							int count = countVector.get(index);
							count++;
							countVector.set(index, count);
					}
					}
					// we have the currentToken from the file
					// is it in the term vector? termVector.contains(currentToken)
					// NO: add it to the term vector
					// where is it in the term vector?
					// create a count of 1 in the count vector,
					// add it to that index in the counts vector
					// YES: if so, where is it in the term vector? termVector.indexOf(currentToken)
					// increment the count of that index in the counts vector
				}
			}
		}catch(NoSuchElementException e2)
		{
			// no more lines in the file
			// no handler is necessary
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();
	} // end fileToVector // end class trainingData

	
	public void classifyRecord(Vector<String>termVector, List<trainingData> trainingdata, int k) {
		ArrayList<trainingData> neighbors = new ArrayList<trainingData>();
		for(int i = 0; i < trainingdata.size();i++) {
			trainingData compareVector = trainingdata.get(i);
			Integer[] compareArray = new Integer[compareVector.countVector.size()];
			compareArray = compareVector.countVector.toArray(compareArray);
			Integer[] countArray = new Integer[countVector.size()];
			countArray = countVector.toArray(countArray);
			compareVector.distance = cosineSimilarity(compareArray, countArray);
			neighbors.add(compareVector);
		}
		Collections.sort(neighbors);//sort so highest cosine similarity is at the top
		while(neighbors.size() > k) {
			//remove bottom-most entry to return it to the top k similar entries
			neighbors.remove(neighbors.size()-1);
		}
		int isSpamVotes = 0;
		//determine class prediction
		for(int i=0; i<neighbors.size();i++) {
			if(neighbors.get(i).isSpam) {
				isSpamVotes++;
			}
		}
		double decision = isSpamVotes/(double) k;
		if(decision >= .5) {
			prediction = true;//It is spam, so says the neighbors
		}
		else {
			prediction = false;//It's a legit email, you guys
		}
		//prediction = true/false;
	}
	
	
	public static double cosineSimilarity(Integer[] vectorA, Integer[] vectorB) {
	
	double dotProduct = 0.0;
	
	double normA = 0.0;
	
	double normB = 0.0;
	
	for (int i = 0; i < vectorA.length; i++) {
	
		dotProduct += vectorA[i] * vectorB[i];
	
		normA += Math.pow(vectorA[i], 2);
	
		normB += Math.pow(vectorB[i], 2);
	
	}

	return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	public void pad(Vector<String>termVector) {
		int countSize = countVector.size();
		for (int i = 0; i < (termVector.size()-countSize); i++)
			countVector.add(0);
	}
	
	@Override
	public String toString() {
		String output = countVector + " Spam:" + isSpam + " Prediction: " + prediction;
		return output;
	}
}