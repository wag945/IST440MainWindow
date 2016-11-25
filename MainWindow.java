import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

//Include the Dropbox SDK.
import com.dropbox.core.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.awt.Desktop;
 
public class MainWindow extends JFrame implements ActionListener {
 
       /**
       *
        */
       private static final long serialVersionUID = 1L;
       private static final String LOGIN = "login";
       private static final String GETIMAGES = "getimages";
       private static final String CLOSE = "close";
       private static final String STARTOVER = "startover";
       private static final String OCR = "ocr";
       private static final String DECRYPTION = "decryption";
       private static final String OPTIONMENU = "optionmenu";
       private static final String LANGUAGEMENU = "languagemenu";
       private static final String OUTPUTFORMAT = "outputformat";
       private static final String RUNOCR = "runocr";
       private static final String PREVIEWOCR = "previewocr";
       private static final String TRANSLATE = "translate";
       private static final String BLANKSTRING = "";
       private JPanel loginpanel;
       private JPanel mainpanel;
       private JLabel usernamelabel;
       private JTextField usernametext;
       private JLabel passwordlabel;
       private JPasswordField passwordfield;
       private JButton loginbutton;
       private String username;
       private String language;
       private String outputformat;
       private JLabel loggedinaslabel;
       private JButton getimagesbutton;
       private JLabel getimagesstatus;
       private JLabel selectedfilelabel;
       private JLabel ocrstatuslabel;
       private JLabel ocrlabel;
       private JButton performtranslatebutton;
       private JButton performocrbutton;
       private JButton ocrpreviewbutton;
       private JButton performdecryptionbutton;
       private JButton closebutton;
       private JButton startoverbutton;
       private File selectedfile;
       private JComboBox<String> optionList;
       private JComboBox<String> languageList;
       private JComboBox<String> outputFormatList;
       private JTextArea textArea;
       public JTextArea runocrarea;
       private JScrollPane scrollPane;
       private JScrollPane runocrpane;
       private DbxClient client;
       private JLabel imagelabel;
       private ImageIcon icon;
       private JButton runocrbutton;
       String[] optionStrings;
 
       /**
       * Launch the application.
       */
       public static void main(String[] args) {
              EventQueue.invokeLater(new Runnable() {
                     public void run() {
                           try {
                                  MainWindow frame = new MainWindow();
                                  frame.setVisible(true);
                           } 
                           catch (Exception e) {
                                  e.printStackTrace();
                           }
                     }
              });
       }
 
       /**
       * Create the frame.
       */
       @SuppressWarnings("unchecked")
	public MainWindow() 
    {
    	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setTitle("IST 440 Team 05");
           setResizable(false);
           setBounds(100, 100, 800, 700);
           setBackground(new Color(0,0,139));
           loginpanel = new JPanel();
           loginpanel.setLayout(null);
           loginpanel.setBackground(new Color(0, 0, 139));
           setContentPane(loginpanel);
             
           usernamelabel = new JLabel("Username:");
           usernamelabel.setForeground(Color.YELLOW);
           usernamelabel.setBounds(210,10,100,25);
           usernamelabel.setFont(new Font("Serif", Font.BOLD, 16));
           loginpanel.add(usernamelabel);
             
           usernametext = new JTextField();
           usernametext.setBounds(350, 10, 100, 25);
           loginpanel.add(usernametext);
           usernametext.setColumns(25);
             
           passwordlabel = new JLabel("Password:");
           passwordlabel.setForeground(Color.YELLOW);
           passwordlabel.setBounds(210,40,100,25);
           passwordlabel.setFont(new Font("Serif", Font.BOLD, 16));
           loginpanel.add(passwordlabel);
             
           passwordfield = new JPasswordField();
           passwordfield.setBounds(350, 40, 100, 25);
           loginpanel.add(passwordfield);
             
           loginbutton = new JButton("Login");
           loginbutton.addActionListener(this);
           loginbutton.setActionCommand(LOGIN);
           loginbutton.setBounds(350, 130, 100, 25);
           loginpanel.add(loginbutton);

           mainpanel = new JPanel();
           mainpanel.setLayout(null);
           mainpanel.setBackground(new Color(0, 0, 139));
           mainpanel.setVisible(false);
              
           runocrbutton = new JButton("Run");
           runocrbutton.addActionListener(this);
           runocrbutton.setActionCommand(RUNOCR);
           mainpanel.add(runocrbutton);

           loggedinaslabel = new JLabel("Username: ");
           loggedinaslabel.setForeground(Color.YELLOW);
           loggedinaslabel.setBounds(10,10,250,25);
           loggedinaslabel.setFont(new Font("Serif", Font.BOLD, 16));
           mainpanel.add(loggedinaslabel);
              
           getimagesbutton = new JButton("Get Images");
           getimagesbutton.addActionListener(this);
           getimagesbutton.setActionCommand(GETIMAGES);
           getimagesbutton.setBounds(10, 50, 150, 50);
           mainpanel.add(getimagesbutton);
              
           getimagesstatus = new JLabel("");
           getimagesstatus.setForeground(Color.YELLOW);
           getimagesstatus.setBounds(10,100,500,25);
           getimagesstatus.setFont(new Font("Sefif",Font.BOLD,14));
           mainpanel.add(getimagesstatus);
              
           imagelabel = new JLabel(); 
           imagelabel.setBounds(375,50,450,300);
           imagelabel.setVisible(false);
           mainpanel.add(imagelabel);

           optionStrings = new String[] {""};
           optionList = new JComboBox<>(optionStrings);
           optionList.setSelectedIndex(0);
           optionList.addActionListener(this);
           optionList.setActionCommand(OPTIONMENU);
           optionList.setBounds(160,50,200,25);
           optionList.setEnabled(false);
           mainpanel.add(optionList);

           selectedfilelabel = new JLabel("");
           selectedfilelabel.setForeground(Color.YELLOW);
           selectedfilelabel.setBounds(350,10,300,25);
           selectedfilelabel.setFont(new Font("Serif", Font.BOLD, 16));
           selectedfilelabel.setVisible(false);
           mainpanel.add(selectedfilelabel);              
              
//           performtranslatebutton = new JButton("Run Translate");
//           performtranslatebutton.addActionListener(this);
//           performtranslatebutton.setActionCommand(TRANSLATE);
//           performtranslatebutton.setBounds(10, 375, 150, 50);
//           performtranslatebutton.setEnabled(false);
//           mainpanel.add(performtranslatebutton);
              
           ocrlabel = new JLabel("");
           ocrlabel.setForeground(Color.YELLOW);
           ocrlabel.setBounds(15,425,100,25);
           ocrlabel.setFont(new Font("Serif",Font.BOLD,14));
           mainpanel.add(ocrlabel);
              
           performocrbutton = new JButton("Run OCR");
           performocrbutton.addActionListener(this);
           performocrbutton.setActionCommand(OCR);
           performocrbutton.setBounds(160,450,150,50);
           performocrbutton.setEnabled(false);
           mainpanel.add(performocrbutton);
              
           ocrstatuslabel = new JLabel("");
           ocrstatuslabel.setForeground(Color.YELLOW);
           ocrstatuslabel.setBounds(10,500,600,25);
           ocrstatuslabel.setFont(new Font("Serif",Font.BOLD,14));
           mainpanel.add(ocrstatuslabel);
              
           ocrpreviewbutton = new JButton("Preview OCR");
           ocrpreviewbutton.addActionListener(this);
           ocrpreviewbutton.setActionCommand(PREVIEWOCR);
           ocrpreviewbutton.setBounds(310,450,100,50);
           ocrpreviewbutton.setEnabled(false);
           mainpanel.add(ocrpreviewbutton);
              
           String[] languageStrings = new String[] {"","English","Spanish","French","German"};
           languageList = new JComboBox<>(languageStrings);
           languageList.setSelectedIndex(0);
           languageList.addActionListener(this);
           languageList.setActionCommand(LANGUAGEMENU);
           languageList.setBounds(10,450,150,25);
           languageList.setEnabled(false);
           mainpanel.add(languageList);
           language = languageStrings[0];

           String[] outputFormatStrings = new String[] {"","txt","rtf","docx","xlsx","pptx","pdf","xml"};
           outputFormatList = new JComboBox<>(outputFormatStrings);
           outputFormatList.setSelectedIndex(0);
           outputFormatList.addActionListener(this);
           outputFormatList.setActionCommand(OUTPUTFORMAT);
           outputFormatList.setBounds(10,475,150,25);
           outputFormatList.setEnabled(false);
           mainpanel.add(outputFormatList);
           outputformat = outputFormatStrings[0];
              
           performdecryptionbutton = new JButton("Run Decryption");
           performdecryptionbutton.addActionListener(this);
           performdecryptionbutton.setActionCommand(DECRYPTION);
           performdecryptionbutton.setBounds(10, 525, 150, 50);
           performdecryptionbutton.setEnabled(false);
           mainpanel.add(performdecryptionbutton);
              
           startoverbutton = new JButton("Start Over");
           startoverbutton.addActionListener(this);
           startoverbutton.setActionCommand(STARTOVER);
           startoverbutton.setBounds(300, 650, 100, 25);
           mainpanel.add(startoverbutton);

           closebutton = new JButton("Close");
           closebutton.addActionListener(this);
           closebutton.setActionCommand(CLOSE);
           closebutton.setBounds(425, 650, 100, 25);
           mainpanel.add(closebutton);
              
           checkForInternetConnection();
       }
 
       @Override
       public void actionPerformed(ActionEvent e) {
              boolean isCorrect = false;
              String cmd = e.getActionCommand();
              char[] input = passwordfield.getPassword();
              char[] correctPassword = { 't','e','s','t'  };
 
              if (LOGIN.equals(cmd)) {
                     System.out.println("LOGIN pressed");
                     username = usernametext.getText();
                     System.out.println("username = " + username);
                     if (username.equals("test")) {
                           if (input.length != correctPassword.length) {
                                  isCorrect = false;
                           }
                           else {
                                  isCorrect = Arrays.equals (input, correctPassword);
                           }
                     }
                    
                     if (true == isCorrect) {
                           System.out.println("Login was successful");
                           loginpanel.setVisible(false);
                           setContentPane(mainpanel);
                           mainpanel.setVisible(true);
                           loggedinaslabel.setText(loggedinaslabel.getText() + " "+username);
                     }
                     else 
                     {
                           System.out.print("Login was not successful");
                           displayDialogBox("Login failed","ERROR");
                     }

                     //Zero out the password.
                     Arrays.fill(correctPassword,'0');
              }
              else if (GETIMAGES.equals(cmd)) {
            	  System.out.println("Download image pressed");
            	  setWaitCursor(this);
            	  languageList.setEnabled(false);
            	  System.out.println("calling connectToDropbox...");
            	  connectToDropbox();
            	  setDefaultCursor(this);
            	  optionList.setEnabled(true);
            	  getimagesbutton.setEnabled(false);
             }
             else if (CLOSE.equals(cmd)) {
            	 System.out.println("Close pressed");
            	 System.exit(0);
             }
             else if (STARTOVER.equals(cmd)) {
            	 System.out.println("Start over pressed");
            	 //Disable all widgets except Get Images and clear all previews
            	 disableAllWidgets();
            	 getimagesbutton.setEnabled(true);
             }
             else if (OCR.equals(cmd)) {
            	 System.out.println("OCR pressed");
            	 setWaitCursor(this);
            	 try {
            		 System.out.println("OCR passing file: " + selectedfile.getCanonicalPath());
                	 TestApp ta = new TestApp(	this,
												"recognize",
												"/Users/Bill/Documents/workspace/IST440MainWindow/ScreenShot.png",
												"results."+outputformat,
												language);
                	 System.out.println("returned from TestApp");
                	 ocrstatuslabel.setText("OCR output = results." + outputformat);
            	 }
            	 catch(Exception exception){}
            	 setDefaultCursor(this);
        		 performocrbutton.setEnabled(false);
        		 ocrpreviewbutton.setEnabled(true);
            	 performdecryptionbutton.setEnabled(true);
        		 languageList.setEnabled(false);
        		 outputFormatList.setEnabled(false);
             }
             else if (DECRYPTION.equals(cmd)) {
            	 System.out.println("DECRYPTION pressed");
            	 performdecryptionbutton.setEnabled(false);
            	 runDecryption("results."+outputformat);
             }
             else if (OPTIONMENU.equals(cmd)) {
            	JComboBox cb = (JComboBox)e.getSource();
                String optionName = (String)cb.getSelectedItem();
                System.out.println("user selected " + optionName);
                selectedfile = new File(optionName);
                setWaitCursor(this);
                try {
					icon = new ImageIcon(ImageIO.read(selectedfile).getScaledInstance(400, 300, Image.SCALE_SMOOTH));
                } catch (IOException e1) {
                	 // TODO Auto-generated catch block
                	 e1.printStackTrace();
                	 displayDialogBox(e1.getMessage(),"ERROR");
                }
                imagelabel.setIcon(icon);
                imagelabel.setVisible(true);
                 //download the file from dropbox
                downloadFileFromDropbox(optionName);
                setContentPane(mainpanel);
                mainpanel.setVisible(true);
                setDefaultCursor(this);
                selectedfilelabel.setText("Selected File: " + optionName);
                selectedfilelabel.setVisible(true);
//                performtranslatebutton.setEnabled(true);
                languageList.setEnabled(true);
             }
             else if (LANGUAGEMENU.equals(cmd)) {
            	 JComboBox cb = (JComboBox)e.getSource();
                 String optionName = (String)cb.getSelectedItem();
                 System.out.println("user selected " + optionName);
                 language = optionName;
                 outputFormatList.setEnabled(true);
             }
             else if (OUTPUTFORMAT.equals(cmd)) {
            	 JComboBox cb = (JComboBox)e.getSource();
            	 String optionName = (String)cb.getSelectedItem();
            	 System.out.println("user selected " + optionName);
            	 outputformat = optionName;
                 getimagesbutton.setEnabled(false);
                 performocrbutton.setEnabled(true);
                 setContentPane(mainpanel);
                 mainpanel.setVisible(true);
                 performocrbutton.setEnabled(true);
             }
             else if (TRANSLATE.equals(cmd)) {
            	 System.out.println("user pressed translate button");
            	 ocrlabel.setText("OCR Options");
                 languageList.setEnabled(true);
                 performtranslatebutton.setEnabled(false);
             }
             else if (PREVIEWOCR.equals(cmd)) {
            	 System.out.println("user pressed preview ocr button");
            	 if ("pdf" == outputformat)
            	 {
            		 openPdfFile("/Users/Bill/Documents/workspace/IST440MainWindow/results."+outputformat);
            	 }
            	 else if ("docx" == outputformat)
            	 {
            		 openDocxFile("/Users/Bill/Documents/workspace/IST440MainWindow/results."+outputformat);
            	 }
            	 else if ("txt" == outputformat)
            	 {
            		 openTxtFile("/Users/Bill/Documents/workspace/IST440MainWindow/results."+outputformat);
            	 }
            	 ocrpreviewbutton.setEnabled(false);
             }
      }
       
      public void connectToDropbox() {
          	// Get your app key and secret from the Dropbox developers website.
          	final String APP_KEY = "50csb8u7pd6z1om";
          	final String APP_SECRET = "ml166jmhl2fo6f5";

          	DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

          	DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
          			Locale.getDefault().toString());
          	System.out.println("Contacting Dropbox...");
          	getimagesstatus.setText("Contacting Dropbox...");
          	DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

          	String accessToken = "zwpDxpPSHMAAAAAAAAAADi3YOpzyKSqoM4TyeffcGnq5hdQhDeyyS_iKsDg0Qsg3";

          	client = new DbxClient(config, accessToken);

          	try 
          	{
          		System.out.println("Linked account name: " + client.getAccountInfo().displayName);
          		System.out.println("Linked account email: " + client.getAccountInfo().email);
          		DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
          		System.out.println("Files in the root path:");
          		
          		for (DbxEntry child : listing.children) 
          		{
          			System.out.println("     name: " + child.name);
          			optionList.addItem(child.name);
          		}
          		
          		getimagesstatus.setText("Retrieved " + listing.children.size() +" files from Dropbox");
          	}
          	catch(DbxException e)
          	{
          		System.out.println("connectToDropbox exception e = " + e.getMessage());
          		getimagesstatus.setText("connecting to dropbox generated exception: " + e.getMessage());
          	}

      }
      
      public void downloadFileFromDropbox(String filename) 
      {
    	  FileOutputStream outputStream = null;
    	  try 
    	  {
    		  outputStream = new FileOutputStream(filename);
    	  } 
    	  catch (FileNotFoundException e) 
    	  {
    		  e.printStackTrace();
    		  displayDialogBox(e.getMessage(),"ERROR");
    	  }
		
    	  try 
    	  {
    		  DbxEntry.File downloadedFile = null;
    		  try 
    		  {
    			  downloadedFile = client.getFile("/"+filename, null,outputStream);
    		  } 
    		  catch (DbxException e) 
    		  {
    			  e.printStackTrace();
    		  } 
    		  catch (IOException e) 
    		  {
    			  e.printStackTrace();
    		  }
			
    		  System.out.println("Metadata: " + downloadedFile.toString());
    		  //getimagesstatus.setText("Metadata: " + downloadedFile.toString());
    		  getimagesstatus.setVisible(true);
    		  getimagesstatus.setText("Downloaded: " + filename);
    	  } 
    	  finally 
    	  {
    		  try 
    		  {
    			  outputStream.close();
    		  } 
    		  catch (IOException e) 
    		  {
    			  e.printStackTrace();
    			  displayDialogBox(e.getMessage(),"ERROR");
    		  }
    	  }
      }
      
      public void openPdfFile(String filename)
      {
    	  try 
    	  {
    		  System.out.println("openPdfFile opening " + filename);
    		  File pdfFile = new File(filename);
    		  	
    		  if (pdfFile.exists()) 
    		  {
    			  if (Desktop.isDesktopSupported()) 
    			  {
    				  Desktop.getDesktop().open(pdfFile);
    			  } 
    			  else 
    			  {
    				  System.out.println("Awt Desktop is not supported!");
    			  }
    		  } 
    		  else 
    		  {
    			  System.out.println(filename + " does not exist");
    		  }
       	  } 
    	  catch (Exception ex) 
    	  {
    		  ex.printStackTrace();
    		  displayDialogBox(ex.getMessage(),"ERROR");
    	  }
      }
      
      public void openDocxFile(String filename)
      {
    	  try 
    	  {
    		  System.out.println("openDocxFile opening " + filename);
    		  File docxFile = new File(filename);
    		  	
    		  if (docxFile.exists()) 
    		  {
    			  if (Desktop.isDesktopSupported()) 
    			  {
    				  Desktop.getDesktop().open(docxFile);
    			  } 
    			  else 
    			  {
    				  System.out.println("Awt Desktop is not supported!");
    			  }
    		  } 
    		  else 
    		  {
    			  System.out.println(filename + " does not exist");
    		  }
       	  } 
    	  catch (Exception ex) 
    	  {
    		  ex.printStackTrace();
    		  displayDialogBox(ex.getMessage(),"ERROR");
    	  }
      }

      public void openTxtFile(String filename)
      {
    	  try 
    	  {
    		  System.out.println("openTxtFile opening " + filename);
    		  File txtFile = new File(filename);
    		  	
    		  if (txtFile.exists()) 
    		  {
    			  if (Desktop.isDesktopSupported()) 
    			  {
    				  Desktop.getDesktop().open(txtFile);
    			  } 
    			  else 
    			  {
    				  System.out.println("Awt Desktop is not supported!");
    			  }
    		  } 
    		  else 
    		  {
    			  System.out.println(filename + " does not exist");
    		  }
       	  } 
    	  catch (Exception ex) 
    	  {
    		  ex.printStackTrace();
    		  displayDialogBox(ex.getMessage(),"ERROR");
    	  }
      }

      public void disableAllWidgets()
      {
          //performtranslatebutton.setEnabled(false);
          performocrbutton.setEnabled(false);
          ocrpreviewbutton.setEnabled(false);
          performdecryptionbutton.setEnabled(false);
          imagelabel.setVisible(false);
          getimagesstatus.setVisible(false);
          selectedfilelabel.setText("");
          ocrlabel.setText("");
          ocrstatuslabel.setText("");
          optionList.setEnabled(false);
          languageList.setEnabled(false);
          outputFormatList.setEnabled(false);
      }
      
      public void runDecryption(String filename)
      {
    	  System.out.println("runDecryption filename = " + filename);
          int min = 0;
          int max = 19;
          CaesarCipher cc = new CaesarCipher();
          try
          {
              String content = new String(Files.readAllBytes(Paths.get(filename)));
              System.out.println(content);
              content = content.toLowerCase();
              
              String DECRYPTFILE = "decryption.txt";
              PrintWriter out = new PrintWriter(DECRYPTFILE);  
              for (int i = min; i <= max; i++)
              {
            	  	String decryptedString = cc.decrypt(content, i);
              		System.out.println("Decrypted i = " + i + " decryptedString = " + decryptedString);
              		//Write the decrypted string to the output file
              		out.print(decryptedString+"\n");
              }
              out.close();
              //Display the decryption output file 
              openTxtFile(DECRYPTFILE);
          }
          catch(IOException e)
          {
        	 System.out.println("main caught exception: " + e.getMessage());
        	 displayDialogBox(e.getMessage(),"ERROR");
          }
      }
      
      public void checkForInternetConnection()
      {
    	  InetAddress[] addresses = null;
    	  try 
    	  {
			addresses = InetAddress.getAllByName("www.google.com");
    	  } 
    	  catch (UnknownHostException e) 
    	  {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	  }
    	  
    	  if (addresses == null || addresses.length == 0)
    	  {
			  displayDialogBox("No Internet connection detected.  Restart the program.","ERROR");    		  
    	  }
      }
      
      public void displayDialogBox(String infoMessage, String titleBar)
      {
          JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
      }

      public static void setWaitCursor(JFrame frame) 
      {
          if (frame != null) {
              RootPaneContainer root = (RootPaneContainer) frame.getRootPane().getTopLevelAncestor();
              root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
              root.getGlassPane().setVisible(true);
          }
      }

      public static void setDefaultCursor(JFrame frame) 
      {
          if (frame != null) {
              RootPaneContainer root = (RootPaneContainer) frame.getRootPane().getTopLevelAncestor();
              root.getGlassPane().setCursor(Cursor.getDefaultCursor());
          }
      }
}
 