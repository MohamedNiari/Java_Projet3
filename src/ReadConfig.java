import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {
	protected int numberBox;
	protected int numberDigit;
	protected int numberTest;
	protected boolean modeDeveloper;
	
	public ReadConfig() {
		/**
		 * Read the config properties
		 * 
		 * @param fichier
		 * @exception FileNotFoundException
		 * @exception IOException
		 */
		Main.logger.info("Récupération des paramètres de configuration.");
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("Resources/config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Main.logger.info("The file config.properties does not exists !");
		} catch (IOException e) {

			e.printStackTrace();
		}
		String box = prop.getProperty("numberBox");
		String digit = prop.getProperty("numberDigit");
		String test = prop.getProperty("numberTest");
		String dev = prop.getProperty("modeDeveloper");
		numberBox = Integer.parseInt(box);
		numberDigit = Integer.parseInt(digit);
		numberTest = Integer.parseInt(test);
		modeDeveloper = Boolean.parseBoolean(dev);
		
	}
	
	public boolean isModeDeveloper() {
		return modeDeveloper;
	}
	
	public void setModeDeveloper(boolean modeDeveloper) {
		this.modeDeveloper = modeDeveloper;
	}
	
	public void displayModeDeveloper(){
		

		if (modeDeveloper == true) {
			Main.logger.info("Le mode développeur est activé");
			Main.logger.warn("\nNumber of Box is " + numberBox + "\nNumber of Digit is " + numberDigit
					+ "\nNumber of Trials is " + numberTest);
		}
		else {
			Main.logger.info("Le mode développeur est désactivé");
		}
		
	}
	
}
