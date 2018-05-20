
public class MasterMind extends Game {

	public MasterMind() {

		ReadConfig("Resources/config.properties");

		String message = "\n---------------------------------------\n";
		message += "|           MASTERMIND                |\n";
		message += "---------------------------------------\n";
		message += "Choisissez le Mode souhaité\n";
		message += "\n1 : Challenger\n";
		message += "2 : Defenseur\n";
		message += "3 : Duel";

		int choix = Presentation(message);

		switch (choix) {
		case 1:
			ChallengerMode("MasterMind");
			break;
		case 2:
			DefenderMode("MasterMind");
			break;
		case 3:
			DualMode("MasterMind");
			break;
		}

	}

}
