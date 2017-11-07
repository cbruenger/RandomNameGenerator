import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

// This is the class that reads and formats the names from the given files, builds the Markov Model
// and generates the names.
public class BabyNamer {
	
	// Private class variables which contain input from the user, necessary data, and the data structures
	// used for the Markov Model and for storing names.
	private int markovOrder;
	private String underScoresBefore;
	private String underScoresAfter;
	private List<String> rawFileData;
	private List<String> formattedFileData;
	private Map<String, LetterListGenerator> markovModel = new HashMap<String, LetterListGenerator>();
	
	// The constructor takes in user input for the Markov Order and type of names to generate.
	// It calls functions to read and format the data in the chosen file, and to build the Markov Model
	public BabyNamer(int markovOrderIn, int nameTypeIn){
		this.markovOrder = markovOrderIn;
		assignformattedFileData(nameTypeIn);
		buildModel();
	}
	
	// This function is called by the constructor and takes in the name type chosen by the user. It uses a 
	// file reader to read the file containing names, and formats the names to lower case with the same 
	// number of leading underscores as the chosen Markov Order, and with one less trailing underscore 
	// as there are leading. It then stores the names in an ArrayList
	public void assignformattedFileData(int nameTypeIn){
		String fileName;
		String underScores = "";
		List<String> rawFileData = new ArrayList<String>();
		List<String> formattedFileData = new ArrayList<String>();
		if (nameTypeIn == 1) fileName = "namesBoys.txt";
		else fileName = "namesGirls.txt";
		Scanner rawDataScanner;
		try{
			rawDataScanner = new Scanner(new FileReader(fileName));
			while (rawDataScanner.hasNextLine()){
				rawFileData.add(rawDataScanner.nextLine());
			}
			rawDataScanner.close();
			this.rawFileData = rawFileData;
		}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
		}
		for (int i = 0; i < markovOrder; i++){
			underScores += "_";
			this.underScoresBefore = underScores;
			this.underScoresAfter = underScores.substring(0, underScores.length() - 1);
		}
		for (String string : rawFileData){
			String formattedString = underScoresBefore + string.trim().toLowerCase() + underScoresAfter;
			formattedFileData.add(formattedString);
		}
		this.formattedFileData = formattedFileData;
	}
	
	// This function is called by the constructor and builds the Markov Model by iterating through each n characters 
	// of each name, where n is the chosen Markov Order. It uses a HashMap to store every set of n characters as 
	// the key, while the value is a LetterListGenerator, which is a class designed to store the possible characters
	// at position n + 1 in an ArrayList. Cases where the n characters are the last characters in the name, where 
	// the HashMap already contains the key, and where the key is not yet stored are each handled separately.
	public void buildModel(){
		for (String name : formattedFileData){
			for (int i = 0; i + markovOrder <= name.length(); i++){
				String substringKey = name.substring(i, i + markovOrder);
				if (i + markovOrder == name.length()) markovModel.put(substringKey, new LetterListGenerator());
				else{
					String appendableLetter = name.substring(i + markovOrder, i + markovOrder + 1);
					if (markovModel.containsKey(substringKey)){
						markovModel.get(substringKey).addLetterToList(appendableLetter);
					}else{
						LetterListGenerator newList = new LetterListGenerator();
						newList.addLetterToList(appendableLetter);
						markovModel.put(substringKey, newList);
					}
				}
			}
		}
	}
	
	// This function generates the names. It takes in the min and max length of the names and the number of names 
	// to generate as chosen by the user. N charcters, which initially are the the leading underscores, are used
	// as the key and the LetterListGenerator representing the key's value is accessed and a random letter chosen
	// from it's ArrayList is returned and appended to the end of the current key. This process continues by setting
	// the last n characters of the growing string as the key until the name is finished, verified to be valid, 
	// reformatted and then stored in an ArrayList. Once the desired number of names have been generated, they
	// are printed.
	public void generateRandomNames(int minLengthIn, int maxLengthIn, int numberOfNames){
		List<String> randomNames = new ArrayList<String>();
		while (randomNames.size() < numberOfNames){
			String newName = this.underScoresBefore;
			String currentKey = newName;
			LetterListGenerator tempGen = markovModel.get(currentKey);
			newName += tempGen.randomLetterFromList();
			
			boolean nameIsFinished = false;
			
			while (!nameIsFinished){
				currentKey = newName.substring(newName.length() - markovOrder, newName.length());
				tempGen = markovModel.get(currentKey);
				if (tempGen.getAppendableLetterList().isEmpty()) nameIsFinished = true;
				else{
					newName += tempGen.randomLetterFromList();
				}
			}
			newName = newName.replace("_", "");
			newName = newName.substring(0, 1).toUpperCase() + newName.substring(1);
			if (newName.length() >= minLengthIn && newName.length() <= maxLengthIn && !rawFileData.contains(newName) && !randomNames.contains(newName)){
				randomNames.add(newName);
			}
		}
		if (randomNames.isEmpty()) System.out.println("No names were generated.");
		else{
			System.out.println("Names generated: \n");
			for (String nameToPrint : randomNames){
				System.out.println(nameToPrint + "\n");
			}
		}
	}	
}