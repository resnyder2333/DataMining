import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class kNearestNeighbors {
	Scanner input = null;
	ArrayList<trainingData> trainingSet = new ArrayList<trainingData>();
	ArrayList<testData> testSet = new ArrayList<testData>();
	Vector<String> termVector = new Vector<String>();
	
	public void kNN(){
		//Build dictionary of all words
		File folder = new File("Train");
		File[] listOfFiles = folder.listFiles();
		//Begin Learning Phase
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	buildDic(file);
		    }
		}
		//build training set
		folder = new File("Train");
		listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	//train record
		    	trainingData data = new trainingData();
		    	//make length of dictionary
		    	data.pad(termVector);
		    	//populate record with data
		    	data.traintheData(file, termVector);
		    	//add record to table
		    	trainingSet.add(data);
		    }
		}
		//Test the classifier
		folder = new File("test");
		listOfFiles = folder.listFiles();
		int total = 0;
		//Begin Testing Phase
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				//read file and train network
		    	testData testRecord = new testData();
		    	//make length of dictionar
		    	testRecord.pad(termVector);
		    	//populate record with data
		    	testRecord.readRecord(file, termVector);
		    	//add record to table
		    	testSet.add(testRecord);
		    }
		}
		int[] ks = {1,3,5,19};
		for(int j=0;j<ks.length;j++) {
			int hits = 0;
			for(int i=0;i<testSet.size();i++) {
				
				testSet.get(i).classifyRecord(termVector, trainingSet, ks[j]);

		    	if(testSet.get(i).isSpam == testSet.get(i).prediction) {
		    		hits++;
		    	}
			}
			total = testSet.size();
			double accuracy = hits/(double) total;
			
			System.out.println(ks[j] + "NN Accuracy: " + (accuracy*100) + "%");
		}
	}
	
	//build termvector to have full size before recording and records
	public void buildDic(File record) {
		try{
			//target a new file with the scanner, if there is none by the name given: give up
			input =new Scanner(record);
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
					//System.out.println("File: "+ testRecord.getName() +"currentToken: " + currentToken);  

	                 int index = termVector.indexOf(currentToken);
	                 if (index == -1) { // currentToken is not there
	                   termVector.add(currentToken);
	                 }
	                 }
					 }
				}
			}
		catch(NoSuchElementException e2){
			// no more lines in the file
			// no handler is necessary

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();

	}
}