import java.sql.*;

class auto
{
	public void createDatabase()
	{
		Connection cn=null;
		PreparedStatement prstm;
		String sql;
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///Information_Schema","root","");
		
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
				String trans ="create table Transaction(SrNo int not null AUTO_INCREMENT primary key,BookID varchar(50) ,MemberID varchar(50),BorrowDate date,ReturnDate date,constraint foreign key(BookID) references Books(BookID),constraint foreign key(MemberID)references Members(MemberID))";
				prstm=cn.prepareStatement(trans);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t transaction Table Created "); 
				
				// creating borrow Table
				String borrow ="create table Borrow(bookID int,	memberID int,BorrowDate date,ReturnDate date)";
				prstm=cn.prepareStatement(borrow);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t borrow Table Created "); 
				
				// creating bill Table
				String bill ="create table Bill(BillNo int auto_increment primary key,	BookID varchar(50) ,	MemberID varchar(50),	BorrowDate date,	ReturnDate date,	Amount varchar(50),	constraint foreign key(BookID) references Books(BookID),	constraint foreign key(MemberID) references Members(MemberID))";
				prstm=cn.prepareStatement(bill);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t borrow Table Created "); 
				
				//inserting record into login table
				String insertLogin ="insert into Login values('marathi','manus','Fav Bike','hyabusa')";
				prstm=cn.prepareStatement(insertLogin);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 1 Record inserted into login table"); 

				//inserting record into book table
				String insertBook ="insert into Books values ('1','aaa','aaa','aaa','52.2','aaa','aaa','30','2015','99','3','borrowed')";
				prstm=cn.prepareStatement(insertBook);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 2nd Record inserted into Book table"); 
				
				//inserting record into book table
				String insertBook2 ="insert into Books values ('2','bbb','bbb','bbb','52.2','bbb','bbb','20','2015','99','3','available')";
				prstm=cn.prepareStatement(insertBook2);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 1 Record inserted into Book table"); 
				
				//inserting record into book table
				String insertBook3 ="insert into Books values ('3','ccc','ccc','ccc','52.2','ccc','ccc','30','2015','99','3','borrowed')";
				prstm=cn.prepareStatement(insertBook3);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 3rd Record inserted into Book table"); 
				
				//inserting record into book table
				String insertBook4 ="insert into Books values ('4','ddd','ddd','ddd','52.2','ddd','ddd','30','2015','99','1','available')";
				prstm=cn.prepareStatement(insertBook4);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 4th Record inserted into Book table"); 
				
				//inserting record into member table
				String insertMember ="insert into Members values ('teacher','11','Member Teacher','','','','Comp','9011989737','cnpratik@gmail.com')";
				prstm=cn.prepareStatement(insertMember);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 1st Record inserted into member table"); 	
				
				//inserting record into member table
				String insertMember2="insert into Members values('student','12','Member student','12','ty','a','','9011989737','cnpratik@gmail.com')";
				prstm=cn.prepareStatement(insertMember2);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 2nd Record inserted into member table"); 
	
				//inserting record into member table
				String insertMember3="insert into Members values ('teacher','13','prince','','','','Comp','9011989737','cnpratik@gmail.com')";
				prstm=cn.prepareStatement(insertMember3);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 3rd Record inserted into member table"); 

				//inserting record into member table
				String insertMember4="insert into Members values ('student','14','prince','4','ty','a','','9011989737','cnpratik@gmail.com');";
				prstm=cn.prepareStatement(insertMember4);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 4th Record inserted into member table"); 
				
				//inserting record into transaction table
				String insertTrans ="insert into Transaction values ('1','1','11','2015-01-01','2015-01-10')";
				prstm=cn.prepareStatement(insertTrans);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 1 Record inserted into transaction table"); 
				
				//inserting record into transaction table
				String insertTrans2 ="insert into Transaction(BookID,MemberID,BorrowDate,ReturnDate) values ('3','13','2015-03-03','2014-03-10')";
				prstm=cn.prepareStatement(insertTrans2);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 2nd Record inserted into transaction table"); 

				//inserting record into borrow table
				String insertBorrow ="insert into borrow values(11,111,'2014-02-12','2014-03-10');";
				prstm=cn.prepareStatement(insertBorrow);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 1 Record inserted into Borrow table"); 
				
				//inserting record into bill table
				String insertBill ="insert into Bill(BookID,MemberID,BorrowDate,ReturnDate,Amount) values (1,11,'2014-02-12','2014-03-10',20)";
				prstm=cn.prepareStatement(insertBill);	prstm.execute();	prstm.close();
				System.out.println("\n\t\t\t 1 Record inserted into bill table\n"); 	
		}
		catch(SQLException e1)
		{ e1.printStackTrace(); }
		}
		
		public static void main(String arg[])throws Exception
		{		
			auto create = new auto();
			create.createDatabase();
		}
}
