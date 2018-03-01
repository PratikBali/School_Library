create database library;
use library;

create table LoginInfo
(
	Name varchar(50),
	Password varchar(100),
	LoginDate date,
	LoginTime time
);
select * from LoginInfo;

create table Login
(
	name varchar(50) primary key,
	password varchar(100),
	question varchar(100),
	answer varchar(100)
);
insert into Login values('pratik','pratik123','Fav Bike','hyabusa');
insert into Login values('newton','lokmanya','Fav Teacher','mankar');
insert into Login values('marathi','manus','Fav Game','cricket');
select * from Login;

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
	NumberOfBooks varchar(10),
	Status varchar(10)
);

insert into Books (BookID,Subject,Language,Title,Version,Author,Publisher,Copyright,Edition,Pages,NumberOfBooks )
	values ('11','study','mar','yuvak','3.2','pratik','max','10','2014','99','3');
insert into Books values ('12','dfg','dfgd','hd','52.2','olgu','kuj','20','2015','99','3','?');
insert into Books values ('13','dk','dfgd','hd','52.2','olgu','kuj','30','2015','99','3','borrowed');
insert into Books values ('14','dk','dfgd','hd','52.2','olgu','kuj','30','2015','99','1','borrowed');
select * from Books;

create table Members
(
	Type varchar(50),
	MemberID varchar(50) primary key,
	Name varchar(50),
	Roll varchar(50),
	Class varchar(50),
	Division varchar(50),
	Dept varchar(50),
	Phone varchar(50),
	Email varchar(50)
);
insert into Members values ('teacher','1','prince','','','','Comp','9011989737','cnpratik@gmail.com');
insert into Members values ('student','2','prince','3','ty','a','','9011989737','cnpratik@gmail.com');
select * from Members;

create table Transaction
(
	SrNo varchar(50) primary key,
	BookID varchar(50) ,
	MemberID varchar(50),
	BorrowDate date,
	ReturnDate date,
	constraint foreign key(BookID) references Books(BookID),
	constraint foreign key(MemberID) references Members(MemberID)
);
insert into Transaction values ('1','22','222','2014-02-12','2014-03-10');
select * from Transaction;

create table Bill
(BillNo int not null auto_increment primary key,
BookID varchar(50) ,
MemberID varchar(50),	
BorrowDate date,	
ReturnDate date,	
LateDays varchar(50),
Fine varchar(50),	
Total varchar(50),	
constraint foreign key(BookID) references Books(BookID),	
constraint foreign key(MemberID) references Members(MemberID));

insert into Bill values (1,'1','11','2014-02-12','2014-03-10','10','2','20');
select * from Bill;

create table Borrow
(
	bookID int,
	memberID int,
	BorrowDate date,
	ReturnDate date
);
insert into borrow values(11,111,'2014-02-12','2014-03-10');

use library;
select * from Books;
select * from Members;
select * from Borrow;
select * from Transaction;
