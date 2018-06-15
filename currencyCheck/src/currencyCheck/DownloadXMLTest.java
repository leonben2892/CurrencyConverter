package currencyCheck;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * Testing the downloadXML method in ParsingXML class.
 * @author Leon & Matan
 */
public class DownloadXMLTest {

	@Test
	public void test() throws IOException {
		ParsingXML parseTest = new ParsingXML();
		int output = parseTest.downloadXML();
		assertEquals(1,output);
	}

}
