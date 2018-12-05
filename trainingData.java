import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class trainingData implements Comparable{
	private Scanner input = null;
	public Vector<Integer> countVector = new Vector<Integer>(); // counts of words; index matches 'terms'
	public boolean isSpam = false;
	public double distance;

	trainingData(){
		//empty constructor
	}
	
	public void traintheData(File testRecord, Vector<String> termVector){
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
	catch(NoSuchElementException e2){
		// no more lines in the file
		// no handler is necessary

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	input.close();

	} // end fileToVector

	//make countVector the correct size
	public void pad(Vector<String> termVector) {
        int countSize = countVector.size();
        for (int i = 0; i < (termVector.size()-countSize); i++)
				countVector.add(0);
	}
	
	//comparison ability, only returns int, so multiplies to preserve value & proportion
	@Override
	public int compareTo(Object trainPoint) {
		trainingData trainingPoint = (trainingData) trainPoint;
		int distanceToTestPoint = (int) (trainingPoint.distance * 1000);
		int mydist = (int) (this.distance * 1000);
		return (int) (distanceToTestPoint-mydist);
	}

	//This is for readability when debugging, tbh
	@Override
	public String toString() {
		String output = " Spam:" + isSpam + " Distance: " + distance + countVector;
		return output;
	}
}