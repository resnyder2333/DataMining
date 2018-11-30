import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class testData {
	Vector<Integer> countVector; // counts of words; index matches 'terms'
	boolean isSpam = false;

	public int testData(File testRecord, Vector<String> termVector){
	Vector<Integer> countVector; // counts of words; index matches 'terms'
	boolean isSpam = false;
	int classification = 0;
	if(testRecord.getName().contains("sp")) {
		isSpam = true;
	}
try{
	//target a new file with the scanner, if there is none by the name given: give up
	Scanner input =new Scanner(testRecord);
	String line;
	//as long as there is still a line to read in the file
	while((line = input.nextLine())!=null){
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
              
             int index = termVector.indexOf(currentToken);
             if (index == -1) { // currentToken is not there

              }
               else {// currentToken exists at 'index'
                 for (int i = 0; i < (termVector.size()-countVector.size()); i++)
					countVector.add(0);
               	int count = countVector.get(index);
               	count++;
                countVector.set(count);
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
		}
	}catch(

	NoSuchElementException e2)
	{
		// no more lines in the file
		// no handler is necessary

	}input.close();
	// attempt to classify, and if it matches the real class...
	if(testData(record) == isSpam) {
	//add to correct count
	return 1;
}
//otherwise return 0 to not add to the number of correct guesses
return 0;
} // end fileToVector
	} // end class trainingData

	// trainingSet:
	// * key terms Vector
	// container aka vectorList of [file-to-counts-vector, and isSpam flag] ->
	// fileData objects
List<trainingData> vectorList = new List<trainingData>;
// for each file in training:
	trainingData fileData = new trainingData(file, termVector);vectorList.add(fileData);

	// read files for test data
	testData testFile = new testData(file, termVector);

	// do predictions
	// we have a 'kValue' from the user:

	// print results

result[] resultArray = new Array[vectorList.length];
	// in: testFile
	// in: vectorList (aka training data)
	// out: resultArray
	for(
	int i = 0;i<vectorList.length;i++)
	{
		trainingData compFile = vectorList.get(i);
		// Integer[] trainArray = new Integer[compFile.countVector.size()];
		// trainArray.countVector.toArray(trainArray);
		// repeat for 'testArray'
		// cosineSimilarity(trainArray, testArray);
		result myResult = new result();
		myResult.distance = cosineSimiliarity(compData.countVector.toArray(), testFile.countVector.toArray());
		myResult.isSpam = compFile.isSpam;
		resultArray.add(myResult);
	}

	class result implements Comparable<result> {
		double distance;
		bool isSpam;

		public int compareTo(result other) {
            return Double.compare( , );
          }
	}

	Collections.sort(resultArray);

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

}
