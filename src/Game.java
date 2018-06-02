import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

/*************************************************************************
 * Game is an abstract class inherited by MasterMind and SearchGame classes Game
 * class hosts the different methods used in that application
 * 
 * @see Game#checkInput()
 * @see Game#generateHint()
 * @see Game#display(char[])
 * @see Game#display(Integer[])
 * @see Game#equalityTest(char[], int)
 * @see Game#equalityTest(String[], int)
 * @see Game#readConfig(String)
 * @see Game#presentation(String)
 * @see Game#endOfGame(String)
 * @see Game#randomCombination()
 * @see Game#comparisonSearchGame(Integer[], Integer[])
 * @see Game#comparisonMasterMind(Integer[], Integer[])
 * @see Game#generateOriginalList()
 * @see Game#generateList(String)
 * @see Game#generateProposalSearchGame(String)
 * @see Game#generateProposalMasterMind(String)
 * @see Game#displayLapstoGo()
 * @see Game#defenderMode(String)
 * @see Game#challengerMode(String)
 * @see Game#dualMode(String)
 * @see Game#displayModeDefender()
 * @see Game#displayModeChallenger()
 * @see Game#displayModeDual()
 * @see Game#revealSolution()
 * @see Game#logInfoPresentation(String, String)
 *************************************************************************/
public abstract class Game extends ReadConfig {

	protected Integer[] proposal;
	protected Integer[] combination;
	protected char[] hint;
	protected char[] hintBackup;
	protected ArrayList<ArrayList<Integer>> proposalList;
	protected ArrayList<Integer> list;
	protected ArrayList<Integer> backup;
	protected Random random;
	protected File file;
	protected String line;
	protected int numberAuthorizedTrials;
	protected int numberCompletedTrials;

	public void checkInput() {

		/**
		 * Check the user input
		 * 
		 * @exception NumberFormatException
		 */
		String message;
		String[] output;
		proposal = new Integer[numberBox];
		boolean entry = false;

		message = "\nMerci de saisir une série de " + numberBox + " chiffres ";
		message += "compris entre 0 et " + (numberDigit - 1);

		do {
			System.out.println(message);
			Scanner scanner = new Scanner(System.in);
			output = scanner.nextLine().split("");
			try {
				for (int i = 0; i < numberBox; i++) {
					if (output.length == numberBox && !equalityTest(output, 0)) {
						proposal[i] = Integer.valueOf(output[i]).intValue();
						entry = true;
					}
				}
			} catch (NumberFormatException e) {
			}
		} while (!entry);
	}

	public void generateHint() {
		/**
		 * generate the starting hint
		 */
		hint = new char[numberBox];
		for (int i = 0; i < numberBox; i++)
			hint[i] = '_';
	}

	public String display(char[] tab) {
		/**
		 * Display a array of characters
		 * 
		 * @param tab
		 */
		String str = "";
		for (char x : tab)
			str += x;
		return str;
	}

	public String display(Integer[] tab) {
		/**
		 * Display a array of integers
		 * 
		 * @param tab
		 */
		String str = "";
		for (int x : tab)
			str += x;
		return str;
	}

	public boolean equalityTest(char[] tab, int n) {
		/**
		 * test the equality of the hint with a successful hint that is a series
		 * of "O" for a MasterMind or "=" for a SearchGame
		 * 
		 * @param tab
		 * @param n
		 */
		if (n > tab.length - 1) {
			return false;
		}

		if (tab[n] != 'O' && tab[n] != '=')
			return true;
		else
			return equalityTest(tab, n + 1);
	}

	public boolean equalityTest(String[] tab, int n) {
		/**
		 * Check an array of numbers so that they are less than the number of
		 * digits
		 * 
		 * @param tab
		 * @param n
		 */
		if (n > tab.length - 1) {
			return false;
		}

		if (Integer.valueOf(tab[n]) > numberDigit - 1)
			return true;
		else
			return equalityTest(tab, n + 1);
	}


	public int presentation(String message) {
		/**
		 * Method that presents the different mode game
		 * 
		 * @param message
		 * @exception NumberFormatException
		 */
		int Input;

		do {
			Input = 0;
			Scanner scanner = new Scanner(System.in);
			try {
				System.out.println(message);
				Input = Integer.parseInt(scanner.nextLine());

			} catch (NumberFormatException e) {
			}

		} while (Input != 1 && Input != 2 && Input != 3);

		return Input;
	}

	public void endOfGame(String nameGame) {
		/**
		 * Method that proposes to replay or end the game
		 * 
		 * @param nameGame
		 */
		String message = "---------------------------------------\n";
		message += "|           FIN DU JEU                |\n";
		message += "---------------------------------------\n";
		message += "Que choisissez-vous de faire ?\n";
		message += "\n1 : Rejouer au même jeu\n";
		message += "2 : Jouer un autre jeu\n";
		message += "3 : Quitter le jeu";

		int choix = presentation(message);

		switch (choix) { 
		case 1:
			if (nameGame == "SearchGame")
				new SearchGame();
			if (nameGame == "MasterMind")
				new MasterMind();
			break;
		case 2:
			new Main().Launching();
			break;
		case 3:
			System.out.println("---------------------------------------");
			System.out.println("|    VOUS QUITTEZ L'APPLICATION !     |");
			System.out.println("---------------------------------------");
			break;
		}
	}

	public void randomCombination() {
		/**
		 * Method that generate a combination of number And display it if
		 * developerMode is enable
		 */
		combination = new Integer[numberBox];

		for (int i = 0; i < numberBox; i++) {
			int n = (int) Math.round(Math.random() * (numberDigit - 1));
			combination[i] = n;
		}

		System.out.println("\nNous venons de choisir la combinaison");

		if (isModeDeveloper() == true) {
			System.out.print("(");
			for (int x : combination)
				System.out.print(x);
			System.out.println(")");
		}
	}

	public void comparisonSearchGame(Integer[] proposal, Integer[] combination) {
		/**
		 * Method that handle the indices according to the proposed numbers
		 * 
		 * @param proposal
		 * @param combination
		 */
		for (int i = 0; i < numberBox; i++) {
			if (proposal[i] == combination[i]) {
				hint[i] = '=';
			}

			if (proposal[i] > combination[i]) {
				hint[i] = '-';
			}

			if (proposal[i] < combination[i]) {
				hint[i] = '+';
			}
		}
		System.out.println(display(hint));
	}

	public void comparisonMasterMind(Integer[] proposal, Integer[] combination) {
		/**
		 * Method that compares the proposed combination with to the secret
		 * combination
		 * 
		 * @param proposal
		 * @param combination
		 */
		boolean[] digitRepetition = new boolean[numberBox];

		for (int i = 0; i < numberBox; i++) {
			digitRepetition[i] = false;
		}

		for (int i = 0; i < numberBox; i++) {
			for (int j = 0; j < numberBox; j++) {
				if (proposal[i] == combination[j] && !digitRepetition[j]) {
					hint[i] = '#';
					digitRepetition[j] = true;
					j++;
				}
			}
		}

		for (int i = 0; i < numberBox; i++) {
			if (proposal[i] == combination[i]) {
				hint[i] = 'O';
			}
		}

		System.out.println(display(hint));
	}

	public void generateOriginalList() {
		/**
		 * Generate a list of integers
		 */
		list = new ArrayList<Integer>();
		for (int i = 0; i < numberDigit; i++)
			list.add(i);
	}

	public void generateList(String modeGame) {
		/**
		 * Generate the List according to proposed combination
		 * 
		 * @param modeGame
		 */
		for (int i = numberBox - 1; i >= 0; i--) {
			if (hint[i] != 'O' && hint[i] != '#' && modeGame == "Defender") {
				list.remove(Integer.valueOf(combination[i]));
			}
			if (hint[i] != 'O' && hint[i] != '#' && modeGame == "Dual") {
				list.remove(Integer.valueOf(proposal[i]));
			}
		}

		for (int i = numberBox - 1; i >= 0; i--) {
			if ((hint[i] == 'O' || hint[i] == '#') && !list.contains(Integer.valueOf(combination[i]))
					&& modeGame == "Defender") {
				list.add(Integer.valueOf(combination[i]));
			}
			if ((hint[i] == 'O' || hint[i] == '#') && !list.contains(Integer.valueOf(proposal[i]))
					&& modeGame == "Dual") {
				list.add(Integer.valueOf(proposal[i]));
			}
		}
	}

	public void generateProposalSearchGame(String gameMode) {
		/**
		 * generate a combination for the search game
		 * 
		 * @param gameMode
		 */
		random = new Random();

		for (int i = 0; i < numberBox; i++) {
			if (hint[i] == '-' && !gameMode.equals("Dual")) {
				combination[i]--;
			}

			if (hint[i] == '-' && gameMode.equals("Dual")) {
				proposal[i]--;
			}

			if (hint[i] == '+' && !gameMode.equals("Dual")) {
				combination[i]++;
			}

			if (hint[i] == '+' && gameMode.equals("Dual")) {
				proposal[i]++;
			}

			if (hint[i] == '_' && !gameMode.equals("Dual")) {
				combination[i] = (int) Math.round(Math.random() * (numberDigit - 1));
			}

			if (hint[i] == '_' && gameMode.equals("Dual")) {
				proposal[i] = (int) Math.round(Math.random() * (numberDigit - 1));
				;
			}
		}
		System.out.println(" ");

		if (!gameMode.equals("Dual")) {
			System.out.print("Proposition de l'ordinateur : ");
			for (int x : combination)
				System.out.print(x);
		} else {
			System.out.print("Proposition de l'ordinateur : ");
			for (int x : proposal)
				System.out.print(x);
		}
		System.out.println(" ");
	}

	public void generateProposalMasterMind(String gameMode) {
		/**
		 * generate a combination for the mastermind
		 * 
		 * @param gameMode
		 */
		random = new Random();
		int numberCount = 0;

		do {
			for (int i = 0; i < numberBox; i++) {
				if (hint[i] != 'O' && !gameMode.equals("Dual")) {
					combination[i] = list.get(random.nextInt(list.size()));
				}
				if (hint[i] != 'O' && gameMode.equals("Dual")) {
					proposal[i] = list.get(random.nextInt(list.size()));
				}
			}

			if (!gameMode.equals("Dual"))
				backup = new ArrayList<Integer>(Arrays.asList(combination));

			if (gameMode.equals("Dual"))
				backup = new ArrayList<Integer>(Arrays.asList(proposal));

			numberCount++;
			if (numberCount >= 20) {
				Main.logger.error("L'ordinateur est incapable de générer une nouvelle proposition");
				generateOriginalList();
			}

		} while (proposalList.contains(backup) && numberCount < 20);

		proposalList.add(backup);

		if (!gameMode.equals("Dual")) {
			System.out.print("Proposition de l'ordinateur : ");
			for (int x : combination)
				System.out.print(x);
			System.out.println("\n");
		}

		if (gameMode.equals("Dual")) {
			System.out.print("Proposition de l'ordinateur : ");
			for (int x : proposal)
				System.out.print(x);
			System.out.println("\n");
		}
	}

	public void displayLapstoGo() {
		/**
		 * Display the number of tests remaining
		 */
		System.out.println("-> Reste " + (numberAuthorizedTrials - numberCompletedTrials) + " tentatives\n");
	}

	public void displayModeChallenger() {
		/**
		 * Display a message for the mode challenger
		 */
		String message = "\n---------------------------------------\n";
		message += "|           MODE CHALLENGER           |\n";
		message += "---------------------------------------\n";
		System.out.println(message);
	}

	public void displayModeDefender() {
		/**
		 * Display a message for the mode defender
		 */
		String message = "\n---------------------------------------\n";
		message += "|           MODE DEFENDER             |\n";
		message += "---------------------------------------\n";
		System.out.println(message);
	}

	public void displayModeDual() {
		/**
		 * Display a message for the mode dual
		 */
		String message = "\n---------------------------------------\n";
		message += "|             MODE DUAL              |\n";
		message += "---------------------------------------\n";
		System.out.println(message);
	}

	public void revealSolution() {
		/**
		 * Reveal the secret combination
		 */
		Main.logger.info("\nLa combinaison secrète était la suivante : \n(" + display(combination) + ")");

	}

	public void logInfoPresentation(String nameGame, String mode) {
		/**
		 * game and mode information
		 * 
		 * @param nameGame
		 * @param mode
		 */
		Main.logger.info("Lancement du jeu " + nameGame + " en mode " + mode);
	}
	
	public void defenderMode(String nameGame) {
		/**
		 * Methode that handle the defender mode
		 * 
		 * @param nameGame
		 */
		combination = new Integer[numberBox];
		numberAuthorizedTrials = numberTest;
		numberCompletedTrials = 0;

		logInfoPresentation(nameGame, "defender");
		displayModeDefender();
		checkInput();

		if (nameGame.equals("MasterMind")) {
			proposalList = new ArrayList<ArrayList<Integer>>();
			generateOriginalList();
		}

		generateHint();

		while (numberCompletedTrials < numberAuthorizedTrials && equalityTest(hint, 0)) {
			displayLapstoGo();
			if (nameGame.equals("SearchGame"))
				generateProposalSearchGame("noDual");
			if (nameGame.equals("MasterMind"))
				generateProposalMasterMind("noDual");
			generateHint();
			if (nameGame.equals("SearchGame"))
				comparisonSearchGame(combination, proposal);
			if (nameGame.equals("MasterMind")) {
				comparisonMasterMind(combination, proposal);
				generateList("Defender");
			}
			numberCompletedTrials++;
		}

		if (!equalityTest(hint, 0)) {
			System.out.println("\n********L'ordinateur a gagné !********\n");
		} else
			System.out.println("\n********L'ordinateur a perdu !********\n");

		endOfGame(nameGame);
	}

	public void challengerMode(String nameGame) {
		/**
		 * Methode that handle the challenger mode
		 * 
		 * @param nameGame
		 */
		numberAuthorizedTrials = numberTest;
		numberCompletedTrials = 0;

		logInfoPresentation(nameGame, "challenger");
		displayModeChallenger();
		randomCombination();
		generateHint();

		while (numberCompletedTrials < numberAuthorizedTrials && equalityTest(hint, 0)) {
			displayLapstoGo();
			checkInput();
			generateHint();

			if (nameGame.equals("SearchGame"))
				comparisonSearchGame(proposal, combination);

			if (nameGame.equals("MasterMind"))
				comparisonMasterMind(proposal, combination);

			numberCompletedTrials++;
		}

		if (!equalityTest(hint, 0)) {
			System.out.println("********Vous avez gagné !********\n");
		} else {
			System.out.println("********Vous avez perdu !********\n");
			revealSolution();
		}
		endOfGame(nameGame);

	}

	public void dualMode(String nameGame) {
		/**
		 * Method that handle the dual mode
		 * 
		 * @param nameGame
		 */
		proposal = new Integer[numberBox];
		combination = new Integer[numberBox];
		numberAuthorizedTrials = numberTest;
		numberCompletedTrials = 0;
		boolean machineTurn = false;

		logInfoPresentation(nameGame, "dual");
		displayModeDual();

		if (nameGame.equals("MasterMind")) {
			proposalList = new ArrayList<ArrayList<Integer>>();
			generateOriginalList();
		}

		randomCombination();
		generateHint();

		while (numberCompletedTrials < numberAuthorizedTrials && equalityTest(hint, 0)) {
			displayLapstoGo();

			if (nameGame == "SearchGame") {
				generateProposalSearchGame("Dual");
				machineTurn = true;
				generateHint();
				comparisonSearchGame(proposal, combination);
			}

			if (nameGame == "MasterMind") {
				generateProposalMasterMind("Dual");
				machineTurn = true;
				generateHint();
				comparisonMasterMind(proposal, combination);
				generateList("Dual");
			}

			if (equalityTest(hint, 0)) {
				checkInput();
				machineTurn = false;
				generateHint();
				if (nameGame == "SearchGame")
					comparisonSearchGame(proposal, combination);
				if (nameGame == "MasterMind") {
					comparisonMasterMind(proposal, combination);
					generateList("Dual");
				}
			}

			numberCompletedTrials++;
		}

		if (!equalityTest(hint, 0) && machineTurn) {
			System.out.println("\n********L'ordinateur a gagné !********\n");
		} else if (!equalityTest(hint, 0) && !machineTurn)
			System.out.println("\n********Vous avez gagné !********\n");
		else {
			System.out.println("\nNi vous, ni la machine n'avez trouvé la combinaison");
			revealSolution();
		}
		endOfGame(nameGame);
	}

}
