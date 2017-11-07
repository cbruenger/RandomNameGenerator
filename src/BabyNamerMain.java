import java.util.Scanner;

// This main class takes the user input, verifies that each selection is valid, passes the input
// into an instance of the BabyNamer class, and calls the BabyNamer function to generate and
// print the names.
public class BabyNamerMain {
	
	// private class variables used to store and verify the user's input as valid
	private static int nameType;
	private static int numberOfNames;
	private static int minLength;
	private static int maxLength;
	private static int markovOrder;
	private static boolean isValidNameType = false;
	private static boolean validNumberToGenerate = false;
	private static boolean isValidMin = false;
	private static boolean isValidMax = false;
	private static boolean isValidMarkovOrder = false;
	
	public static void main(String[] args) {
		
		// A scanner used to read the user's input
		Scanner inputScanner = new Scanner(System.in);
		
		// Requests the user to select the type of names to generate
		System.out.println("Enter the number corresponding to the type of names you want to generate:");
		System.out.println("     1. Male");
		System.out.println("     2. Female");
		
		// Verifies that the user made a valid selection
		while (!isValidNameType){
			int nameTypeIn = inputScanner.nextInt();
			if (nameTypeIn == 1 || nameTypeIn == 2){
				isValidNameType = true;
				nameType = nameTypeIn;
			}
			else System.out.println("That was not a valid selection. Try again.");
		}
		
		// Requests the user to select the number of names to generate
		System.out.println("Enter the number of names you want to generate:");
		
		// Verifies that the user made a valid selection
		while(!validNumberToGenerate){
			int numberIn = inputScanner.nextInt();
			if (numberIn > 0){
				validNumberToGenerate = true;
				numberOfNames = numberIn;
			}
			else System.out.println("That was not a valid number. Choose a number greater than 0.");
		}
		
		// Requests the user to select the minimun length of the names
		System.out.println("Enter the minimum length of the names:");
		
		// Verifies that the user made a valid selection
		while(!isValidMin){
			int minIn = inputScanner.nextInt();
			if (minIn > 0){
				isValidMin = true;
				minLength = minIn;
			}
			else System.out.println("That was not a valid number. Choose a number greater than 0.");
		}
		
		// Requests the user to select the maximum length of the names
		System.out.println("Enter the maximum length of the names:");
		
		// Verifies that the user made a valid selection
		while(!isValidMax){
			int maxIn = inputScanner.nextInt();
			if (maxIn >= minLength){
				isValidMax = true;
				maxLength = maxIn;
			}
			else System.out.println("That was not a valid number. Choose a number greater than or equal to the minimum length.");
		}
		
		// Requests the user to select the number representing the Markov Order to be used
		System.out.println("Enter the Markov Order you want the program to use:");
		
		// Verifies that the user made a valid selection
		while(!isValidMarkovOrder){
			int orderIn = inputScanner.nextInt();
			if (orderIn > 0){
				isValidMarkovOrder = true;
				markovOrder = orderIn;
			}
			else System.out.println("That was not a valid number. Choose a number greater than 0.");
		}
		
		// No more input needed from user so the scanner is closed
		inputScanner.close();
		
		// Creates an instance of the BabyNamer class and passes the user input
		BabyNamer babyNamer = new BabyNamer(markovOrder, nameType);
		
		// Calls the BabyNamer function to generate and print names according to user input guidlines
		babyNamer.generateRandomNames(minLength, maxLength, numberOfNames);
	}
}