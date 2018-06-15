package currencyCheck;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class will handle all the XML copying and parsing.
 * @author Leon & Matan
 * @version 1.0
 */
public class ParsingXML {
	//NodesLists to hold all the data from the needed tags from the XML file
	private NodeList currenciesNamesNL;
	private NodeList unitNL;
	private NodeList currencyCodeNL;
	private NodeList countryNL;
	private NodeList rateNL;
	private NodeList changeNL;
	private NodeList dateNL;
	
	//Date string
	private String date;
	
	//String array compiled from Countries code + country name
	private String[] codeAndCountries;
	
	//String arrays to hold the string data from the XML file
	private String[] currenciesNamesSTR;
	private String[] currencyCodeSTR;
	private String[] countrySTR;
	
	//int/float arrays to hold the int/float data from the XML file
	private int[] unitInt;
	private float[] rateFloat;
	private float[] changeFloat;
	
	/**
	 * Copying an XML file of a given address to program directory.
	 * @throws IOException
	 * @return 1-Success, 0-Fail
	 */
	public int downloadXML() throws IOException
	{
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL("http://www.boi.org.il/currency.xml").openStream());
	        fout = new FileOutputStream("currenciesRates.xml");

	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }	        
	    }catch(IOException e){
	    	System.out.println("Cannot connect to the internet!");
	    	return 0;
	    } finally {
	    	if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }
	    return 1;
	}
	
	/**
	 * Parsing the XML data into the correct array(string/int/float).
	 * @return The length of all the arrays.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public int parseXML() throws SAXException, IOException, ParserConfigurationException
	{

		File file = new File("currenciesRates.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = (Document)builder.parse(file);
		
		//Parsing date from XML to string date
		dateNL = ((org.w3c.dom.Document) doc).getElementsByTagName("LAST_UPDATE");
		date = dateNL.item(0).getFirstChild().getNodeValue();
		
		//Parsing and coping string data from XML to string arrays
		currenciesNamesNL = ((org.w3c.dom.Document) doc).getElementsByTagName("NAME");
		copyNodeToString(1);
		currencyCodeNL = ((org.w3c.dom.Document) doc).getElementsByTagName("CURRENCYCODE");
		copyNodeToString(2);
		countryNL = ((org.w3c.dom.Document) doc).getElementsByTagName("COUNTRY");
		copyNodeToString(3);

		//Parsing and coping int/float data from XML to int/float arrays
		unitNL = ((org.w3c.dom.Document) doc).getElementsByTagName("UNIT");
		copyNodeToIntFloat(1);
		rateNL = ((org.w3c.dom.Document) doc).getElementsByTagName("RATE");
		copyNodeToIntFloat(2);
		changeNL = ((org.w3c.dom.Document) doc).getElementsByTagName("CHANGE");
		copyNodeToIntFloat(3);
		
		setCountriesAndCodeString();
		
		return currenciesNamesSTR.length+currencyCodeSTR.length+countrySTR.length+unitInt.length+rateFloat.length+changeFloat.length;
	}
	
	/**
	 * Copy the data from a NodeList to a String array
	 * @param flag
	 */
	private void copyNodeToString(int flag)
	{
       switch (flag) {
	       case 1:  
	       {
	    	   	currenciesNamesSTR = new String[currenciesNamesNL.getLength()+1];
	   			for(int i=0; i<currenciesNamesSTR.length-1; i++)
	   			{
	   				currenciesNamesSTR[i+1]=currenciesNamesNL.item(i).getFirstChild().getNodeValue();
	   			}  
	   			currenciesNamesSTR[0] = "Shekel";
	   			break;
	       }
	       case 2:  
	       {
	    	   	currencyCodeSTR = new String[currencyCodeNL.getLength()+1];
	  			for(int i=0; i<currencyCodeSTR.length-1; i++)
	  			{
	  				currencyCodeSTR[i+1]=currencyCodeNL.item(i).getFirstChild().getNodeValue();
	  			}  
	  			currencyCodeSTR[0] = "NIS";
	  			break;
	       }
	       case 3:  
	       {
	    	   	countrySTR = new String[countryNL.getLength()+1];
	  			for(int i=0; i<currencyCodeSTR.length-1; i++)
	  			{
	  				countrySTR[i+1]=countryNL.item(i).getFirstChild().getNodeValue();
	  			}  
	  			countrySTR[0] = "Israel";
	  			break;
	       }  
	       default: 
	       {
	    	   System.out.println("Invalid flag!");
	    	   break;
	       }
       }
		
	}

	/**
	 *Copy the data from a nodeList to an int/float array
	 * @param flag
	 */
	private void copyNodeToIntFloat(int flag)
	{
       switch (flag) {
	       case 1:  
	       {
	    	   	unitInt = new int[unitNL.getLength()+1];
	   			for(int i=0; i<unitInt.length-1; i++)
	   			{
	   				unitInt[i+1]=Integer.parseInt(unitNL.item(i).getFirstChild().getNodeValue());
	   			}  
	   			unitInt[0] = 1;
	   			break;
	       }
	       case 2:  
	       {
	    	   	rateFloat = new float[rateNL.getLength()+1];
	  			for(int i=0; i<rateFloat.length-1; i++)
	  			{
	  				rateFloat[i+1]=Float.parseFloat(rateNL.item(i).getFirstChild().getNodeValue());
	  			}  
	  			rateFloat[0] = 1.0f;
	  			break;
	       }
	       case 3:  
	       {
	    	   	changeFloat = new float[changeNL.getLength()+1];
	  			for(int i=0; i<changeFloat.length-1; i++)
	  			{
	  				changeFloat[i+1]=Float.parseFloat(changeNL.item(i).getFirstChild().getNodeValue());
	  			}  
	  			changeFloat[0] = 0.0f;
	  			break;
	       }  
	       default: 
	       {
	    	   System.out.println("Invalid flag!");
	    	   break;
	       }
       }
		
	}
	
	/**
	 * Creating a string array consisting of country code + country name 
	 */
	private void setCountriesAndCodeString()
	{
		codeAndCountries = new String[countrySTR.length];
		for(int i=0; i<countrySTR.length; i++)
		{
			codeAndCountries[i] = currencyCodeSTR[i]+"  ("+countrySTR[i]+")";
		}
	}
	
	//Getters
	public String[] getCurrenciesNamesSTR() {
		return currenciesNamesSTR;
	}

	public String[] getCurrencyCodeSTR() {
		return currencyCodeSTR;
	}

	public String[] getCountrySTR() {
		return countrySTR;
	}

	public int[] getUnitInt() {
		return unitInt;
	}

	public float[] getRateFloat() {
		return rateFloat;
	}

	public float[] getChangeFloat() {
		return changeFloat;
	}
	
	public String getDate() {
		return date;
	}
	
	public String[] getCountriesAndCode() {
		return codeAndCountries;
	}
	
	//Setters
	public void setRateFloat(float[] rateFloat) {
		this.rateFloat = rateFloat;
	}

	
	//This method prints all the data we parsed from the XML
	public void printArrays()
	{
//		System.out.println("date: "+date+"\n");
//		for(int i=0; i<currenciesNamesSTR.length; i++)
//		{
//			System.out.print(currenciesNamesSTR[i]+", ");
//		}
//		System.out.println("\n-----------------------------------------------------------------------------------");
//		for(int i=0; i<currencyCodeSTR.length; i++)
//		{
//			System.out.print(currencyCodeSTR[i]+", ");
//		}
//		System.out.println("\n-----------------------------------------------------------------------------------");
//		for(int i=0; i<countrySTR.length; i++)
//		{
//			System.out.print(countrySTR[i]+", ");
//		}
//		System.out.println("\n-----------------------------------------------------------------------------------");
//		for(int i=0; i<unitInt.length; i++)
//		{
//			System.out.print(unitInt[i]+", ");
//		}
//		System.out.println("\n-----------------------------------------------------------------------------------");
		for(int i=0; i<rateFloat.length; i++)
		{
			System.out.print(rateFloat[i]+", ");
		}
//		System.out.println("\n-----------------------------------------------------------------------------------");
//		for(int i=0; i<changeFloat.length; i++)
//		{
//			System.out.print(changeFloat[i]+", ");
//		}
	}
}
