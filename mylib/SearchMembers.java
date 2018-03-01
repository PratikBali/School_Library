import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class SearchMembers extends JInternalFrame implements ActionListener
{
	Statement stm;
	Connection cn=null;
	PreparedStatement ps;
	ResultSet rs;

	String sql;
	int flag=0,i;
	

	JLabel lbl[] = new JLabel[9];
    String lbl_string[] =
	{
        " Member Type "," MemberID ", " Name "," Roll "," Class ", " Division "," Department "," Contact ",
		" Email "
	};

	JTextField tf[] = new JTextField[9];
	JButton btn_print = new JButton("Print");
	JButton btn_exit = new JButton("Exit");
	
    JPanel northPanel = new JPanel();  
    JLabel northLabel = new JLabel("MEMBER INFORMATION");   
    JPanel centerPanel = new JPanel();   
    JPanel lbl_Panel = new JPanel();
    JPanel tf_Panel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel southPanel = new JPanel();

	SearchMembers(String query)//constructor
	{
		super("Search Member", false, true, false, true);
		//super("Search Member");
		
		ImageIcon icon = new ImageIcon("images/Find16.gif");
		setFrameIcon(icon);

		Container cp = getContentPane();
		
		//  setting north panel for north label
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        northPanel.add(northLabel);
        cp.add("North", northPanel);
      
		// setting center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Searched member"));

		// setting Label Panel
        lbl_Panel.setLayout(new GridLayout(9, 1, 1, 1));
		for(i=0;i<9;i++)
		{
			lbl[i] = new JLabel(lbl_string[i]);
			lbl[i].setFont(new Font("Comic sans ms", Font.BOLD, 13));
			lbl_Panel.add(lbl[i]);
		}
		centerPanel.add("West", lbl_Panel);

		// TextField panel
		tf_Panel.setLayout(new GridLayout(9, 1, 1, 1));
		for(i=0;i<9;i++)
		{
			tf[i] = new JTextField(20);
			tf_Panel.add(tf[i]);
			tf[i].setEditable(false);
		}
		centerPanel.add("Center", tf_Panel);
				
		// setting insert Button
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btn_print.setFont(new Font("Comic sans ms", Font.BOLD, 13));
  
		//ButtonPanel.add(btn_print);
		centerPanel.add("South", ButtonPanel);
		
		cp.add("Center", centerPanel);
		
		//centerPanel.setVisible(false);
		
		// setting south panel
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btn_exit.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        southPanel.add(btn_exit);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);
		
		//adding listener
		btn_print.addActionListener(this);
		btn_exit.addActionListener(this);
		
		//setting ToolTipText
		btn_print.setToolTipText("Insert Member In Library");
		btn_exit.setToolTipText("Exit From This Frame");

		btn_print.setMnemonic('a');
		btn_exit.setMnemonic('x');

		final String DEFAULT_QUERY = query;
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stm.executeQuery(DEFAULT_QUERY);

			while(rs.next())
			{
				tf[0].setText(rs.getString(1));
				tf[1].setText(rs.getString(2));
				tf[2].setText(rs.getString(3));
				tf[3].setText(rs.getString(4));
				tf[4].setText(rs.getString(5));
				tf[5].setText(rs.getString(6));
				tf[6].setText(rs.getString(7));
				tf[7].setText(rs.getString(8));
				tf[8].setText(rs.getString(9));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		setVisible(true);
		pack();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btn_exit)
		{
			dispose();
		}
	}
	
/*	public static void main(String a[])
	{
		new SearchMembers();
	}*/
}