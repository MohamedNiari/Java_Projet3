
public class SearchGame extends Game {

	public SearchGame() {

		readConfig("Resources/config.properties");

		String message = "\n---------------------------------------\n";
		message += "|           JEU RECHERCHE +/-         |\n";
		message += "---------------------------------------\n";
		message += "Choisissez le Mode souhaité\n";
		message += "\n1 : Challenger\n";
		message += "2 : Defenseur\n";
		message += "3 : Duel";

		int choix = presentation(message);

		switch (choix) {
		case 1:
			challengerMode("SearchGame");
			break;
		case 2:
			defenderMode("SearchGame");
			break;
		case 3:
			dualMode("SearchGame");
			break;
		}

	}

}
