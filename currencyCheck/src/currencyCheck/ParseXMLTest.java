package currencyCheck;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Testing the parseXML method in ParsingXML class.
 * @author Leon & Matan
 */
public class ParseXMLTest {

	@Test
	public void test() throws SAXException, IOException, ParserConfigurationException {
		ParsingXML parseTest = new ParsingXML();
		int output = parseTest.parseXML();
		assertEquals(90,output);
	}

}
