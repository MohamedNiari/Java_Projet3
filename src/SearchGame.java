
public class SearchGame extends Game {

	public SearchGame() {

		ReadConfig("Resources/config.properties");

		String message = "\n---------------------------------------\n";
		message += "|           JEU RECHERCHE +/-         |\n";
		message += "---------------------------------------\n";
		message += "Choisissez le Mode souhaité\n";
		message += "\n1 : Challenger\n";
		message += "2 : Defenseur\n";
		message += "3 : Duel";

		int choix = Presentation(message);

		switch (choix) {
		case 1:
			ChallengerMode("SearchGame");
			break;
		case 2:
			DefenderMode("SearchGame");
			break;
		case 3:
			DualMode("SearchGame");
			break;
		}

	}

}
