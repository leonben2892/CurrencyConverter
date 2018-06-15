package currencyCheck;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Holds the GUI for the rates table.
 * @author Leon & Matan
 */
public class RatesTableGUI {
	private JFrame ratesTableFrame; 				//Rates Table Frame
	
	private JLabel ratesTableHeadline;				//Rates table headline: "Currency Exchange Rate Table"
	private JLabel currExchangeRateDate;			//Label for the date: "Current Date:"
	private JLabel currExchangeRateDateData;		//Label to hold the current date
	private JLabel currTableSearch;					//Label for currency search: "Search Currency:"
	
	private JTextField currTableSearchTF;			//Hold the currency that needs to be searched
	
	private JTable ratesTableData;					//Table to hold all the rates data
	
	private JScrollPane tableScrollPane;			//Hold the rates table
	
	private DefaultTableModel model;
	private TableRowSorter<TableModel> sorter; 

	
	 public RatesTableGUI(ParsingXML xmlData)
	 {	
		 
		//Lines 20 to 34 is for making the table headlines
		String[] currCode = xmlData.getCurrencyCodeSTR();		//Holds all the country codes
		int[] currUnit = xmlData.getUnitInt();
		String[] currCode2 = new String[currCode.length+1];		//Will hold all the country codes with empty cell in the beginning
		for(int p=0;p<currCode2.length;p++)
		{
			if(p==0)
			{
				currCode2[p] = " ";
			}
			else
			{
				currCode2[p] = currCode[p-1]+"("+currUnit[p-1]+")";
			}
		}
		
		//Lines 36 to 57 is to insert the correct data to tableContent matrix
		float[] currRates = xmlData.getRateFloat();
		String[][] tableContent = new String[15][16];
		for(int i=0; i<15; i++)
		{
			for(int j=0; j<16;j++)
			{			
				if(j==0)
				{
					tableContent[i][j] = currCode[i]+"("+currUnit[i]+")";
					float tmpCurr = currRates[i];
					for(int k=0; k<currRates.length; k++)
					{
						currRates[k] = currRates[k]/tmpCurr;
					}
				}
				else
				{
					tableContent[i][j] = Float.toString(currRates[j-1]);
				}			
			}
		}
		
		model = new DefaultTableModel (tableContent,currCode2);
		sorter = new TableRowSorter<>(model);
		
		//Rates table
		ratesTableFrame = new JFrame("Rates Table");
		ratesTableFrame.setResizable(false);
		ratesTableData = new JTable(model);
		ratesTableData.setRowHeight(25);
		ratesTableData.setFont(ratesTableData.getFont().deriveFont(14.0f));
		ratesTableData.setRowSorter(sorter);
		tableScrollPane = new JScrollPane(ratesTableData);
		tableScrollPane.setBounds(16,40, 1300, 399);	
		ratesTableFrame.add(tableScrollPane);

		
		//Rates table headline: "Currency Exchange Rate Table"
		ratesTableHeadline = new JLabel("Currency Exchange Rate Table");
		ratesTableHeadline.setBounds(540, 0, 330, 50);
		ratesTableHeadline.setFont(ratesTableHeadline.getFont().deriveFont(20.0f));
		ratesTableFrame.add(ratesTableHeadline);
		
		//Label for date: "Current Date:"
		currExchangeRateDate = new JLabel("Current Date:");
		currExchangeRateDate.setFont(currExchangeRateDate.getFont().deriveFont(16.0f));
		currExchangeRateDate.setBounds(16, 0, 120, 50);
		ratesTableFrame.add(currExchangeRateDate);
		
		//Label to hold the current data
		currExchangeRateDateData = new JLabel(xmlData.getDate());
		currExchangeRateDateData.setBounds(126,0,120,50);
		currExchangeRateDateData.setFont(currExchangeRateDateData.getFont().deriveFont(16.0f));
		currExchangeRateDateData.setForeground(Color.BLUE);
		ratesTableFrame.add(currExchangeRateDateData);
		
		//Label for currency search: "Search Currency:"
		currTableSearch = new JLabel("Search currency:");
		currTableSearch.setBounds(930,0, 270, 50);
		currTableSearch.setFont(currTableSearch.getFont().deriveFont(16.0f));
		ratesTableFrame.add(currTableSearch);
		
		//Hold the currency that needs to be searched
		currTableSearchTF = new JTextField();
		currTableSearchTF.setBounds(1065,11, 100, 25);
		currTableSearchTF.setFont(currTableSearchTF.getFont().deriveFont(16.0f));
		currTableSearchTF.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				filterRow();
				
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				filterRow();
				
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				filterRow();
				
			}
			  public void filterRow(){
		          String text = currTableSearchTF.getText().toUpperCase();
		          if (text.trim().length() == 0) {
		              sorter.setRowFilter(null);
		          } else {
		             sorter.setRowFilter(RowFilter.regexFilter(text));
		          }				  
				}
			});
		ratesTableFrame.add(currTableSearchTF);
				
		//General frame required data
		ratesTableFrame.setLayout(null);
		ratesTableFrame.setSize(1350,500);
		ratesTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ratesTableFrame.setVisible(true);
	 }
}
