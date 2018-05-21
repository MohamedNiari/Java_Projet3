import java.util.Scanner;

public class Program {

	static boolean modeDeveloper = false;
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		for (String arg : args)
			if (arg.equals("-d"))
				modeDeveloper = true;

		new Program().Launching();
		scanner.close();
	}

	public void Launching() {	
		int input;

		if (modeDeveloper == true) {
			System.out.println("***************************************");
			System.out.println("*** Le mode développeur est activé ****\n");
		}

		do {
			input = 0;
			String Message = "\n---------------------------------------\n";
			Message += "|           PAGE D'ACCUEIL            |\n";
			Message += "---------------------------------------\n";
			Message += "A quel jeu souhaitez-vous jouez ?\n";
			Message += "\n1 : Mastermind\n";
			Message += "2 : Recherche";

			try {
				System.out.println(Message);
				input = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {}

		} while (input != 1 && input != 2);
		
		if (input == 1) {
			new MasterMind();
		}

		if (input == 2) {
			new SearchGame();
		}
	}
}
