import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class BillMissing extends JFrame implements ActionListener
{
	private JPanel northPanel = new JPanel();
	private JLabel northLabel = new JLabel("Missing Fine");
	private JPanel centerPanel = new JPanel();
	
	private JPanel infoPanel = new JPanel();
	private JLabel[] infoLabel = new JLabel[5];
	private String[] infoString = {" Bill No.", " Book Id",	" Member ID", " Borrow Date"," Book Price"};
	private JTextField[] infoTextField = new JTextField[5];
	
	private JPanel ButtonPanel = new JPanel();
	private JButton printButton = new JButton("Print");
	private JButton backButton = new JButton("Back");
	
	private JPanel southPanel = new JPanel();
	private JButton cancelButton = new JButton("Cancel");
	
	private Connection cn = null;
	private Statement stm = null;
	private PreparedStatement ps = null;
	private ResultSet rsBill = null;
	private ResultSet rsBorrow = null;
	
	public BillMissing(String bid, String mid)
	{
		super("Missing of Book Fine Bill");
		Container cp = getContentPane();
		
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northPanel.add(northLabel);
		cp.add("North", northPanel);
		
		centerPanel.setLayout(new BorderLayout());
		infoPanel.setLayout(new GridLayout(5, 1, 1, 5));
		
		for (int i = 0; i < infoLabel.length; i++)
		{
			infoPanel.add(infoLabel[i] = new JLabel(infoString[i]));
			
			if (i == 0 || i == 1|| i == 2|| i == 3)
			{
				infoPanel.add(infoTextField[i] = new JTextField(13));
				infoTextField[i].setEditable(false);
			}
			else
			infoPanel.add(infoTextField[i] = new JTextField(13));
		}
		centerPanel.add("Center", infoPanel);
		
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ButtonPanel.add(printButton);
		ButtonPanel.add(backButton);
		centerPanel.add("South", ButtonPanel);
		
		centerPanel.setBorder(BorderFactory.createTitledBorder("Fine Details"));
		cp.add("Center", centerPanel);
		
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(cancelButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);
		
		infoTextField[1].setText(bid);
		infoTextField[2].setText(mid);
		
				printButton.addActionListener(this);
		backButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rsBorrow=stm.executeQuery("select * from  Borrow");
			
			rsBorrow.next();
			infoTextField[3].setText(rsBorrow.getString(3));
			//infoTextField[4].setText(rsBorrow.getString(4));
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		getMax();
		
		setVisible(true);
		pack();
	}
	
	void getMax()
	{	int bno;
		try
		{
                String sql = "select max(BillNo) from Bill";
                rsBill = stm.executeQuery(sql);
                rsBill.next();
                 bno=rsBill.getInt(1);
				bno++;
				infoTextField[0].setText(""+bno);
		}
		catch(Exception e)
		{
				bno=1;
				infoTextField[0].setText(""+bno);			
		}
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==printButton)
		{
			
		}
		if(ae.getSource()==backButton)
		{
			new ReturnBooks();
			dispose();
		}
		if(ae.getSource()==cancelButton)
		{
			dispose();
		}
		
	}
	
	/*public static void main(String a[])
	{
		new BillMissing();
	}*/
}
