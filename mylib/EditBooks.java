import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditBooks extends JInternalFrame implements ActionListener
{
	//for creating the North Panel
	private JPanel northPanel = new JPanel();
	//for creating the North Label
	private JLabel northLabel = new JLabel("BOOK INFORMATION");

	//for creating the Center Panel
	private JPanel centerPanel = new JPanel();
	//for creating the edit Panel
	private JPanel editPanel = new JPanel();
	//for creating the edit information Panel
	private JPanel editInformationPanel = new JPanel();
	//for creating the edit label panel
	private JPanel editInformationLabelPanel = new JPanel();
	//for creating the edit texinformationTextFieldield panel
	private JPanel editInformationTextFieldPanel = new JPanel();
	//for creating the edit button panel
	private JPanel editInformationButtonPanel = new JPanel();

	//for creating the label
	private JLabel editLabel = new JLabel("BookID ");
	//for creating the texinformationTextFieldield
	private JTextField editTextField = new JTextField(25);
	//for creating the button
	private JButton editButton = new JButton("Search");

	//for creating the information Panel
	private JPanel informationPanel = new JPanel();
	//for creating an Internal Panel in the center panel
	private JPanel informationLabelPanel = new JPanel();

	//for creating an array of JLabel
	private JLabel[] informationLabel = new JLabel[10];

	//for creating an array of String
	private String[] informationString = {
											" BookID ",
											" The Book Subject ",
											" The Book Language ",
											" The Book Title ",
											" The Book Version ",
											" The Name of the Author(s) ",
											" The name of the Publisher ",
											" Copyright for the book ",
											" The Edition Number ",
											" The Number of Books ",
										};
											
	//for creating an Internal Panel in the center panel
	private JPanel informationTextFieldPanel = new JPanel();
	//for creating an array of JTextField
	private JTextField[] informationTextField = new JTextField[10];

	//for creating an Internal Panel in the center panel
	private JPanel updateInformationButtonPanel = new JPanel();
	//for creating a button
	private JButton updateInformationButton = new JButton("Update the Information");

	//for creating South Panel
	private JPanel southPanel = new JPanel();
	//for creating a button
	private JButton exitButton = new JButton("Exit");

	//for creating an array of string to store the data
	private String[] data;
	
	String sql;
	Connection cn=null;
	PreparedStatement ps;
	Statement stm;
	ResultSet rs,rs1;

	//constructor of addBooks
	public EditBooks()
	{
		//for setting the title for the internal frame
		super("Edit Books", false, true, false, true);
		//super("Edit Books");
		//for setting the icon
		setFrameIcon(new ImageIcon("images/Edit16.gif"));
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		//for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for setting the font for the North Panel
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for adding the label in the North Panel
		northPanel.add(northLabel);
		//for adding the north panel to the container
		//add("North", northPanel);
		cp.add("North", northPanel);

		//for setting the layout
		centerPanel.setLayout(new BorderLayout());
		//for setting the layout
		editPanel.setLayout(new BorderLayout());
		//for setting the border to the panel
		editPanel.setBorder(BorderFactory.createTitledBorder("BookID: "));
		//for setting the layout
		editInformationPanel.setLayout(new BorderLayout());
		//for setting the layout
		editInformationLabelPanel.setLayout(new GridLayout(1, 1, 1, 1));
		//for adding the label to the panel
		editInformationLabelPanel.add(editLabel);
		//for setting the font to the label
		editLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		//for adding the editInformationLabelPanel to the editInformationLabel
		editInformationPanel.add("West", editInformationLabelPanel);
		//for setting the layout
		editInformationTextFieldPanel.setLayout(new GridLayout(1, 1, 1, 1));
		//for adding the texinformationTextFieldield to the panel
		editInformationTextFieldPanel.add(editTextField);
		//for setting the font to the texinformationTextFieldield
		editTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		//for adding the editInformationTexinformationTextFieldield to the editInformationPanel
		editInformationPanel.add("East", editInformationTextFieldPanel);
		//for adding the editInformationPanel to the editPanel
		editPanel.add("North", editInformationPanel);
		//for setting the layout
		editInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//for adding the button to the panel
		editInformationButtonPanel.add(editButton);
		//for setting the fonr to the button
		editButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		//for adding the editInformationButtonPanel to the editPanel
		editPanel.add("Center", editInformationButtonPanel);
		//for adding the editPanel to the centerPanel
		centerPanel.add("North", editPanel);

		//for setting the layout
		informationPanel.setLayout(new BorderLayout());
		//for setting the border to the panel
		informationPanel.setBorder(BorderFactory.createTitledBorder("Edit a book: "));
		//for setting the layout
		informationLabelPanel.setLayout(new GridLayout(10, 1, 1, 1));
		for (int i = 0; i < informationLabel.length; i++)
		{
			informationLabelPanel.add(informationLabel[i] = new JLabel(informationString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		informationPanel.add("West", informationLabelPanel);

		//for setting the layout
		informationTextFieldPanel.setLayout(new GridLayout(10, 1, 1, 1));
		for (int i = 0; i < informationTextField.length; i++) {
			informationTextFieldPanel.add(informationTextField[i] = new JTextField(25));
			informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		informationPanel.add("East", informationTextFieldPanel);

		updateInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		updateInformationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		updateInformationButtonPanel.add(updateInformationButton);
		informationPanel.add("South", updateInformationButtonPanel);
		centerPanel.add("Center", informationPanel);
		cp.add("Center", centerPanel);
		//add("Center", centerPanel);

		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(exitButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);
		//add("South", southPanel);
		 
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs1=stm.executeQuery("select * from  Books");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		 
		editButton.addActionListener(this);
		updateInformationButton.addActionListener(this);
		exitButton.addActionListener(this);
		//for setting the visible to true
		setVisible(true);
		//show the internal frame
		pack();
		valid(informationTextField[0]);
		valid(informationTextField[7]);
		valid(informationTextField[8]);
		valid(informationTextField[9]);
	}
	//for checking the information from the text field
	public boolean isCorrect()
	{
		data = new String[10];
		for (int i = 0; i < informationLabel.length; i++)
		{
			if (!informationTextField[i].getText().equals(""))
			{
				data[i] = informationTextField[i].getText();
			}
			else
				return false;
		}
		return true;
	}

	//for checking the information from the text field
	public boolean isEditCorrect()
	{
		if (editTextField.getText().equals(""))
			return false;
		return true;
	}

	//for setting the array of JTextField to empty
	public void clearTextField()
	{
		editTextField.setText(null);
		for (int i = 0; i < informationTextField.length; i++)
		{
			informationTextField[i].setText(null);
		}
	}
	void valid(final JTextField tt)
	{
		tt.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				if(tt.getText().length()<10 && e.getKeyChar()>='0' && e.getKeyChar()<='9')

				super.keyTyped(e);    // optional

				else
				{
					e.consume();		// diascard the event
					Toolkit tk=Toolkit.getDefaultToolkit();
					tk.beep();	// raise the sound
				}
            }
		});
	}
	public void actionPerformed(ActionEvent ae)
	{int flag = 0;
		try
		{
			if(ae.getSource()==editButton)
			{
				for (int i = 0; i < informationLabel.length; i++)
				{	informationTextField[0].setEditable(false);
				}
				if (isEditCorrect())
				{
					int data = Integer.parseInt(editTextField.getText());	
					rs=stm.executeQuery("select * from  Books WHERE BookID =" + data);
					while(rs.next())
					{
						if(data==rs.getInt(1))
						{ flag=1;
							
							informationTextField[0].setText(rs.getString(1));
							informationTextField[1].setText(rs.getString(2));
							informationTextField[2].setText(rs.getString(3));
							informationTextField[3].setText(rs.getString(4));
							informationTextField[4].setText(rs.getString(5));
							informationTextField[5].setText(rs.getString(6));
							informationTextField[6].setText(rs.getString(7));
							informationTextField[7].setText(rs.getString(8));
							informationTextField[8].setText(rs.getString(9));
							informationTextField[9].setText(rs.getString(11));
						}
					}
					if(flag==0)
					{
						JOptionPane.showMessageDialog(null, "Book Not Found", "Error", JOptionPane.ERROR_MESSAGE);
						editTextField.setText(null);
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Please, write a correct BookID", "Error", JOptionPane.ERROR_MESSAGE);
					editTextField.setText(null);
					clearTextField();
				}
				editTextField.selectAll();
				editTextField.requestFocus();
			}
			
			if(ae.getSource()==updateInformationButton)
			{
				if (isCorrect())
				{
					//BookID, Subject, Language, Title, Version, Author, Publisher, Copyright, Edition, Pages,	NumberOfBooks
					
					sql=("UPDATE Books SET BookID = '" + data[0] + "',	Subject = '" + data[1] + "',Language = '" + data[2] + "',Title = '" + data[3] +"',	Version='" + data[4] + "',Author ='" + data[5] + "',Publisher ='" + data[6] + "',Copyright =" + data[7] + ",Edition ='" + data[8] + "',	NumberOfBooks ='" + data[9] + "' WHERE BookID ='" + editTextField.getText() +"'");
									
							//for setting the array of JTextField to empty
					ps = cn.prepareStatement(sql);
					ps.execute();
					ps.close();
					JOptionPane.showMessageDialog(null,"Record Updated Successfully");
					clearTextField();
				
				}
				//if there is a missing data, then display Message Dialog
				else
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			//for adding the action listener for the button to dispose the frame
			if(ae.getSource()==exitButton)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
	}

}