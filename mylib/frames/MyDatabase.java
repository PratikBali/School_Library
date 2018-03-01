import java.sql.*;

class MyDatabase
{
	public static void main(String arg[])throws Exception
	{
		Connection cn=null;
		PreparedStatement prstm;
		String sql;
		
		try
		{
		  cn=DriverManager.getConnection("jdbc:mysql:///test","root","");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{	// creating database
			sql="create database if not exists library";
			prstm=cn.prepareStatement(sql);	prstm.execute();	prstm.close();
			System.out.println("\n\t Database Created Successfully");
			// using database
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			// creating login table
			String log ="create table if not exists Login(name varchar(50) primary key,password varchar(100),question varchar(100),answer varchar(100))";
			prstm=cn.prepareStatement(log);	prstm.execute();	prstm.close();
			System.out.println("\n\t Login Table Created Successfully"); 
				String val = "insert into Login values('pratik','pratik123','Favourite Bike','hyabusa');";
			prstm=cn.prepareStatement(val);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t Values inserted into table login");
			//creating books table
			String book ="create table if not exists  Books(BookID varchar(50) primary key,		Subject varchar(50),	Language varchar(50),	Title varchar(100),		Version varchar(50),	Author varchar(50),	Publisher varchar(50),	Copyright varchar(50),	Edition varchar(50),	Pages varchar(10),	NumberOfBooks varchar(10))";
			prstm=cn.prepareStatement(book);	prstm.execute();	prstm.close();
			System.out.println("\n\t Book Table Created Successfully");
			// creating Members Table
			String member ="create table if not exists Members(	Type varchar(50),	MemberID varchar(50) primary key,	Name varchar(50),	Roll varchar(50),	Class varchar(50),	Division varchar(50),	Dept varchar(50),	Phone varchar(50),	Email varchar(50))";
			prstm=cn.prepareStatement(member);	prstm.execute();	prstm.close();
			System.out.println("\n\t Members Table Created Successfully"); 
		}
		catch(SQLException e1)
		{ e1.printStackTrace(); }
				
	}
}
