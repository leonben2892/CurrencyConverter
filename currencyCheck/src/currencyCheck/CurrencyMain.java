package currencyCheck;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CurrencyMain {

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

		ParsingXML xmlToParse = new ParsingXML();
		xmlToParse.downloadXML();
		xmlToParse.parseXML();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run(){
				try {
					CurrencyConverterGUI ccg = new CurrencyConverterGUI(xmlToParse);
				} catch (IOException e) {
					System.out.println("Couldn't load GUI.");
					e.printStackTrace();
				}
			}
		});		
	}

}
