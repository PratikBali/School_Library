import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.applet.*;
class Login extends JFrame implements ActionListener 
{
//1) declaration
JLabel l1,l2,ldate,ltime;
JTextField t1;
JButton b1,b2,b3,b4;
JPasswordField p1;
Date date;
DateFormat df;
JPanel p2,p3,p4;
JMenuBar m;
 JMenu s;
 Connection con=null;
 Statement stmt=null;
 PreparedStatement pr=null;
 ResultSet rs=null; 
  String s1,s2,s5,sql;
Login()
{
setSize(600,400);
setLocation(300,50);
try
     {
	  con=DriverManager.getConnection("jdbc:mysql:///library","root","");
	 }
 
     catch(Exception ae)
      {
        System.out.println(ae);
      }
	  date=new Date();	
setTitle("Login");
setLayout(null);

//2)memory allocation
l1=new JLabel("User name:");
l2=new JLabel("Password:");
t1=new JTextField();
b1=new JButton("Ok");
b2=new JButton("Exit");
b3=new JButton("RESET");
b4=new JButton("Forgot Password ?");
p1=new JPasswordField();
ltime=new JLabel(""+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
df=new SimpleDateFormat("yyyy-MM-dd");
ldate=new JLabel(""+df.format(date));
m=new JMenuBar();
  //s=new JMenu("Settings");
   p4=new JPanel();
	p2=new JPanel();
	p3=new JPanel();
	
	p4.setLayout(null);
	p2.setLayout(null);
	p3.setLayout(null);
//3)add on frame
add(l1);
add(l2);
add(t1);
add(p1);
add(b1);
add(b2);
add(b3);
add(p4);
 add(p2);
 add(p3);
	
	p2.setBorder(BorderFactory.createRaisedBevelBorder());		 
    p3.setBorder(BorderFactory.createRaisedBevelBorder());		 
	p4.setBorder(BorderFactory.createRaisedBevelBorder());	
 	
  
 
  p2.add(ldate);
  p2.add(ltime);
  
  p3.add(l1);
  p3.add(l2);
  p3.add(t1);
  p3.add(p1);
  
  p4.add(b1);
  p4.add(b2);
  p4.add(b3);
p4.add(b4);
   //m.add(s);
 setJMenuBar(m);
  
b1.setToolTipText("this is submit button");
b2.setToolTipText("exit");
b3.setToolTipText("clear");
//4)setBounds
p2.setBounds(10,10,410,60);
ldate.setBounds(14,5,80,20);
ltime.setBounds(330,5,80,20);
p3.setBounds(10,80,290,120);
l1.setBounds(30,20,150,40);
l2.setBounds(30,80,150,20);
t1.setBounds(180,30,90,20);
p1.setBounds(180,80,90,20);
p4.setBounds(10,220,550	,40);
b1.setBounds(50,10,100,25);
b2.setBounds(160,10,100,25);
b3.setBounds(270,10,100,25);
b4.setBounds(380,10,150,25);
//5)ActionListener
b1.addActionListener(this);
b2.addActionListener(this);
b3.addActionListener(this);
b4.addActionListener(this);
//s.addActionListener(this);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}
 public void actionPerformed(ActionEvent ae)
 {
  int cnt=0;
  int f=0;
  if(ae.getSource()==b1)
  {
	  String date1,time1;
	  try
	    {
			if(t1.getText().length()!=0 && p1.getText().length()!=0)	
			{
				 con=DriverManager.getConnection("jdbc:mysql:///library","root","");
				 stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				 //rs=stmt.executeQuery("select user,pass from login where user='"+t1.getText()+"'");
				 rs=stmt.executeQuery("select * from Login ");
        // rs.first();
		 
		 while(rs.next())
		  {
		   s1=rs.getString(1);
		   s2=rs.getString(2);
		   if(s1.equals(t1.getText()) && s2.equals(p1.getText()))
		   {
		     f=1;
			 sql="insert into LoginInfo values('"+s1+"','"+s2+"','"+ldate.getText()+"','"+ltime.getText()+"')";
			 pr=con.prepareStatement(sql);
			 pr.execute();
		   }
         }
		 
	 
      if(f==1)
	   { 
	       JOptionPane.showMessageDialog(null,"Login Successfully");
	        //new MainFrame();
            //dispose();	
       }
	 else
	   {
         JOptionPane.showMessageDialog(null,"Wrong username or password","Error",JOptionPane.ERROR_MESSAGE);
         t1.requestFocus();
       }
     }
	 else
	    {
		 JOptionPane.showMessageDialog(null,"All fields are compelsary","Error",JOptionPane.ERROR_MESSAGE);
         t1.requestFocus();
		} 
      } 
      catch(Exception e1)
       {
        System.out.println(e1);
       }
       t1.requestFocus(); 
    	
  }	 
if(ae.getSource()==b2)
{
System.exit(0);
}
if(ae.getSource()==b3)
{
t1.setText(" ");
p1.setText("");
}
if(ae.getSource()==b4)
			{ new pwd_recov();
						dispose();
			}

}
public static void main(String args[])
{
new Login();
}
}


/*
create table LOginInfo
(
username varchar(50),
password varchar(50),
login_date date,
login_time time
);
*/