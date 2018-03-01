import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class EditMembers extends JInternalFrame implements ActionListener
{
	private JPanel northPanel = new JPanel();
	private JLabel northLabel = new JLabel("MEMBER INFORMATION");
	private JPanel centerPanel = new JPanel();
	private JPanel editPanel = new JPanel();
	private JPanel editInformationPanel = new JPanel();
	private JPanel editInformationLabelPanel = new JPanel();
	private JPanel edittfPanel = new JPanel();
	private JPanel editButtonPanel = new JPanel();
	private JLabel editLabel = new JLabel("MemberID ");
	private JTextField editTextField = new JTextField(25);
	private JButton editButton = new JButton("Search");
	private JPanel informationPanel = new JPanel();
	private JPanel informationLabelPanel = new JPanel();
	private JLabel[] informationLabel = new JLabel[8];
	private String[] informaionString = {" Member ID ", " Name ", " Roll ",
	                                     " Class ", " Division ", " Department", "Phone ","Email ID"};
	private JPanel tfPanel = new JPanel();
	private JTextField[] tf = new JTextField[8];
	private JPanel updateInformationButtonPanel = new JPanel();
	private JButton updateInformationButton = new JButton("Update the Information");
	private JPanel southPanel = new JPanel();
	private JButton OKButton = new JButton("Exit");
	private String[] data;
	private String sql;
	private Connection cn=null;
	private PreparedStatement ps;
	private Statement stm;
	private ResultSet rs,rsmt,rsm;
		int flag=0,flag2=0;
	//constructor of addMembers
	public EditMembers() 
	{
		//for setting the title for the internal frame
		//super("Edit Members");
		super("Edit Members", false, true, false, true);
		//for setting the icon
		setFrameIcon(new ImageIcon("images/Edit16.gif"));
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		//for setting the layout
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for setting the font
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for adding the label to the panel
		northPanel.add(northLabel);
		//for adding the panel to the container
		cp.add("North", northPanel);

		//for setting the layout
		centerPanel.setLayout(new BorderLayout());
		//for setting the layout
		editPanel.setLayout(new BorderLayout());
		//for setting the border to the panel
		editPanel.setBorder(BorderFactory.createTitledBorder("MemberID: "));
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
		edittfPanel.setLayout(new GridLayout(1, 1, 1, 1));
		//for adding the textField to the panel
		edittfPanel.add(editTextField);
		//for setting the font to the textField
		editTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		//for adding the edittf to the editInformationPanel
		editInformationPanel.add("East", edittfPanel);
		//for adding the editInformationPanel to the editPanel
		editPanel.add("North", editInformationPanel);
		//for setting the layout
		editButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//for adding the button to the panel
		editButtonPanel.add(editButton);
		//for setting the fonr to the button
		editButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		//for adding the editInformationButtonPanel to the editPanel
		editPanel.add("Center", editButtonPanel);
		//for adding the editPanel to the centerPanel
		centerPanel.add("North", editPanel);

		//for setting the layout
		informationPanel.setLayout(new BorderLayout());
		//for setting the border to the panel
		informationPanel.setBorder(BorderFactory.createTitledBorder("Edit a member: "));
		//for setting the layout
		informationLabelPanel.setLayout(new GridLayout(8, 1, 1, 1));
		//for setting the layout
		tfPanel.setLayout(new GridLayout(8, 1, 1, 1));

		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(informaionString[i]));
			informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
		}
		//for adding the panel to the centerPanel
		informationPanel.add("West", informationLabelPanel);

		for (int i = 0; i < informationLabel.length; i++)
		{
			tfPanel.add(tf[i] = new JTextField(25));
			tf[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
			tf[i].requestFocus();
		}
		informationPanel.add("East", tfPanel);

		updateInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		updateInformationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		updateInformationButtonPanel.add(updateInformationButton);
		informationPanel.add("South", updateInformationButtonPanel);
		centerPanel.add("Center", informationPanel);
		cp.add("Center", centerPanel);

		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		OKButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(OKButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rsm=stm.executeQuery("select * from  Members");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		updateInformationButton.addActionListener(this);
		editButton.addActionListener(this);
		OKButton.addActionListener(this);
		
		valid(tf[2]);
		valid(tf[6]);
		
		editTextField.requestFocus();
		
		setVisible(true);
		
		pack();
		
		Hide();

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
	
	public boolean isEditCorrect()
	{
		if (editTextField.getText().equals(""))
			return false;
		return true;
	}

	public void clearTextField()
	{
		editTextField.setText(null);
		for (int i = 0; i < informationLabel.length; i++)
		{
			tf[i].setText(null);
		}
	}

	public void Hide()
	{
		for (int i = 0; i < informationLabel.length; i++)
		{
			tf[i].setEditable(false);
		}
	}
	public void Show()
	{
		tf[0].setEditable(true);//show memberID
		tf[1].setEditable(true);//show Member Name
		tf[6].setEditable(true);//show contact
		tf[7].setEditable(true);//show Email	
	}	

	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource()== editButton)
			{
				for (int i = 0; i < informationLabel.length; i++)
				{	tf[0].setEditable(false);
				}
				if (isEditCorrect())
				{
					int data = Integer.parseInt(editTextField.getText());	
					rsm=stm.executeQuery("select * from Members where MemberID="+data);
					while(rsm.next())
					{
						if(rsm.getInt(2)==data)
						{	flag=1;
							System.out.println(data);
					
							if(rsm.getString(1).equals("teacher"))
							{	//flag2=1;
								Show(); // shows memberID,name,phone,email
								tf[0].setEditable(false);
								tf[3].setEditable(false);//hide class
								tf[4].setEditable(false);//hide division
								tf[2].setEditable(false);//hide roll
								tf[5].setEditable(true);//show department
							}
							else if(rsm.getString(1).equals("student"))
							{
								Show(); // shows memberID,name,phone,email
								tf[0].setEditable(false);
								tf[3].setEditable(true);//show class
								tf[4].setEditable(true);//show division
								tf[2].setEditable(true);//show roll
								tf[5].setEditable(false);//hide department
							}
						}
					}						
					if(flag==0)
					{
						JOptionPane.showMessageDialog(null, "Member Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
						editTextField.setText(null);
						editTextField.requestFocus();
						clearTextField();
						//break;
					}
	
					rs=stm.executeQuery("select * from  Members WHERE MemberID ='" + data +"'");
					while(rs.next())
					{
						tf[0].setText(rs.getString(2));
						tf[1].setText(rs.getString(3));
						tf[2].setText(rs.getString(4));
						tf[3].setText(rs.getString(5));
						tf[4].setText(rs.getString(6));
						tf[5].setText(rs.getString(7));
						tf[6].setText(rs.getString(8));
						tf[7].setText(rs.getString(9));
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please, write the MemberID", "Warning", JOptionPane.WARNING_MESSAGE);
					editTextField.setText(null);
					editTextField.requestFocus();
					clearTextField();
				}
				editTextField.selectAll();
				editTextField.requestFocus();
			}
			
			if(ae.getSource()==updateInformationButton)
			{
				String name = tf[1].getText();
				String roll = tf[2].getText();
				String cls = tf[3].getText();
				String div = tf[4].getText();
				String dept = tf[5].getText();
				String contact = tf[6].getText();
				String email = tf[7].getText();

				int data = Integer.parseInt(editTextField.getText());	
				rsm=stm.executeQuery("select * from  Members where MemberID="+data);
				rsm.next();
				
				if(rsm.getString(1).equals("teacher"))
				{
					if(tf[0].getText().length()==0 || tf[1].getText().length()==0|| tf[5].getText().length()==0|| tf[6].getText().length()==0|| tf[7].getText().length()==0)
					{
						JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
						tf[0].requestFocus();
					}
					else
					{
						sql=("UPDATE Members SET Name = '"+name+"', Dept = '" +dept + "', Phone = '"+contact+"', Email = '"+email+"' WHERE MemberID = " + data);
						ps = cn.prepareStatement(sql);
						ps.execute();
						ps.close();
						JOptionPane.showMessageDialog(null,"Record Updated Successfully");
						clearTextField();
					}
				}
								
				else if(rsm.getString(1).equals("student"))
				{
					if(tf[0].getText().length()==0 || tf[1].getText().length()==0|| tf[2].getText().length()==0|| tf[3].getText().length()==0|| tf[4].getText().length()==0|| tf[6].getText().length()==0|| tf[7].getText().length()==0)
					{
						JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
						tf[0].requestFocus();
					}
					else
					{
						String sql2=("UPDATE Members SET Name = '"+name+"',Roll='"+roll+"', Class = '"
					+ cls + "', Division = '" + div + "', Phone = '"+contact+"', Email = '"+email+"' WHERE MemberID = " + data);
						ps = cn.prepareStatement(sql2);
						ps.execute();
						ps.close();
						JOptionPane.showMessageDialog(null,"Record Updated Successfully");
						clearTextField();
					}
				}

			}				
			if(ae.getSource()==OKButton)
			{
			System.exit(0);
			}
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
	}
		//for adding the action listener for the button to dispose the frame
		
	public static void main (String a[])
	{	new EditMembers();
	}	
}