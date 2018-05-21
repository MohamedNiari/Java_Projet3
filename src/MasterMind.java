
public class MasterMind extends Game {

	public MasterMind() {

		readConfig("Resources/config.properties");

		String message = "\n---------------------------------------\n";
		message += "|           MASTERMIND                |\n";
		message += "---------------------------------------\n";
		message += "Choisissez le Mode souhaité\n";
		message += "\n1 : Challenger\n";
		message += "2 : Defenseur\n";
		message += "3 : Duel";

		int choix = presentation(message);

		switch (choix) {
		case 1:
			challengerMode("MasterMind");
			break;
		case 2:
			defenderMode("MasterMind");
			break;
		case 3:
			dualMode("MasterMind");
			break;
		}

	}

}
