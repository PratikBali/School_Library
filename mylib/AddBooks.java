import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class AddBooks extends JInternalFrame implements ActionListener
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
	JButton btn_addBook = new JButton("Add Book");
	JButton btn_exit = new JButton("Exit");
	JButton btn_clr = new JButton("Clear");
	JButton btn_chk = new JButton("Check");
	
    JPanel northPanel = new JPanel();  
    JLabel northLabel = new JLabel("BOOK INFORMATION");   
    JPanel centerPanel = new JPanel();   
    JPanel lbl_Panel = new JPanel();
    JPanel tf_Panel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel southPanel = new JPanel();

	public AddBooks()
	{
		super("Add Books", false, true, false, true);
		
			
			ImageIcon icon = new ImageIcon("images/Add16.gif");
			setFrameIcon(icon);
		
		//setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));
		Container cp = getContentPane();
		
		//Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//  setting north panel for north label
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        northPanel.add(northLabel);
        cp.add("North", northPanel);
      
		// setting center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new book:"));

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
			tf[i] = new JTextField(11);
			tf_Panel.add(tf[i]);
		}
		centerPanel.add("Center", tf_Panel);
		
		// setting insert Button
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btn_addBook.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        btn_clr.setFont(new Font("Comic sans ms", Font.BOLD, 13));
		ButtonPanel.add(btn_clr);
		ButtonPanel.add(btn_addBook);
		btn_addBook.setVisible(false);
		ButtonPanel.add(btn_chk);
		centerPanel.add("South", ButtonPanel);
		
		cp.add("Center", centerPanel);
		
		// settting south panel
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btn_exit.setFont(new Font("Comic sans ms", Font.BOLD, 13));
        southPanel.add(btn_exit);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);
		
		btn_addBook.addActionListener(this);
		btn_exit.addActionListener(this);
		btn_clr.addActionListener(this);
		btn_chk.addActionListener(this);
		
		btn_addBook.setToolTipText("Insert Book In Library");
		btn_exit.setToolTipText("Exit From This Frame");
		btn_clr.setToolTipText("Clears each Text Field");
		
		btn_addBook.setMnemonic('a');
		btn_exit.setMnemonic('x');
		btn_clr.setMnemonic('c');
		
		
		
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

		valid(tf[0]);
		valid(tf[4]);
		valid(tf[7]);
		valid(tf[8]);
		valid(tf[9]);
		valid(tf[10]);
		
		setVisible(true);
		pack();
	}
	
	public boolean emptyCheck()
	{
		for(i=0;i<11;i++)
		{
			if(tf[i].getText().length()==0)
			return false;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource()==btn_chk)
			{
				if(emptyCheck())
				{
					btn_addBook.setVisible(true);
					btn_chk.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"All Fields Are Necessory");
					tf[0].requestFocus();	
					//break;
				}
			}
			
			if(e.getSource()==btn_addBook)
			{
				String BookID = tf[0].getText();
				String sub = tf[1].getText();
				String lang = tf[2].getText();
				String title = tf[3].getText();
				String ver = tf[4].getText();
				String auth = tf[5].getText();
				String pub = tf[6].getText();
				String copyrt = tf[7].getText();
				String editn = tf[8].getText();
				String pno = tf[9].getText();
				String copies = tf[10].getText();

				rs=stm.executeQuery("select *from Books");	
				while(rs.next())
				{
					if(rs.getString(1).equals(BookID))
					{
						flag=1;
						JOptionPane.showMessageDialog(null,"Book already exist");
						tf[0].requestFocus();
					}
				}
					
				if(flag==0)
				{	
					sql = "insert into Books(BookID, Subject, Language, Title, Version, Author, Publisher, Copyright, Edition, Pages,	NumberOfBooks) values('"+BookID+"','"+sub+"','"+lang+"','"+title+"','"+ver+"','"+auth+"','"+pub+"','"+copyrt+"','"+editn+"','"+pno+"','"+copies+"')";
							
					ps = cn.prepareStatement(sql);
					ps.execute();
					ps.close();
					JOptionPane.showMessageDialog(null,"Book Added successfully");
					btn_chk.setVisible(true);
					btn_addBook.setVisible(false);
					//break;
					clrscr();
				}
			}
			if(e.getSource()==btn_clr)
			{
				clrscr();
			}
			if(e.getSource()==btn_exit)
			{
						dispose();
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,""+ex);
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
	
	public void clrscr()
	{
		for(i=0;i<11;i++)
				{
					tf[i].setText("");
					tf[0].requestFocus();
				}	
	}
	
	
	public static void main(String a[])
	{
		new AddBooks();
	}
}

/**
create database library;
use library;
create table Books
(
	BookID varchar(50) primary key,
	Subject varchar(50),
	Language varchar(50),
	Title varchar(100),
	Version varchar(50),
	Author varchar(50),
	Publisher varchar(50),
	Copyright varchar(50),
	Edition varchar(50),
	Pages varchar(10),
	NumberOfBooks varchar(10)
);

insert into Books values ('zs34','study','mar','yuvak','3.2','pratik','max','yes','2014','99','3');
select * from Books;
**/

