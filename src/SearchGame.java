/*************************************************************************
 * This is the class searchgame that proposes to launch different modes of the
 * game
 *************************************************************************/
public class SearchGame extends Game {

	/**
	 * It's the constructor of the class that read the config.properties
	 * 
	 * @see Game#readConfig(String)
	 * @see Game#challengerMode(String)
	 * @see Game#defenderMode(String)
	 * @see Game#dualMode(String)
	 */

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
