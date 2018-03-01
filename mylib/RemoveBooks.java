import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveBooks extends JInternalFrame implements ActionListener
{
	//for creating the North Panel
	private JPanel northPanel = new JPanel();
	//for creating the label
	private JLabel title = new JLabel("BOOK INFORMATION");

	//for creating the Center Panel
	private JPanel centerPanel = new JPanel();
	//for creating an Internal Panel in the center panel
	private JPanel removePanel = new JPanel();
	//for creating the label
	private JLabel removeLabel = new JLabel(" Write the Book ID: ");
	//for creating the text field
	private JTextField removeTextField = new JTextField();
	//for creating string to store the data
	private int data;
	//for creating an Internal Panel in the center panel
	private JPanel removeMemberPanel = new JPanel();
	//for creating the button
	private JButton removeButton = new JButton("Remove");
	//for creating the South Panel
	private JPanel southPanel = new JPanel();
	//for adding the button
	private JButton exitButton = new JButton("Exit");
		String sql;
	Connection cn=null;
	PreparedStatement ps;
	Statement stm;
	ResultSet rs;
	
	//for checking the information from the text field
	public boolean isCorrect()
	{
		if (!removeTextField.getText().equals(""))
		{
			data = Integer.parseInt(removeTextField.getText());
			return true;
		}
		else
			return false;
	}

	public RemoveBooks()
	{
		//for setting the title for the internal frame
		//super("Remove Books");
		super("Remove Books", false, true, false, true);
		//for setting the icon
		setFrameIcon(new ImageIcon("images/Delete16.gif"));
		//for getting the graphical user interface components display area
		//Container cp = getContentPane();

		//for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for setting the font
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for adding the label
		northPanel.add(title);
		//for adding the panel to the container
		//cp.add("North", northPanel);
		add("North", northPanel);

		//for setting the layout
		centerPanel.setLayout(new BorderLayout());
		//for setting the layout
		removePanel.setLayout(new GridLayout(1, 2, 1, 1));
		//for adding the label
		removePanel.add(removeLabel);
		//for adding the text field
		removePanel.add(removeTextField);
		//for adding the internal panel to the panel
		centerPanel.add("Center", removePanel);

		//for setting the layout
		removeMemberPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//for adding the button
		removeMemberPanel.add(removeButton);
		//for addint the internal panel to the center panel
		centerPanel.add("South", removeMemberPanel);
		//for setting the border
		centerPanel.setBorder(BorderFactory.createTitledBorder("Remove a book:"));
		//for adding the center panel to the container
		//cp.add("Center", centerPanel);
		add("Center", centerPanel);

		//for setting the font for the label & buttons
		removeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		removeTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		removeButton.setFont(new Font("Tahoma", Font.BOLD, 11));

		//for setting the layout
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//for adding the button
		southPanel.add(exitButton);
		//for setting the border
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		//for add the south panel to the container
		//cp.add("South", southPanel);
		add("South", southPanel);

		removeButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		try
		{cn = DriverManager.getConnection("jdbc:mysql:///library","root","");
		stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs=stm.executeQuery("select * from  Books");
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
		//for setting the visible to true
		setVisible(true);
		//show the internal frame
		pack();
	}
		
	public void actionPerformed(ActionEvent ae)
	{
		//for checking if there is a missing information
		try
		{
			if(ae.getSource()==removeButton)
			{
				if (isCorrect())
				{
					//for getting the information
					rs=stm.executeQuery("select * from  Books WHERE BookID =" + data);
					try
					{
						rs.first();
						int numberOfBooks = rs.getInt(11);		
						int bid = rs.getInt(1);
						if(bid==data)
						{
						
							if (numberOfBooks == 1)
							{
								sql = "delete from Books where BookID=" + data;
								ps = cn.prepareStatement(sql);
								ps.execute();
								ps.close();
								JOptionPane.showMessageDialog(null,"Book Deleted successfully");
								//for setting JTextField to null
								removeTextField.setText(null);
							}
							if (numberOfBooks > 1)
							{
								numberOfBooks -= 1;
								String query = "UPDATE Books SET NumberOfBooks = " + numberOfBooks + " WHERE BookID = " + data;
								ps = cn.prepareStatement(query);
								ps.execute();
								ps.close();
								JOptionPane.showMessageDialog(null,"Book Decrease by 1 Available Books are "+ numberOfBooks);
								//for setting JTextField to null
								removeTextField.setText(null);
							}
						}
						else
						{
						JOptionPane.showMessageDialog(null,"Book not Available");
									
						}
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null,"Book not Available");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
				}				
			}
			//if there is a missing data, then display Message Dialog
			
			if(ae.getSource()==exitButton)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
	}
	public static void main (String a[])
	{new RemoveBooks();
	}
}