package currencyCheck;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Holds the GUI for the currency converter calculator.
 * @author Leon & Matan
 */
public class CurrencyConverterGUI{
	private JFrame converterFrame; 					//Currency Converter frame
	
	private JLabel converterHeadlineLB;				//"Matan & Leon Currency Converter" label
	private JLabel currentDateLB;					//"Current Date:" label
	private JLabel currentDateDataLB;				//Holds the current date
	private JLabel currencyIhaveLB;					//"Currency I Have:" label
	private JLabel currencyIwantLB; 				//"Currency I Want:" label
	private JLabel lineLB;							//Draws a line
	private JLabel currentTimeLB;					//"Current Time:" label
	private JLabel currentTimeDataLB;				//Holds the current time
	
	private JTextField sumToConvertTF;				//Hold the sum we want to convert
	private JTextField convertResultTF;				//Holds the result of the conversion
			
	private JButton ConvertButton;					//Convert button
	private JButton tableBtn;						//Button to opens rates table
	private JButton updateBtn;						//Button to update to the up to date data
	private JButton aboutBtn;						//Button to show the programmers details
	private JButton switchBtn;						//Button to hold the icon of switch button
	private JButton resetBtn;						//Button to reset all of the components
	
	private JComboBox<Object> currencyIHaveCB; 		//Holds all the countries names + countries code
	private JComboBox<Object> currencyIWantCB;		//Holds all the countries names + countries code
	
	private Calendar cal;							//Variable for getting the current time
	private SimpleDateFormat sdf;					//Variable for choosing a format to show the time
	
	private ImageIcon switchIcon;					//Icon of switch button

	private ParsingXML prsXML;						//Holds all the XML data
	
	/*Writing to a file variables*/
	File fout; 
	FileOutputStream fos;
	BufferedWriter bw;
	
	Dimension dim;									//Variable to hold screen size(helping in opening the application in the middle of the screen)
		
	/*Listeners required variables*/
	private float wantedCurrencyRate;				//Hold the currency rate we want to convert to
	private float amountToConvert;					//Hold the amount of money we want to convert
	private float finalConversionAmount;			//Hold the final amount of the conversion after all the calculations
	private int wantedCurrencyUnit;					//Hold the unit of the finalConversionAmount currency	
	
	@SuppressWarnings("unchecked")
	public CurrencyConverterGUI(ParsingXML xmlToParse) throws IOException {
		 fout = new File("Application log file.txt"); 
		 fos = new FileOutputStream(fout);
		 bw = new BufferedWriter(new OutputStreamWriter(fos));
		 prsXML = xmlToParse;
		 wantedCurrencyRate = 0;
		 bw.write("Currency Converter application opened!");
		 bw.newLine();

		//General frame required data
		converterFrame = new JFrame("Currency Converter");
		converterFrame.setResizable(false);
		converterFrame.setLayout(null);
		converterFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		converterFrame.setSize(1000,500);	
		converterFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src//resources//appBackground.jpg")))));
		converterFrame.setVisible(true);
		converterFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		try {
		    			bw.write("Currency Converter application closed!");
						bw.close();
					} catch (IOException e) {
						System.out.println("Couldn't write to log file.");
						e.printStackTrace();
					}
		    }
		});
		
		//Makes the application open mid screen
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		converterFrame.setLocation(dim.width/2-converterFrame.getSize().width/2, dim.height/2-converterFrame.getSize().height/2);
					
		//Application Headline label: "Matan & Leon Currency Converter"
		converterHeadlineLB = new JLabel("Matan & Leon Currency Converter");
		converterHeadlineLB.setBounds(370,0,340,50);
		converterHeadlineLB.setFont(converterHeadlineLB.getFont().deriveFont(20.0f));
		converterHeadlineLB.setForeground(new Color(153, 153, 153));
		converterFrame.add(converterHeadlineLB);
		
		//Date label: "Current Date:"
		currentDateLB = new JLabel("Current Date: ");
		currentDateLB.setBounds(10,0,200,100);
		currentDateLB.setFont(currentDateLB.getFont().deriveFont(17.0f));
		currentDateLB.setForeground(new Color(153, 153, 153));
		converterFrame.add(currentDateLB);
		//Date label to hold the current date
		currentDateDataLB = new JLabel(prsXML.getDate());
		currentDateDataLB.setBounds(120,40,100,20);
		currentDateDataLB.setFont(currentDateDataLB.getFont().deriveFont(19.0f));
		currentDateDataLB.setForeground(new Color(204,163,0));
		converterFrame.add(currentDateDataLB);
		
		//Time label: "Current Time:"
		currentTimeLB = new JLabel("Current Time:");
		currentTimeLB.setBounds(10,24,200,100);
		currentTimeLB.setFont(currentDateLB.getFont().deriveFont(17.0f));
		currentTimeLB.setForeground(new Color(153, 153, 153));
		converterFrame.add(currentTimeLB);
		//Time label to hold the current time
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm");
        currentTimeDataLB = new JLabel(sdf.format(cal.getTime()));
        currentTimeDataLB.setBounds(120, 65, 100, 20);
        currentTimeDataLB.setFont(currentTimeDataLB.getFont().deriveFont(19.0f));
        currentTimeDataLB.setForeground(new Color(204,163,0));
        converterFrame.add(currentTimeDataLB);
		
		//Currency I have label: "Currency I Have:"
		currencyIhaveLB= new JLabel("Currency I Have:");
		currencyIhaveLB.setBounds(130,100,150,40);
		currencyIhaveLB.setFont(currencyIhaveLB.getFont().deriveFont(17.0f));
		currencyIhaveLB.setForeground(new Color(153, 153, 153));
		converterFrame.add(currencyIhaveLB);
		//Currency I have ComboBox
		currencyIHaveCB = new JComboBox<Object>();
		currencyIHaveCB.setModel(populate(prsXML));
		currencyIHaveCB.setRenderer(new ImagesTextRenderer());
		currencyIHaveCB.setBounds(285,100,150,40);
		currencyIHaveCB.setSelectedIndex(0);
		currencyIHaveCB.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
                float[] tmpRates = prsXML.getRateFloat();
                String[] tmpCodeAndCountry = prsXML.getCountriesAndCode();
                if(currencyIHaveCB.getSelectedIndex() != 0)
                {
                    float sourceCurrencyRate=tmpRates[currencyIHaveCB.getSelectedIndex()-1];
                    for(int j=0; j<tmpRates.length; j++)
                    {
                    	tmpRates[j] = tmpRates[j]/sourceCurrencyRate;
                    }
                    Thread setRates = new Thread(new Runnable() {
                    	  public void run() {
                    		  prsXML.setRateFloat(tmpRates);
                    	  }
                    	});
                    setRates.start();                                 
                    try {
						bw.write("User chose: "+tmpCodeAndCountry[currencyIHaveCB.getSelectedIndex()-1]+" as the currency he has.");
						bw.newLine();
					} catch (IOException e1) {
						System.out.println("Couldn't write to log file.");
						e1.printStackTrace();
					}
                }
		    }
		});
		converterFrame.add(currencyIHaveCB);
		
		//Currency I want label: "Currency I want:"
		currencyIwantLB= new JLabel("Currency I Want:");
		currencyIwantLB.setBounds(130,200,150,40);
		currencyIwantLB.setFont(currencyIwantLB.getFont().deriveFont(17.0f));
		currencyIwantLB.setForeground(new Color(153, 153, 153));
		converterFrame.add(currencyIwantLB);
		//Currency I want ComboBox
		currencyIWantCB = new JComboBox<Object>();
		currencyIWantCB.setModel(populate(prsXML));
		currencyIWantCB.setRenderer(new ImagesTextRenderer());
		currencyIWantCB.setBounds(285,200,150,40);
		currencyIWantCB.setSelectedIndex(0);
		currencyIWantCB.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
                float[] tmpRates = prsXML.getRateFloat();
                int[] tmpUnits = prsXML.getUnitInt();
                String[] tmpCodeAndCountry = prsXML.getCountriesAndCode();
                if(currencyIWantCB.getSelectedIndex() != 0)
                {
                    wantedCurrencyRate = tmpRates[currencyIWantCB.getSelectedIndex()-1];
            		wantedCurrencyUnit = tmpUnits[currencyIWantCB.getSelectedIndex()-1];
                    try {
						bw.write("User chose: "+tmpCodeAndCountry[currencyIWantCB.getSelectedIndex()-1]+" as the currency he wants.");
						bw.newLine();
					} catch (IOException e1) {
						System.out.println("Couldn't write to log file.");
						e1.printStackTrace();
					}
                }
		    }
		});
		converterFrame.add(currencyIWantCB);

		//Text field to hold the sum we want to convert			 
		sumToConvertTF = new JTextField();
		sumToConvertTF.setFont(sumToConvertTF.getFont().deriveFont(20.0f));
		sumToConvertTF.setBounds(680,100,150,40);			
		sumToConvertTF.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				amountToConvertChanges();
				
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				amountToConvertChanges();
				
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				amountToConvertChanges();
				
			}
			  public void amountToConvertChanges(){
				 if(sumToConvertTF.getText().equals("")==false && sumToConvertTF.getText().charAt(0) != '.')
				 {
					 amountToConvert = Float.parseFloat(sumToConvertTF.getText());
				 }
			  }

			});
		sumToConvertTF.addKeyListener(new KeyAdapter() {
		        public void keyTyped(KeyEvent e) {
		          char c = e.getKeyChar();
		          if (!((c >= '0') && (c <= '9') ||(c == KeyEvent.VK_BACK_SPACE) ||(c == KeyEvent.VK_DELETE)||(c == KeyEvent.VK_PERIOD))) 
		          {
		            e.consume();
		          }
		        }
		      });
		converterFrame.add(sumToConvertTF);
		
		//Text field that will hold the result of the conversion
		convertResultTF = new JTextField();
		convertResultTF.setBounds(680,200,150,40);
		convertResultTF.setEditable(false);
		convertResultTF.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		convertResultTF.setFont(convertResultTF.getFont().deriveFont(20.0f));
		convertResultTF.setFont(sumToConvertTF.getFont().deriveFont(Font.BOLD));
		converterFrame.add(convertResultTF);
		
		//Convert button
		ConvertButton= new JButton("Convert");
		ConvertButton.setFont(ConvertButton.getFont().deriveFont(17.0f));
		ConvertButton.setBounds(480,125,150,40);
		ConvertButton.setBackground(new Color(226, 225, 225));
		ConvertButton.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(currencyIHaveCB.getSelectedIndex() != 0 && currencyIWantCB.getSelectedIndex() != 0 && sumToConvertTF.getText() != "")
		    	{
		    		int[] tmpUnits = prsXML.getUnitInt();
		    		finalConversionAmount=((amountToConvert/wantedCurrencyRate)*wantedCurrencyUnit)/tmpUnits[currencyIHaveCB.getSelectedIndex()-1];
			    	convertResultTF.setText(Float.toString(finalConversionAmount));
			    	String[] tmpCodeAndCountry = prsXML.getCountriesAndCode();
                    try {
						bw.write("User converted: "+amountToConvert+" from: "+tmpCodeAndCountry[currencyIHaveCB.getSelectedIndex()-1]+" to: "+tmpCodeAndCountry[currencyIWantCB.getSelectedIndex()-1]+" and the result is: "+finalConversionAmount+" "+tmpCodeAndCountry[currencyIWantCB.getSelectedIndex()-1]);
						bw.newLine();
					} catch (IOException e1) {
						System.out.println("Couldn't write to log file.");
						e1.printStackTrace();
					}
		    	}	 
		    }
		});
		converterFrame.add(ConvertButton);
		
		//Reset Button
		resetBtn = new JButton("Reset");
		resetBtn.setFont(ConvertButton.getFont().deriveFont(17.0f));
		resetBtn.setBounds(480,170,150,40);
		resetBtn.setBackground(new Color(204, 204, 204));
		resetBtn.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	sumToConvertTF.setText("");
		    	convertResultTF.setText("");
		    	currencyIHaveCB.setSelectedIndex(0);
		    	currencyIWantCB.setSelectedIndex(0);
                try {
					bw.write("User pressed the reset button.");
					bw.newLine();
				} catch (IOException e1) {
					System.out.println("Couldn't write to log file.");
					e1.printStackTrace();
				}
		    }
		});
		converterFrame.add(resetBtn);
		
		//Drawing a line 
		lineLB = new JLabel("______________________________________________________________________________________________________________________________________________________________________");
		lineLB.setBounds(0, 250, 1000, 50);
		lineLB.setForeground(new Color(204,163,0));
		converterFrame.add(lineLB);
		
		//Rates table button
		tableBtn = new JButton("See Rates Table");
		tableBtn.setBounds(175, 320,200,70);
		tableBtn.setFont(tableBtn.getFont().deriveFont(16.0f));
		tableBtn.setBackground(new Color(204, 204, 204));
		tableBtn.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	RatesTableGUI rtGUI = new RatesTableGUI(prsXML);
                try {
					bw.write("User pressed the rates table button.");
					bw.newLine();
				} catch (IOException e1) {
					System.out.println("Couldn't write to log file.");
					e1.printStackTrace();
				}
		    }
		});
		converterFrame.add(tableBtn);
		
		//Update button
		updateBtn = new JButton("Update Rates Table");
		updateBtn.setBounds(425,320,200,70);
		updateBtn.setFont(updateBtn.getFont().deriveFont(16.0f));
		updateBtn.setBackground(new Color(204, 204, 204));
		updateBtn.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	Thread xmlDownload = new Thread(new Runnable() {
				  public void run() {
					try {
						prsXML.downloadXML();
					} catch (IOException e) {
						System.out.println("Couldn't update XML file.");
						e.printStackTrace();
					}
				  }
				});
              xmlDownload.start(); 					
				currentDateDataLB.setText(prsXML.getDate());
				cal = Calendar.getInstance();
				currentTimeDataLB.setText(sdf.format(cal.getTime()));
				try {
					bw.write("User pressed the update rates table button.");
					bw.newLine();
				} catch (IOException e1) {
					System.out.println("Couldn't write to log file.");
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Data has been updated!", "Update", 1);
		    }
		});
		converterFrame.add(updateBtn);
		
		//Switch icon and button
		switchIcon = new ImageIcon("src//resources//switchBtn1.png");
		switchBtn = new JButton(switchIcon);
		switchBtn.setBounds(340, 154, 30, 30);
		switchBtn.setBackground(new Color(204, 204, 204));
		switchBtn.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(currencyIHaveCB.getSelectedIndex() != 0 && currencyIWantCB.getSelectedIndex() != 0)
		    	{
                    try {
						bw.write("User pressed the switch between currencies button.");
						bw.newLine();
					} catch (IOException e1) {
						System.out.println("Couldn't write to log file.");
						e1.printStackTrace();
					}
		    		float[] tmpRates = prsXML.getRateFloat();
		    		int[] tmpUnits = prsXML.getUnitInt();
		    		float newSourceRate = tmpRates[currencyIWantCB.getSelectedIndex()-1];
		    		for(int i=0; i<tmpRates.length; i++){
		    			tmpRates[i]=tmpRates[i]/newSourceRate;
		    		}
		    		Thread setRates = new Thread(new Runnable() {
                  	  public void run() {
                  		  prsXML.setRateFloat(tmpRates);
                  	  }
                  	});
		    		setRates.start();
		    		wantedCurrencyRate = tmpRates[currencyIHaveCB.getSelectedIndex()-1];
            		wantedCurrencyUnit = tmpUnits[currencyIHaveCB.getSelectedIndex()-1];
		    		int selectedIndexIHaveCB = currencyIHaveCB.getSelectedIndex();		    		
			    	currencyIHaveCB.setSelectedIndex(currencyIWantCB.getSelectedIndex());
			    	currencyIWantCB.setSelectedIndex(selectedIndexIHaveCB);  
		    	}
		    }
		});
		converterFrame.add(switchBtn);
		
		//About button
		aboutBtn = new JButton("About");
		aboutBtn.setBounds(675,320,200,70);
		aboutBtn.setFont(aboutBtn.getFont().deriveFont(16.0f));
		aboutBtn.setBackground(new Color(204, 204, 204));
		aboutBtn.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	JOptionPane.showMessageDialog(null, "This project was created by: \n Matan Nabatian \n Leon Benjamin", "About", 3);
		    	try {
					bw.write("User pressed the about button.");
					bw.newLine();
				} catch (IOException e1) {
					System.out.println("Couldn't write to log file.");
					e1.printStackTrace();
				}
				
		    	
		    }
		});
		converterFrame.add(aboutBtn);
	}
	 
	 //Inserting data to the JComboBoxes
	 private DefaultComboBoxModel<Object> populate(ParsingXML tmpParseXML)
	 {
		 String[] tmpCurrCodeAndNames = tmpParseXML.getCountriesAndCode();
		 String[] tmpCurrNames = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
		 DefaultComboBoxModel<Object> dm = new DefaultComboBoxModel<Object>();
		 for(int i=0; i<16; i++)
		 {
			 if(i == 0)
			 {
				 dm.addElement(new ImagesNText(new ImageIcon("src//resources//99.png"),""));
			 }
			 else
			 {
				 dm.addElement(new ImagesNText(new ImageIcon("src//resources//"+tmpCurrNames[i-1]+".png"),tmpCurrCodeAndNames[i-1]));
			 }
		 }
		 return dm;
	 }
}

/**
 * Setting the JComboBox to look exactly like default JComboBox but having pictures and strings in the options.
 * @author Leon & Matan
 */
@SuppressWarnings("serial")
class ImagesTextRenderer extends JLabel implements ListCellRenderer
{
	@Override
	public Component getListCellRendererComponent(JList list, Object val, int index, boolean selected, boolean focused) {
		// TODO Auto-generated method stub
		
		//Get Values
		ImagesNText it = (ImagesNText)val;
		
		if(it != null)
		{
			setIcon(it.getCurrImg());
			setText(it.getCurrName());
						
			if(selected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				
				setFont(list.getFont());
			}
		}
		return this;
	}
}

/**
 * This class was created to help building a JComboBox that has a picture and a string in every option.
 * @author Leon & Matan
 *
 */
class ImagesNText
{
	private Icon currImg;
	private String currName;
	
	public ImagesNText(Icon currImg,String currName)
	{
		this.currImg=currImg;
		this.currName=currName;
	}

	public Icon getCurrImg() {
		return currImg;
	}

	public void setCurrImg(Icon currImg) {
		this.currImg = currImg;
	}

	public String getCurrName() {
		return currName;
	}

	public void setCurrName(String currName) {
		this.currName = currName;
	}
	
}


