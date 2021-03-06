import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class kNearestNeighbor {
	private Scanner input;
	Vector<Integer> myDoc; 
	Vector<String> termVector;
	
	public kNearestNeighbor(String path) {
		try {
			input = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.print("Error opening file.");
			System.exit(1);
		}
	}
	
	private void count() {
		String line;
		try {
			for(int index = 0; line != null; index++) {
				while ((line = input.nextLine()) != null) {
					StringTokenizer st = new StringTokenizer(line);
					while(st.hasMoreTokens()) {
						String word;
						word = Integer.parseInt(word);
						myDoc.add(index, word);
					
						word = st.nextToken();
					}
				}
			}
				
		}
		catch (NoSuchElementException e) {
			// no more lines in the file
			// no handler is necessary
		}
	}
	
	class trainingData {
		   Vector<Integer> vector; // counts of words; index matches 'terms'
		   Boolean isSpam; // flag to indicate known spam vs known NS
			
			terms.add(word);
			fileToVector(File); //or could be a constructor. needs the terms Vector

			double calCosine(Vector<Integer> testVector);
	}
	List< trainingData >  trainingSet;
	
	class testData{
		Vector<Integer> testVector;
		Boolean isSpam;

		fileToVector(File);
		
		double calCosine();	
	}

	class result{
      	double distance;
    	Boolean isTrainingSpam;
    }

	class prediction{
		int kValue;
		//(k results)
		Boolean isTestSpam;
		//?? bool isCorrect;

	}

	public static double cosineSimilarity(double[] vectorA, double[] vectorB) {

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
