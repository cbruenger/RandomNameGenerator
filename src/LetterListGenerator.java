import java.util.List;
import java.util.ArrayList;

// This class is used in the BabyNamer Class as the value in a key-value paired HashMap
// which represents the Markov Model. This class allows multiple values to be stored in
// an ArrayList for a particular key, and then produces a random letter from the lest.
public class LetterListGenerator {
	
	// A private class variable representing the list of letters 
	private List<String> appendableLetters = new ArrayList<String>();
	
	// This function is used when a letter is discovered while building the Markov Model.
	// The letter is added to the list of other letters.
	public void addLetterToList(String letterIn){
		appendableLetters.add(letterIn.toLowerCase());
	}
	
	// This function returns a random letter from the list. If the list is empty, an exception is raised.
	public String randomLetterFromList(){
		if (appendableLetters.isEmpty()) throw new IllegalStateException("Insufficient training data.");
		int randomIndex = (int) Math.floor(Math.random() * appendableLetters.size());
		return appendableLetters.get(randomIndex);
	}
	
	// This function returns the ArrayList of Letters
	public List<String> getAppendableLetterList(){
		return appendableLetters;
	}
}