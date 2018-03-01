import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;

public class BorrowBooks extends JFrame implements ActionListener
{
	private JPanel northPanel = new JPanel();
	private JLabel title = new JLabel("BOOK INFORMATION");

	private JPanel centerPanel = new JPanel();
	private JPanel informationPanel = new JPanel();
	private JLabel[] informationLabel = new JLabel[4];
	private String[] informationString = {" Write the Book ID", " Write the Member ID",
	                                      " The Current Date", " The Return Date"};
	private JTextField[] informationTextField = new JTextField[4];
	//private String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
	
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String dt = f.format(new java.util.Date());
		Calendar c = Calendar.getInstance();
		
		
	//for creating an array of string to store the data
	private String[] data;

	private JPanel borrowButtonPanel = new JPanel();
	private JButton borrowButton = new JButton("Borrow");

	private JPanel southPanel = new JPanel();
	private JButton cancelButton = new JButton("Cancel");

	private String query;
	private Connection cn = null;
	private Statement stm = null;
	private PreparedStatement ps = null;
	private ResultSet rsBook = null;
	private ResultSet rsMember = null;
	private ResultSet rsTrans = null;


	//for checking the information from the text field
	public boolean isCorrect()
	{
		data = new String[4];
		for (int i = 0; i < informationLabel.length; i++)
		{
			if (!informationTextField[i].getText().equals(""))
				data[i] = informationTextField[i].getText();
			else
				return false;
		}
		return true;
	}

	//for setting the array of JTextField to null
	public void clearTextField()
	{
		for (int i = 0; i < informationTextField.length; i++)
			if (i != 2)
				informationTextField[i].setText(null);
	}

	public BorrowBooks()
	{
		super("Borrow Books");
		//super("Borrow Books", false, true, false, true);
		//setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));

		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		northPanel.add(title);
		cp.add("North", northPanel);

		centerPanel.setLayout(new BorderLayout());
		informationPanel.setLayout(new GridLayout(4, 2, 1, 1));

		for (int i = 0; i < informationLabel.length; i++)
		{
			informationPanel.add(informationLabel[i] = new JLabel(informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
			if (i == 2)
			{
				informationPanel.add(informationTextField[i] = new JTextField(dt));
				informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
				informationTextField[i].setEnabled(false);
			}
			else if (i == 3)
			{
				try
				{
					c.setTime(f.parse(dt));
					c.add(Calendar.DATE, 7);
					dt = f.format(c.getTime());
					
					informationPanel.add(informationTextField[i] = new JTextField(dt));
					informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
					informationTextField[i].setEnabled(false);
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				informationPanel.add(informationTextField[i] = new JTextField());
				informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
			}
		}
		
		

		centerPanel.add("Center", informationPanel);

		borrowButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		borrowButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		borrowButtonPanel.add(borrowButton);
		centerPanel.add("South", borrowButtonPanel);
		centerPanel.setBorder(BorderFactory.createTitledBorder("Borrow a book:"));
		cp.add("Center", centerPanel);

		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(cancelButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rsBook=stm.executeQuery("select * from  Books");
			rsMember=stm.executeQuery("select * from  Members");
			rsTrans=stm.executeQuery("select * from  Transaction");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}


		borrowButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		setVisible(true);
		pack();
		
	}

	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource()==borrowButton)
			{
				//for checking if there is a missing information
				if (isCorrect())
				{
					rsBook=stm.executeQuery("SELECT * FROM Books WHERE BookID = " + data[0]);
					rsMember=stm.executeQuery("SELECT * FROM Members WHERE MemberID = " + data[1]);
					rsTrans=stm.executeQuery("SELECT * FROM Transaction");
					
					rsBook.first();
					int numberOfBooks = rsBook.getInt(11);
					
					rsTrans.first();
					int srNumber = rsTrans.getInt(1);
					srNumber +=1;
					
					if (numberOfBooks >= 1)
					{
						numberOfBooks -= 1;
						
						String sql = "update Books SET NumberOfBooks = '" + numberOfBooks + "', status ='borrowed' where BookID=" + data[0];
						ps = cn.prepareStatement(sql);
						ps.execute();
						ps.close();
								
						String sqlTrans ="INSERT INTO Transaction (SrNo, BookID, MemberID, BorroweDate, ReturnDate) VALUES ('" + srNumber + "','"+ data[0] + "','" + data[1] + "','" + data[2] + "','" + data[3] + "')";
						ps = cn.prepareStatement(sqlTrans);
						ps.execute();
						ps.close();
						
						JOptionPane.showMessageDialog(null, "The book is borrowed", "Warning", JOptionPane.WARNING_MESSAGE);
						clearTextField();
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Book not available", "Warning", JOptionPane.WARNING_MESSAGE);
						clearTextField();
					}
				
				}
				else
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		
			if(ae.getSource()==cancelButton)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}
	
	public static void main(String a[])
	{
		new BorrowBooks();
	}
}