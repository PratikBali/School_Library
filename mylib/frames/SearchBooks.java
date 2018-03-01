import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class SearchBooks extends JFrame implements ActionListener
{
	Statement stm;
	Connection cn=null;
	PreparedStatement ps;
	ResultSet rs;

	String sql;
	int flag=0,i;
	

	JLabel lbl[] = new JLabel[11];
    String lbl_string[] =
	{
        " BookID ", " The Book Subject "," The Book Language "," The Book Title ",
        " The Book Version ", " The Name of the Author(s) "," The name of the Publisher ",
        " Copyright for the book ", " The Edition Number ", " The Number of Pages ",
        " The Number of Copies "
	};

	JTextField tf[] = new JTextField[11];
	JButton btn_print = new JButton("Print");
	JButton btn_exit = new JButton("Exit");
	
    JPanel northPanel = new JPanel();  
    JLabel northLabel = new JLabel("BOOK INFORMATION");   
    JPanel centerPanel = new JPanel();   
    JPanel lbl_Panel = new JPanel();
    JPanel tf_Panel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel southPanel = new JPanel();

	public SearchBooks()
	{
		//super("Add Books", false, true, false, true);
		super("Search Books");	
			
		ImageIcon icon = new ImageIcon("images/Add16.gif");
		//setFrameIcon(icon);
		
		//setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));
		Container cp = getContentPane();
		
		//Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//setting north panel for north label
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        northPanel.add(northLabel);
        cp.add("North", northPanel);
      
		// setting center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Searched book:"));

		// setting Label Panel
        lbl_Panel.setLayout(new GridLayout(11, 1, 1, 1));
		for(i=0;i<11;i++)
		{
			lbl[i] = new JLabel(lbl_string[i]);
			lbl[i].setFont(new Font("Comic sans ms", Font.BOLD, 13));
			lbl_Panel.add(lbl[i]);
		}
		centerPanel.add("West", lbl_Panel);

		// setting TextField Panel
		tf_Panel.setLayout(new GridLayout(11, 1, 1, 1));
		for(i=0;i<11;i++)
		{
			tf[i] = new JTextField(20);
			tf_Panel.add(tf[i]);
			tf[i].setEditable(false);
		}
		
		centerPanel.add("Center", tf_Panel);
		
		// setting insert Button
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btn_print.setFont(new Font("Comic sans ms", Font.BOLD, 13));
		ButtonPanel.add(btn_print);
		centerPanel.add("South", ButtonPanel);
		
		cp.add("Center", centerPanel);
		
		// settting south panel
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btn_exit.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        southPanel.add(btn_exit);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);
		
		btn_print.addActionListener(this);

		btn_exit.setToolTipText("Exit From This Frame");
		btn_print.setToolTipText("Clears each Text Field");
		
		btn_exit.setMnemonic('x');
		btn_print.setMnemonic('c');
	
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
	
		setVisible(true);
		pack();
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
	
	}
	
	
	public static void main(String a[])
	{
		new SearchBooks();
	}
}