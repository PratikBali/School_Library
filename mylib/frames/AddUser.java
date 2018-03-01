/*create table login
(
	name varchar(50) primary key,
	password varchar(100),
	question varchar(100),
	answer varchar(100)
);
insert into login values('pratik','pratik123','Favourite Bike','hyabusa');
select * from login;*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class AddUser extends JFrame implements ActionListener
{
	JTextField txt_id,txt_ans;
	JLabel l_id,l_pwd,l_que,l_ans,l_pf;
	JButton b_save,b_back,b_ext;
	JComboBox cmb;
	JPasswordField pwd,pf;

	String id,pd,q,a;
	Statement stm;
	ResultSet rs;
	Connection cn=null;
	PreparedStatement prstm;
	String sql;
	
	AddUser()
	{
		super("Add New user ");
		//super(" new user ",false,true,false,true);
		setSize(350,320);
		setLocation(450,150);		       setLayout(null);

		l_id=new JLabel("New user name");	   txt_id=new JTextField();
		l_pwd=new JLabel("New password");        pwd=new JPasswordField();
		l_pf=new JLabel("Confirm password");        pf=new JPasswordField();
		l_que=new JLabel("Question");	         cmb=new JComboBox();
		l_ans=new JLabel("Answer");	           txt_ans=new JTextField();

		b_save=new JButton("Save");        b_back=new JButton("Back");    
		b_ext=new JButton("Exit");	
		
		cmb.addItem("Select Question");
		cmb.addItem("First School");
		cmb.addItem("First College");
		cmb.addItem("Fav Bike");
		cmb.addItem("Fav Teacher");
		cmb.addItem("Fav Food");	
		cmb.addItem("Best Friend");
		cmb.addItem("Fav Player");
		cmb.addItem("Fav Game");

		add(l_id);	add(l_pwd);	add(l_que);	add(l_ans);add(l_pf);add(pf);
		add(txt_id);	add(pwd);	add(cmb);	add(txt_ans);
		add(b_save);	add(b_ext);	add(b_back);

		l_id.setBounds(50,50,150,20);	txt_id.setBounds(160,50,120,20);
		l_pwd.setBounds(50,80,150,20);     pwd.setBounds(160,80,120,20);
		l_pf.setBounds(50,110,150,20);	   pf.setBounds(160,110,120,20);
		l_que.setBounds(50,140,100,20);  cmb.setBounds(160,140,120,20);
		l_ans.setBounds(50,170,70,20);	 txt_ans.setBounds(160,170,120,20);
		b_save.setBounds(50,200,70,20);	 b_back.setBounds(130,200,70,20);
		b_ext.setBounds(210,200,70,20);
		 //b_ext.setBounds(210,170,70,20);
		
		b_save.addActionListener(this);    b_ext.addActionListener(this);	b_back.addActionListener(this);

		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stm.executeQuery("select * from  login");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		try	
		{
			if(e.getSource()==b_save)
			{
				if(txt_id.getText().length()==0 || pwd.getText().length()==0 ||	cmb.getSelectedIndex()==0|| txt_ans.getText().length()==0 )
				{
					JOptionPane.showMessageDialog(null,"All field necessory!!!!","Error",JOptionPane.ERROR_MESSAGE); 
					txt_id.requestFocus();
					if(txt_id.getText().length()==0)       txt_id.requestFocus();	
					else if(pwd.getText().length()==0)	pwd.requestFocus();
					else if(cmb.getSelectedIndex()==0)	cmb.requestFocus();
					else if(txt_ans.getText().length()==0 )	txt_ans.requestFocus();
				}
				else if(pwd.getText().equals(pf.getText()))
				{
					id=txt_id.getText();
					pd=pwd.getText();
					q=cmb.getSelectedItem().toString();
					a=txt_ans.getText();
						
					sql="insert into login values('"+id+"','"+pd+"','"+q+"','"+a+"')";
					prstm=cn.prepareStatement(sql);
					prstm.execute();	prstm.close();
					JOptionPane.showMessageDialog(null,"New User Added successfully");
					txt_id.requestFocus();
					txt_id.setText(null);
					pwd.setText(null);
					pf.setText(null);
					txt_ans.setText(null);
					cmb.setSelectedIndex(0);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Password does not match","Error",JOptionPane.ERROR_MESSAGE); 
					pwd.requestFocus();
					pwd.setText(null);
					pf.setText(null);

				}
			}
			
			if(e.getSource()==b_ext)
			{
				System.exit(0);
			}
			
			/*if(e.getSource()==b_back)
			{
				new loginMenu();
				dispose();
			}*/
	
		}
		
		catch(Exception e1)
		{
			System.out.println(e1);
		}
	}
	public static void main(String a[])
	{new AddUser();
	}
}
