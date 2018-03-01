import java.sql.*;

class auto
{
	public static void main(String arg[])throws Exception
	{
		Connection cn=null;
		PreparedStatement prstm;
		String sql;
		
		try
		{
		  cn=DriverManager.getConnection("jdbc:mysql:///Information_Schema","root","");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			System.out.println("\n Accesssing Mysql ");
			System.out.println("\n Accesssing whole Database ");
			//dropping existing database
			String dropDB="drop database if exists library";
			prstm=cn.prepareStatement(dropDB);	prstm.execute();	prstm.close();
			System.out.println("\n\t Library Database dropped Successfully");
		
			// creating database
			sql="create database if not exists library";
			prstm=cn.prepareStatement(sql);	prstm.execute();	prstm.close();
			System.out.println("\n\t Library Database Created Successfully");
			
			// using database
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			System.out.println("\n\t Database Changed");
			
			// creating login table
			String log ="create table if not exists Login(name varchar(50) primary key,password varchar(100),question varchar(100),answer varchar(100))";
			prstm=cn.prepareStatement(log);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t Login Table Created"); 
			
			// creating login info table
			String log2 ="create table LoginInfo(Name varchar(50),Password varchar(100),LoginDate date,LoginTime time)";
			prstm=cn.prepareStatement(log2);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t Login Info Table Created ");
			
			//creating books table
			String book ="create table if not exists  Books(BookID varchar(50) primary key,		Subject varchar(50),	Language varchar(50),	Title varchar(100),		Version varchar(50),	Author varchar(50),	Publisher varchar(50),	Copyright varchar(50),	Edition varchar(50),	Pages varchar(10),	NumberOfBooks varchar(10),	Status varchar(10))";
			prstm=cn.prepareStatement(book);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t Book Table Created ");
			
			// creating Members Table
			String member ="create table if not exists Members(	Type varchar(50),	MemberID varchar(50) primary key,	Name varchar(50),	Roll varchar(50),	Class varchar(50),	Division varchar(50),	Dept varchar(50),	Phone varchar(50),	Email varchar(50))";
			prstm=cn.prepareStatement(member);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t Members Table Created "); 
			
			// creating transaction Table
			String trans ="create table if not exists Transaction(	SrNo varchar(50) primary key,BookID varchar(50) ,MemberID varchar(50),BorrowDate date,ReturnDate date,constraint foreign key(BookID) references Books(BookID),constraint foreign key(MemberID)references Members(MemberID))";
			prstm=cn.prepareStatement(trans);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t transaction Table Created "); 
			
			//inserting record into login table
			String insertLogin ="insert into Login values('marathi','manus','Fav Bike','hyabusa')";
			prstm=cn.prepareStatement(insertLogin);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t\t 1 Record inserted into login table"); 

			//inserting record into book table
			String insertBook ="insert into Books values ('11','dk','dfgd','hd','52.2','olgu','kuj','30','2015','99','3','borrowed')";
			prstm=cn.prepareStatement(insertBook);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t\t 1 Record inserted into Book table"); 
			
			//inserting record into member table
			String insertMember ="insert into Members values ('teacher','111','Teacher','','','','Comp','9011989737','cnpratik@gmail.com')";
			prstm=cn.prepareStatement(insertMember);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t\t 1st Record inserted into member table"); 
			
			
			//inserting record into member table
			String insertMember2="insert into Members values ('student','12','Member student','3','ty','a','','9011989737','cnpratik@gmail.com')";
			prstm=cn.prepareStatement(insertMember2);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t\t 2nd Record inserted into member table"); 
			
			//inserting record into transaction table
			String insertTrans ="insert into Transaction values ('1','11','111','2015-01-01','2015-01-10')";
			prstm=cn.prepareStatement(insertTrans);	prstm.execute();	prstm.close();
			System.out.println("\n\t\t\t 1 Record inserted into transaction table\n"); 

			
		}
		catch(SQLException e1)
		{ e1.printStackTrace(); }
				
	}
}
