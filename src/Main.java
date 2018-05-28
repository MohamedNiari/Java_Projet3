import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*************************************************************************
 * It manages the developer mode and the LogManager And It launches the method
 * Launching() {@value #developerMode} {@value #logger}
 * 
 * @author Mohamed NIARI
 * @see Main#Launching()
 * @exception NumberFormatException
 *************************************************************************/

public class Main {
	static boolean developerMode = false;
	static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		for (String arg : args)
			if (arg.equals("-d"))
				developerMode = true;

		new Main().Launching();
	}

	/**
	 * The following method proposes the homepage of the different games And to
	 * enable or not the developer mode
	 * 
	 * @exception NumberFormatException
	 */
	public void Launching() {
		int input;

		if (developerMode == true) {
			logger.info("Le mode développeur est activé");
		} else {
			logger.info("Le mode développeur est désactivé");
		}

		do {
			input = 0;
			String Message = "\n---------------------------------------\n";
			Message += "|           PAGE D'ACCUEIL            |\n";
			Message += "---------------------------------------\n";
			Message += "A quel jeu souhaitez-vous jouez ?\n";
			Message += "\n1 : Mastermind\n";
			Message += "2 : Recherche";
			Scanner scanner = new Scanner(System.in);

			try {
				System.out.println(Message);
				input = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
			}

		} while (input != 1 && input != 2);

		if (input == 1) {
			new MasterMind();
		}

		if (input == 2) {
			new SearchGame();
		}
	}
}
