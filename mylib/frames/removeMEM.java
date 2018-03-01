import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class removeMEM extends JFrame implements ActionListener
{
	//for creating the North Panel
	private JPanel northPanel = new JPanel();
	//for creating the label
	private JLabel title = new JLabel("Member INFORMATION");

	//for creating the Center Panel
	private JPanel centerPanel = new JPanel();
	//for creating an Internal Panel in the center panel
	private JPanel removePanel = new JPanel();
	//for creating the label
	private JLabel removeLabel = new JLabel(" Write the Member ID: ");
	//for creating the text field
	private JTextField removeTextField = new JTextField();
	//for creating string to store the id
	//private String id;
	private int id;
	//for creating an Internal Panel in the center panel
	private JPanel removeMemberPanel = new JPanel();
	//for creating the button
	private JButton removeButton = new JButton("Remove");
	//for creating the South Panel
	private JPanel southPanel = new JPanel();
	//for adding the button
	private JButton exitButton = new JButton("Exit");
	private String sql;
	private Connection cn=null;
	private PreparedStatement ps;
	private Statement stm;
	private ResultSet rs,rst;
	
	//for checking the information from the text field
	public boolean isCorrect()
	{
		if (!removeTextField.getText().equals(""))
		{
			//id = removeTextField.getText();
			id = Integer.parseInt(removeTextField.getText());
			return true;
		}
		else
			return false;
	}

	public removeMEM()
	{
		//for setting the title for the internal frame
		//super("Remove Members");
		super("Remove Members");
		//for setting the icon
		//setFrameIcon(new ImageIcon("images/Delete16.gif"));
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		//for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for setting the font
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for adding the label
		northPanel.add(title);
		//for adding the panel to the container
		cp.add("North", northPanel);
		//add("North", northPanel);

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
		centerPanel.setBorder(BorderFactory.createTitledBorder("Remove a Member:"));
		//for adding the center panel to the container
		cp.add("Center", centerPanel);
		//add("Center", centerPanel);

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
		cp.add("South", southPanel);
		//add("South", southPanel);
		
		removeTextField.requestFocus();
		valid(removeTextField);

		removeButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		try
		{cn = DriverManager.getConnection("jdbc:mysql:///library","root","");
		stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs=stm.executeQuery("select * from  Members");
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
		//for setting the visible to true
		setVisible(true);
		//show the internal frame
		pack();
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
	{
		//for checking if there is a missing information
		try
		{
			if(ae.getSource()==removeButton)
			{
				if (isCorrect())
				{
					rs=stm.executeQuery("select * from  Members WHERE MemberID =" + id);
					rst=stm.executeQuery("select * from  transaction");
					try
					{
						//rs.first();
						rst.first();
						int issueBook = rst.getInt(3);
						if (issueBook == id)
						{
							JOptionPane.showMessageDialog(null, "Book(s) borrowed by the member", "Warning", JOptionPane.WARNING_MESSAGE);
						}
						else
						{
							sql="DELETE FROM Members WHERE MemberID = " + id;
							ps = cn.prepareStatement(sql);
							ps.execute();
							ps.close();
							JOptionPane.showMessageDialog(null,"Member Deleted successfully");
							removeTextField.setText(null);
						}	
					}
					catch(Exception ex)	{ex.printStackTrace();}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
				}				
			}
			//if there is a missing id, then display Message Dialog
			
			if(ae.getSource()==exitButton)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
	}
		public static void main(String a[])
	{
		new removeMEM();
	}
}