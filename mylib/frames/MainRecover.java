import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class MainRecover extends JFrame implements ActionListener
{
	JLabel l_user,l_que,l_ans;
	JTextField t_user,t_ans;
	JComboBox cb;
	JButton btn_recov,btn_ext,btn_back;

	String sql;
	int flag=0;

	Connection cn=null;
	PreparedStatement ps;
	Statement stm;
	ResultSet rs;

	MainRecover()
	{
		setTitle("Recover Your Password");
		setSize(370,250);
		setLocation(150,100);
		setLayout(null);

		cb=new JComboBox();

		l_user = new JLabel("Enter User Name");
		l_que = new JLabel("Enter Question    ");
		l_ans = new JLabel("Enter Answer     ");

		t_user = new JTextField();
		t_ans = new JTextField();

		btn_recov = new JButton("Recover");
		btn_back = new JButton("Back");
		btn_ext = new JButton("Exit");

		add(l_user);	add(l_que);	add(l_ans);
		add(t_user);	add(t_ans);
		add(btn_recov);	add(btn_back);	add(btn_ext);
		cb.addItem("Select Question");
		cb.addItem("First School");
		cb.addItem("First College");
		cb.addItem("Fav Bike");
		cb.addItem("Fav Teacher");
		cb.addItem("Fav Food");	
		cb.addItem("Best Friend");
		cb.addItem("Fav Player");
		cb.addItem("Fav Game");
		add(cb);

		l_user.setBounds(10,40,110,20);
		l_que.setBounds(10,70,110,20);
		l_ans.setBounds(10,100,110,20);

		t_user.setBounds(130,40,200,20);
		cb.setBounds(130,70,200,20);
		t_ans.setBounds(130,100,200,20);

		btn_back.setBounds(10,140,100,30);
		btn_recov.setBounds(120,140,100,30);
		btn_ext.setBounds(230,140,100,30);

		btn_recov.addActionListener(this);
		btn_back.addActionListener(this);
		btn_ext.addActionListener(this);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);

		try
		{	cn = DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(Exception e)
		{e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource()==btn_ext)
			{ new MainLogin();
			dispose();
			}
			if(e.getSource()==btn_back)
			{ new MainLogin();
			dispose();			}
			if(e.getSource()==btn_recov)
			{
				if(t_user.getText().length()==0 || t_ans.getText().length()==0 || cb.getSelectedIndex()==0)
				{
					JOptionPane.showMessageDialog(null,"All Fields Are Necessory");
					if(t_user.getText().length()==0)
						JOptionPane.showMessageDialog(null,"Please Enter Username","Error",JOptionPane.ERROR_MESSAGE);
					if(cb.getSelectedIndex()==0)
						JOptionPane.showMessageDialog(null,"Please Select Question","Error",JOptionPane.ERROR_MESSAGE);
					if(t_ans.getText().length()==0)
						JOptionPane.showMessageDialog(null,"Please Enter Answer","Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					rs = stm.executeQuery("select * from Login");
					String id = t_user.getText();
					String itm = cb.getSelectedItem().toString();
					String ans = t_ans.getText();

					while(rs.next())
					{
						if(rs.getString(1).equals(id) && rs.getString(3).equals(itm) && rs.getString(4).equals(ans))
						{
							flag=1;
							JOptionPane.showMessageDialog(null,"Your Password is "+rs.getString(2),"Recovery Successful",JOptionPane.WARNING_MESSAGE);
						}
					}
					if(flag==0)
							JOptionPane.showMessageDialog(null,"Invalid Information");			
					rs.close();		
				}
			}
		}
		catch(SQLException ex)
		{	ex.printStackTrace();
		}

	}
	public static void main(String a[])
	{
		new MainRecover();
	}
}

