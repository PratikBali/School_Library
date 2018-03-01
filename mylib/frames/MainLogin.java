import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.applet.*;

class MainLogin extends JFrame implements ActionListener
{
	JLabel lbl_user,lbl_pass,ltime,ldate;
	JButton btn_log,btn_ext,btn_rec;
	JTextField tf_user;
	JPasswordField pass;
 
	Date date;
	DateFormat df;
	
	String sql;
	int flag=0;

	Connection cn=null;
	PreparedStatement ps;
	Statement stm;
	ResultSet rs;
	
	MainLogin()
	{
		setTitle("Login And Recovery");
		setSize(370,200);
		setLocation(100,100);
		setLayout(null);

		btn_log = new JButton("Login");
		btn_rec = new JButton("Forgot Password");
		btn_ext = new JButton("Exit");
		lbl_user = new JLabel("Enter Username  ");	tf_user = new JTextField();
		lbl_pass = new JLabel("Enter Password  ");	pass = new JPasswordField();
	
		date=new Date();	
		ltime=new JLabel(""+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
		df=new SimpleDateFormat("yyyy-MM-dd");
		ldate=new JLabel(""+df.format(date));

		add(lbl_user);add(lbl_pass);add(tf_user);add(pass);add(btn_log);add(btn_ext);add(btn_rec);

		lbl_user.setBounds(20,30,150,20);	tf_user.setBounds(180,30,150,20);
		lbl_pass.setBounds(20,60,150,20); 	pass.setBounds(180,60,150,20);
		
		btn_rec.setBounds(20,110,140,20);
		btn_log.setBounds(170,110,70,20);
		btn_ext.setBounds(250,110,80,20);
		
		btn_log.setMnemonic('l');
		btn_rec.setMnemonic('f');
		btn_ext.setMnemonic('x');
		
		btn_log.setToolTipText("Login");
		btn_rec.setToolTipText("Recover Your Password");
		btn_ext.setToolTipText("Exit");
		
		
		btn_log.addActionListener(this);
		btn_rec.addActionListener(this);
		btn_ext.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try
		{	cn = DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e)
	{int f=0;
		try
		{
			if(e.getSource()==btn_ext)
			System.exit(0);

			if(e.getSource()==btn_rec)
			{ new MainRecover();
						dispose();
			}
			
			if(e.getSource()==btn_log)
			{
				if(tf_user.getText().length()!=0 && pass.getText().length()!=0)	
				{
					rs=stm.executeQuery("select * from Login ");
					while(rs.next())
					{
						String s1=rs.getString(1);
						String s2=rs.getString(2);
						if(s1.equals(tf_user.getText()) && s2.equals(pass.getText()))
						{
							f=1;
							sql="insert into LoginInfo values('"+s1+"','"+s2+"','"+ldate.getText()+"','"+ltime.getText()+"')";
							ps=cn.prepareStatement(sql);
							ps.execute();
						}
					}
					if(f==1)
					{ 
						JOptionPane.showMessageDialog(null,"Login Successfully");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Wrong username or password","Error",JOptionPane.ERROR_MESSAGE);
						tf_user.requestFocus();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"All fields are compelsary","Error",JOptionPane.ERROR_MESSAGE);
					tf_user.requestFocus();
				} 
			}
		}
		catch(SQLException ex)
		{	JOptionPane.showMessageDialog(this,""+ex);
		}
	}
	public static void main(String a[])
	{
		new MainLogin();
	}
}

/**
create database pizza;
use pizza;

create table Login
(
	name varchar(50) primary key,
	password varchar(100),
	question varchar(100),
	answer varchar(100)
);
insert into Login values('pratik','pratik123','Favourite Bike','hyabusa');
select * from Login;
**/
