import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;

public class SearchBooksAndMembers extends JInternalFrame implements ActionListener
{
	private JPanel northPanel = new JPanel();
	private JLabel title = new JLabel("Search for Books and Members");
	private JPanel center = new JPanel();
	private JPanel centerBooksPanel = new JPanel();
	private JPanel searchBooksPanel = new JPanel();
	private JPanel searchBooksButtonPanel = new JPanel();
	//private JLabel searchBooksLabel = new JLabel(" Search by: ");
	//private JComboBox searchBooksTypes;
	//private String[] booksTypes = {"BookID", "Subject", "Title", "Language", "Author", "Publisher"};
	private JLabel booksKey = new JLabel(" Enter Book ID ");
	private JTextField booksKeyTextField = new JTextField(20);
	private JButton searchBooksButton = new JButton("Search");

	//for creating the Center Panel
	private JPanel centerMembersPanel = new JPanel();
	//for creating an Internal Panel in the center panel
	private JPanel searchMembersPanel = new JPanel();
	//for creating an Internal Panel in the center panel
	private JPanel searchMembersButtonPanel = new JPanel();

	//for creating the table
	//private JLabel searchMembersLabel = new JLabel(" Search by: ");
	//for creating JComboBox
	//private JComboBox searchMembersTypes;
	//for creating String[]
	//private String[] membersTypes = {"MemberID", "Name", "Roll", "Department"};
	//for creating the label
	private JLabel membersKey = new JLabel(" Enter the Member ID ");
	//for cearting the text field
	private JTextField membersKeyTextField = new JTextField(20);
	//for creating the button
	private JButton searchMembersButton = new JButton("Search");

	//for creating the south panel
	private JPanel southPanel = new JPanel();
	//for creating the button
	private JButton cancelButton = new JButton("Cancel");

	//for creating an array of string to store the data
	private String[] booksData;
	private String[] membersData;
	//create objects from another classes for using them in the ActionListener
	private SearchBooks searchBook;
	private SearchMembers searchMember;
	
		private String sql;
	private Connection cn=null;
	private PreparedStatement ps;
	private Statement stm;
	private ResultSet rs,rsBookID;
		int flag=0;

	//for checking the information from the text field
	/*public boolean isBooksDataCorrect()
	{
		if (!booksKeyTextField.getText().equals(""))
		{
			String bookID = booksKeyTextField.getText();
		}
			else
				return false;
		}
		return true;
	}*/

	//for checking the information from the text field
	/*public boolean isMembersDataCorrect()
	{
		membersData = new String[2];
		membersData[0] = searchMembersTypes.getSelectedItem().toString();
		for (int i = 1; i < membersData.length; i++)
		{
			if (!membersKeyTextField.getText().equals(""))
			{
				if (searchMembersTypes.getSelectedItem().toString().equals("MemberID"))
				{
					membersData[i] = membersKeyTextField.getText();
				}
				else
					membersData[i] = "'%" + membersKeyTextField.getText() + "%'";
			}
			else
				return false;
		}
		return true;
	}*/

	//constructor of searchBooksAndMembers
	public SearchBooksAndMembers()
	{
		//for setting the title for the internal frame
		//super("Search");
		super("Search", false, true, false, true);
		//for setting the icon
		setFrameIcon(new ImageIcon("images/Find16.gif"));
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		//for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		northPanel.add(title);
		cp.add("North", northPanel);

		center.setLayout(new BorderLayout());
		centerBooksPanel.setLayout(new BorderLayout());
		searchBooksPanel.setLayout(new GridLayout(2, 2, 1, 1));
		//searchBooksPanel.add(searchBooksLabel);
		//searchBooksPanel.add(searchBooksTypes = new JComboBox(booksTypes));
		searchBooksPanel.add(booksKey);
		searchBooksPanel.add(booksKeyTextField);
		centerBooksPanel.add("North", searchBooksPanel);
		searchBooksButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		searchBooksButtonPanel.add(searchBooksButton);
		centerBooksPanel.add("South", searchBooksButtonPanel);
		centerBooksPanel.setBorder(BorderFactory.createTitledBorder("Search for a books:"));
		center.add("West", centerBooksPanel);

		//for setting the layout
		centerMembersPanel.setLayout(new BorderLayout());
		//for setting the layout
		searchMembersPanel.setLayout(new GridLayout(2, 2, 1, 1));
		//for adding the label
		//searchMembersPanel.add(searchMembersLabel);
		//for adding the JComboBos[]
		//searchMembersPanel.add(searchMembersTypes = new JComboBox(membersTypes));
		//for adding the label
		searchMembersPanel.add(membersKey);
		//for adding the text field
		searchMembersPanel.add(membersKeyTextField);
		//for adding the internal panel to the panel
		centerMembersPanel.add("North", searchMembersPanel);

		//for setting the layout
		searchMembersButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//for adding the button
		searchMembersButtonPanel.add(searchMembersButton);
		//for adding the internal panel to the center panel
		centerMembersPanel.add("South", searchMembersButtonPanel);
		//for setting the border
		centerMembersPanel.setBorder(BorderFactory.createTitledBorder("Search for a members:")); 
		//for adding center panel to the center
		center.add("East", centerMembersPanel);

		//for adding the center to the container
		cp.add("Center", center);

		/**
		 *for setting the font to the lables & buttons
		 */
		//searchBooksLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		//searchBooksTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		booksKey.setFont(new Font("Tahoma", Font.BOLD, 11));
		booksKeyTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchBooksButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		//searchMembersLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		//searchMembersTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		membersKey.setFont(new Font("Tahoma", Font.BOLD, 11));
		membersKeyTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchMembersButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));

		//for setting the layout
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//for adding the button
		southPanel.add(cancelButton);
		//for setting the border
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		//for adding the south panel to the container
		cp.add("South", southPanel);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stm.executeQuery("select * from  Books");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
		searchBooksButton.addActionListener(this);
		searchMembersButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		setVisible(true);
		pack();
	}
		public void actionPerformed(ActionEvent ae)
		{
			try
			{
				if(ae.getSource()==searchBooksButton)
				{
					if (!booksKeyTextField.getText().equals(""))
					{
						rsBookID=stm.executeQuery("select * from  Books");
						try
						{
							String bookQuery = "SELECT BookID, Subject, Language, Title, Version, Author, Publisher, Copyright, Edition, Pages,	NumberOfBooks FROM Books WHERE BookID LIKE " + booksKeyTextField.getText() ;
							
							//text field input
							int inputBookID = Integer.parseInt(booksKeyTextField.getText());
							rsBookID.first();
							//book id from database
							int bookID = rsBookID.getInt(1);
							if (bookID != 0)
							{
								searchBook = new SearchBooks(bookQuery);
								getParent().add(searchBook);
								try
								{
									searchBook.setSelected(true);
								}
								catch (Exception e){		}
								//dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "No Match(es)", "Error", JOptionPane.ERROR_MESSAGE);
								booksKeyTextField.setText(null);
							}
						}catch (Exception e){		}
					}
					else
						JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
						
				}
		
				if(ae.getSource()==searchMembersButton)
				{
				if (!membersKeyTextField.getText().equals(""))
				{
					ResultSet rsm=stm.executeQuery("select * from  Members");

					String memberQuery = "SELECT Type ,MemberID , Name , Roll , Class, Division , Dept , Phone , Email  FROM Members WHERE MemberID LIKE " + membersKeyTextField.getText();
					
					int inputMem = Integer.parseInt(membersKeyTextField.getText());
					rsm.first();
					int memberID = rsm.getInt(2);
					
					if (memberID != 0)
					{
						searchMember = new SearchMembers(memberQuery);
						getParent().add(searchMember);
						try
						{
							searchMember.setSelected(true);
				 		}
						catch (Exception e)
						{}
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "No Match(es)", "Error", JOptionPane.ERROR_MESSAGE);
						membersKeyTextField.setText(null);
					}
				}
				else
				
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				
				
				if(ae.getSource()==cancelButton)
				{
			
					dispose();
				}
			}catch (Exception e){		}
		}

	public static void main(String a[])
	{
		new SearchBooksAndMembers();
	}
}