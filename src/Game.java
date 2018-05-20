import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public abstract class Game {

	protected int numberBox;
	protected int numberDigit;
	protected int numberTest;
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

	public void CheckInput() {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String message;
		String[] output;

		proposal = new Integer[numberBox];
		boolean entry = false;

		message = "\nMerci de saisir une série de " + numberBox + " chiffres ";
		message += "compris entre 0 et " + (numberDigit - 1);

		do {
			System.out.println(message);
			output = scanner.nextLine().split("");
			try {
				for (int i = 0; i < numberBox; i++) {
					if (output.length == numberBox && !EqualityTest(output, 0)) {
						proposal[i] = Integer.valueOf(output[i]).intValue();
						entry = true;
					}
				}
			} catch (NumberFormatException e) {
			}
		} while (!entry);

	}

	public void GenerateHint() {

		hint = new char[numberBox];
		for (int i = 0; i < numberBox; i++)
			hint[i] = '_';

	}

	public String Display(char[] tab) {
		String str = "";
		for (char x : tab)
			str += x;
		return str;
	}

	public boolean EqualityTest(char[] tab, int n) {
		if (n > tab.length - 1) {
			return false;
		}

		if (tab[n] != 'O' && tab[n] != '=')
			return true;
		else
			return EqualityTest(tab, n + 1);
	}

	public boolean EqualityTest(String[] tab, int n) {
		if (n > tab.length - 1) {
			return false;
		}

		if (Integer.valueOf(tab[n]) > numberDigit - 1)
			return true;
		else
			return EqualityTest(tab, n + 1);
	}

	public void ReadConfig(String fichier) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(fichier);
			prop.load(input);
		} catch (FileNotFoundException e) {
			/**
			 * <p>
			 * Va gérer l'exception en cas d'absence de fichier
			 * </p>
			 */
			e.printStackTrace();
		} catch (IOException e) {
			/**
			 * <p>
			 * Exception levée pour la gestion des input/output
			 * </p>
			 */
			e.printStackTrace();
		}
		String box = prop.getProperty("numberBox");
		String digit = prop.getProperty("numberDigit");
		String test = prop.getProperty("numberTest");
		numberBox = Integer.parseInt(box);
		numberDigit = Integer.parseInt(digit);
		numberTest = Integer.parseInt(test);
	}

	public int Presentation(String message) {

		int Input;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		do {
			Input = 0;
			try {
				System.out.println(message);
				Input = scanner.nextInt();

			} catch (InputMismatchException e) {
				scanner.next();
			}

		} while (Input != 1 && Input != 2 && Input != 3);

		return Input;

	}

	@SuppressWarnings("static-access")
	public void EndOfGame(String nameGame) {

		String message = "---------------------------------------\n";
		message += "|           FIN DU JEU                |\n";
		message += "---------------------------------------\n";
		message += "Que choisissez-vous de faire ?\n";
		message += "\n1 : Rejouer au même jeu\n";
		message += "2 : Jouer un autre jeu\n";
		message += "3 : Quitter le jeu";

		int choix = Presentation(message);

		switch (choix) {
		case 1:
			if (nameGame == "SearchGame")
				new SearchGame();
			if (nameGame == "MasterMind")
				new MasterMind();
			break;
		case 2:
			new Program().Launching();
			break;
		case 3:
			System.out.println("---------------------------------------");
			System.out.println("|    VOUS QUITTEZ L'APPLICATION !     |");
			System.out.println("---------------------------------------");
			break;
		}

	}

	public void RandomCombination() {

		combination = new Integer[numberBox];

		for (int i = 0; i < numberBox; i++) {
			int n = (int) Math.round(Math.random() * (numberDigit - 1));
			combination[i] = n;
		}

		System.out.println("\nNous venons de choisir la combinaison");

		if (Program.modeDeveloper == true) {
			System.out.print("(");
			for (int x : combination)
				System.out.print(x);
			System.out.println(")");
		}

	}

	public void ComparisonSearchGame(Integer[] proposal, Integer[] combination) {

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

		System.out.println(Display(hint));

	}

	public void ComparisonMasterMind(Integer[] proposal, Integer[] combination) {

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

		System.out.println(Display(hint));

	}

	public void GenerateOriginalList() {
		list = new ArrayList<Integer>();
		for (int i = 0; i < numberDigit; i++)
			list.add(i);

	}

	public void GenerateList(String modeGame) {

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

	public void GenerateProposalSearchGame(String gameMode) {
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

	public void GenerateProposalMasterMind(String gameMode) {

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
			if (numberCount >= 20)
				System.out.println("\n*L'ordinateur est incapable de générer une nouvelle proposition");

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

	public void DisplayLapstoGo() {

		System.out.println("-> Reste " + (numberAuthorizedTrials - numberCompletedTrials) + " tentatives\n");
	}

	public void DefenderMode(String nameGame) {

		combination = new Integer[numberBox];
		numberAuthorizedTrials = numberTest;
		numberCompletedTrials = 0;

		DisplayModeDefender();
		CheckInput();
		if (nameGame.equals("MasterMind")) {
			proposalList = new ArrayList<ArrayList<Integer>>();
			GenerateOriginalList();
		}

		GenerateHint();

		while (numberCompletedTrials < numberAuthorizedTrials && EqualityTest(hint, 0)) {
			DisplayLapstoGo();
			if (nameGame.equals("SearchGame"))
				GenerateProposalSearchGame("noDual");
			if (nameGame.equals("MasterMind"))
				GenerateProposalMasterMind("noDual");
			GenerateHint();
			if (nameGame.equals("SearchGame"))
				ComparisonSearchGame(combination, proposal);
			if (nameGame.equals("MasterMind")) {
				ComparisonMasterMind(combination, proposal);
				GenerateList("Defender");
			}
			numberCompletedTrials++;
		}

		if (!EqualityTest(hint, 0)) {
			System.out.println("\n********L'ordinateur a gagné !********\n");
		} else
			System.out.println("\n********L'ordinateur a perdu !********\n");

		EndOfGame(nameGame);
	}

	public void ChallengerMode(String nameGame) {

		numberAuthorizedTrials = numberTest;
		numberCompletedTrials = 0;

		DisplayModeChallenger();
		RandomCombination();
		GenerateHint();

		while (numberCompletedTrials < numberAuthorizedTrials && EqualityTest(hint, 0)) {
			DisplayLapstoGo();
			CheckInput();
			GenerateHint();

			if (nameGame.equals("SearchGame"))
				ComparisonSearchGame(proposal, combination);

			if (nameGame.equals("MasterMind"))
				ComparisonMasterMind(proposal, combination);

			numberCompletedTrials++;
		}

		if (!EqualityTest(hint, 0)) {
			System.out.println("********Vous avez gagné !********\n");
		} else {
			System.out.println("********Vous avez perdu !********\n");
			RevealSolution();
		}
		EndOfGame(nameGame);

	}

	public void DualMode(String nameGame) {

		proposal = new Integer[numberBox];
		combination = new Integer[numberBox];
		numberAuthorizedTrials = numberTest;
		numberCompletedTrials = 0;
		boolean machineTurn = false;

		DisplayModeDual();

		if (nameGame.equals("MasterMind")) {
			proposalList = new ArrayList<ArrayList<Integer>>();
			GenerateOriginalList();
		}

		RandomCombination();
		GenerateHint();

		while (numberCompletedTrials < numberAuthorizedTrials && EqualityTest(hint, 0)) {

			DisplayLapstoGo();

			if (nameGame == "SearchGame") {
				GenerateProposalSearchGame("Dual");
				machineTurn = true;
				GenerateHint();
				ComparisonSearchGame(proposal, combination);
			}

			if (nameGame == "MasterMind") {
				GenerateProposalMasterMind("Dual");
				machineTurn = true;
				GenerateHint();
				ComparisonMasterMind(proposal, combination);
				GenerateList("Dual");
			}

			if (EqualityTest(hint, 0)) {
				CheckInput();
				machineTurn = false;
				GenerateHint();
				if (nameGame == "SearchGame")
					ComparisonSearchGame(proposal, combination);
				if (nameGame == "MasterMind") {
					ComparisonMasterMind(proposal, combination);
					GenerateList("Dual");
				}
			}

			numberCompletedTrials++;
		}

		if (!EqualityTest(hint, 0) && machineTurn) {
			System.out.println("\n********L'ordinateur a gagné !********\n");
		} else if (!EqualityTest(hint, 0) && !machineTurn)
			System.out.println("\n********Vous avez gagné !********\n");
		else {
			System.out.println("\nNi vous, ni la machine n'avez trouvé la combinaison");
			RevealSolution();
		}
		EndOfGame(nameGame);

	}

	public void DisplayModeChallenger() {

		String message = "\n---------------------------------------\n";
		message += "|           MODE CHALLENGER           |\n";
		message += "---------------------------------------\n";
		System.out.println(message);

	}

	public void DisplayModeDefender() {

		String message = "\n---------------------------------------\n";
		message += "|           MODE DEFENDER             |\n";
		message += "---------------------------------------\n";
		System.out.println(message);

	}

	public void DisplayModeDual() {

		String message = "\n---------------------------------------\n";
		message += "|             MODE DUAL              |\n";
		message += "---------------------------------------\n";
		System.out.println(message);

	}

	public void RevealSolution() {

		System.out.println("\nLa combinaison secrète était la suivante : ");

		System.out.print("(");
		for (int x : combination)
			System.out.print(x);
		System.out.println(")");

	}
}
