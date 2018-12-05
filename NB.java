import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class NB {
	//scanner for reading files
	private Scanner input=null;
	//Hashmaps to store words and counts of each email in each class
	private HashMap<String, Integer> emailCounts = new HashMap<String, Integer>();
	private HashMap<String, Integer> spamCounts = new HashMap<String, Integer>();
	//ints to store the number of email and spam entries
	private int numEmail = 0;
	private int numSpam = 0;
	
	//Constructor to run code when called
	public NB() {
		NaiveBayes();
	}
	
	//Manages Training, testing and reporting results
	public void NaiveBayes() {
		//for each in training set, read and add to hash map
		File folder = new File("Train");
		File[] listOfFiles = folder.listFiles();
		
		//Begin Learning Phase
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				//read file and train network
				readFile(file);
				
				//count number of each class
				if(file.getName().contains("spm")) {
					numSpam++;
				}
				else {
					numEmail++;
				}
		    }
		}
		
		//Begin Testing Phase
		folder = new File("test");
		listOfFiles = folder.listFiles();
		int hits = 0, testSize = 0;
		//for each file in the test set
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				//read file and test the network
				hits += ClassifyNB(file);
				//count how many files were tested
				testSize++;
		    }
		}

		//calculate Accuracy
		double accuracy = hits/(double)testSize;
		System.out.println("Naive Bayes Accuracy: "+ (accuracy* 100) + "%");
	}
	
	//method for reading the given file and obtaining a list and a set of the words in that file
	private void readFile(File readFrom) {
			try{
				//target a new file with the scanner, if there is none by the name given: give up
				input=new Scanner(readFrom);
				}catch(FileNotFoundException e){
					System.err.println("Error opening file..");
					System.exit(1);
				}
				try{
				String line;
				
				//as long as there is still a line to read in the file
				while((line=input.nextLine())!=null){
					//tokenize the line, and process each token to be compared later
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreTokens()) {
						//Make a placeholder to store the next Token for processing
						String currentToken = st.nextToken();
						//If the token has punctuation attached to the end,
						if(currentToken.matches("\\w+\\W")) {
							//remove the last character (the punctuation)
							currentToken = currentToken.substring(0, currentToken.length()-1);
						}
						//convert all the characters to lowercase, to count words, regardless of Caps
						currentToken = currentToken.toLowerCase();
						
						//count word in appropriate class
						if(readFrom.getName().contains("spm")) {
							//count towards spam
							//Add the fully processed word to the list and the set 
							if(!spamCounts.containsKey(currentToken)) {
								//if this is the first instance of the word, add it to the set
								spamCounts.put(currentToken, 1);
							}
							else {
								//otherwise, increment the count
								spamCounts.put(currentToken, spamCounts.get(currentToken)+ 1);
							} 
							//System.out.println("Spam " + currentToken + " " + spamCounts.get(currentToken));
						}
						else {
							//count towards non-spam
							//Add the fully processed word to the list and the set 
							if(!emailCounts.containsKey(currentToken)) {
								//if this is the first instance of the word, add it to the set
								emailCounts.put(currentToken, 1);
							}
							else {
								//otherwise, increment the count
								emailCounts.put(currentToken, emailCounts.get(currentToken)+ 1);
							} 

							//System.out.println("Email " +currentToken + " " + emailCounts.get(currentToken));
						}
						
					}
				}
				}catch(NoSuchElementException e2){
				// no more lines in the file
				// no handler is necessary
					
				}
				
			input.close();
		}

	//reads test file, and attempts to classify using Naive Bayes
	private int ClassifyNB(File testRecord) {
		//set of what words are in this record
		HashSet<String> record = new HashSet<String>();
		//decide true class: 0 is an email, 1 is a spam
		int classification = 0;
		if(testRecord.getName().contains("spm")) {
			classification = 1;
		}
		try{
			
			//target a new file with the scanner, if there is none by the name given: give up
			input=new Scanner(testRecord);
			}catch(FileNotFoundException e){
				System.err.println("Error opening file..");
				System.exit(1);
			}
			try{
			String line;
			//as long as there is still a line to read in the file
			while((line=input.nextLine())!=null){
				//tokenize the line, and process each token to be compared later
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					//Make a placeholder to store the next Token for processing
					String currentToken = st.nextToken();
					//If the token has punctuation attached to the end,
					if(currentToken.matches("\\w+\\W")) {
						//remove the last character (the punctuation)
						currentToken = currentToken.substring(0, currentToken.length()-1);
					}
					//convert all the characters to lowercase, to count words, regardless of Caps
					currentToken = currentToken.toLowerCase();
					//add word to the set of words in this email
					record.add(currentToken);
				}
			}
			}catch(NoSuchElementException e2){
			// no more lines in the file
			// no handler is necessary
				
			}
		input.close();
		//attempt to classify, and if it matches the real class...
		if(ClassifyRecordNB(record) == classification) {
			//add to correct count
			return 1;
		}
		//otherwise return 0 to not add to the number of correct guesses
		return 0;
	}
	
	//Does actual classification of individual records
	private int ClassifyRecordNB(HashSet<String> record){
		//initialize array and scores for testing
		Object[]  arrayOfWords = record.toArray();
		double emailScore = 1, spamScore = 1, vocabulary = emailCounts.size()+spamCounts.size();
		
		for(int i=0; i<arrayOfWords.length;i++) {
			//Check for missing words, set to zero rather than null
			int emailMatches;
			if(emailCounts.get(arrayOfWords[i]) == null) {
				emailMatches  = 0;
			}
			else {
				emailMatches = emailCounts.get(arrayOfWords[i]);
			}
			int spamMatches;
			if(spamCounts.get(arrayOfWords[i]) == null) {
				spamMatches  = 0;
			}
			else {
				spamMatches = spamCounts.get(arrayOfWords[i]);
			}
			
			//get likelihood of class based on each word
			//Uses log method to prevent underflow of double changing results
			emailScore += Math.log((emailMatches+1)/(double)(numEmail+vocabulary));
			spamScore += Math.log((spamMatches+1)/(double)(numSpam+vocabulary));
		}
		if(emailScore > spamScore) {
			//return email
			return 0;
		}
		else {
			//return spam
			return 1;
		}
	}
}
