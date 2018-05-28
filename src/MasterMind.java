/*************************************************************************
 * This is the class mastermind that proposes to launch different modes of the
 * game
 *************************************************************************/
public class MasterMind extends Game {

	/**
	 * It's the constructor of the class that read the config.properties
	 * 
	 * @see Game#readConfig(String)
	 * @see Game#challengerMode(String)
	 * @see Game#defenderMode(String)
	 * @see Game#dualMode(String)
	 */

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
