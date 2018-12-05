import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SpamDetector {
	public static void main(String[] args) {
		//KNN
		kNearestNeighbors neighbor = new kNearestNeighbors();
		neighbor.kNN();
		//NB
		 NB bayes = new NB();
	}
}
