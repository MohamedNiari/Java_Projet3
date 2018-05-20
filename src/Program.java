import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

	static boolean modeDeveloper = false;

	public static void main(String[] args) {
		for (String arg : args)
			if (arg.equals("-d"))
				modeDeveloper = true;

		Launching();

	}

	public static void Launching() {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		int Input;

		if (modeDeveloper == true) {
			System.out.println("***************************************");
			System.out.println("*** Le mode développeur est activé ****\n");
		}

		do {
			Input = 0;

			String Message = "\n---------------------------------------\n";
			Message += "|           PAGE D'ACCUEIL            |\n";
			Message += "---------------------------------------\n";
			Message += "A quel jeu souhaitez-vous jouez ?\n";
			Message += "\n1 : Mastermind\n";
			Message += "2 : Recherche";

			try {
				System.out.println(Message);

				Input = scanner.nextInt();

			} catch (InputMismatchException e) {
				scanner.next();
			}

		} while (Input != 1 && Input != 2);

		scanner.nextLine();

		if (Input == 1) {
			new MasterMind();
		}

		if (Input == 2) {
			new SearchGame();

		}

	}

}
