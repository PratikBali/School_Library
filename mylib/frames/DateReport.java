import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import javax.swing.table.*;
import java.awt.Font;

class DateReport extends JFrame implements ActionListener
{
 JLabel fD,tD,l1;
 DateButton fdate,tdate;
 JButton show_reports,print,cancel,cancel1;
 JPanel p1,p2; 
 Container cc;
 String fromd,tod;
 Font big,small;
 
 String fd,td;

 String path;
    OutputStream file ;
    Document document;
    Paragraph p;

    PdfPTable table;
    PdfPCell c1;
 
 Connection cn=null;
 Statement stm;
 ResultSet rs;
 PreparedStatement prstm;
 String sql;

  JTable tab;
 DefaultTableModel mdl;

 Object colHead[] = {"Bill ID","Book ID", "Member ID", "Borrow Date", "Return Date","Amount"};
 Object data[][]={{"","Total Transaction = ",new Integer(0)}};
 JScrollPane jsp;  
 int cnt=0,row=0,i=0;

 DateReport()
 {
  super("Date to date Reports");
  setSize(650,650);
  setLocation(300,10);
  setLayout(null);
  
  //big = new Font("TIMES_ROMAN", 22,Font.BOLD);
  //big = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
 // small = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
         
  fD = new JLabel("Enter from date : "); 
  tD = new JLabel(" to date : "); 
  l1 = new JLabel("Date to Date Reports ");
 
  cc=getContentPane();
  cc.setLayout(null); 
 
  fdate = new DateButton();
  add(fdate);

  tdate = new DateButton();
  add(tdate);
  
  p1 = new JPanel(); 
  p1.setLayout(null);
  //add(p1);
  p1.setBounds(50,140,550,500);  
  p1.setVisible(false); 
  //p1.setBackground(Color.RED);
  p1.setBorder(BorderFactory.createTitledBorder("Library Management System"));
   
  p2 = new JPanel(); 
  p2.setLayout(null);
  add(p2);
  p2.setBounds(50,650,550,500);  
  p2.setVisible(false); 
 
  print = new JButton("Print ");
  show_reports = new JButton("Show Reports");
  cancel = new JButton("Cancel");
  cancel1 = new JButton("Reset");
  
  add(fD);			add(tD);			
  add(show_reports);			add(cancel);
  
  Font f=new Font("Georgia",Font.BOLD,20);
  l1.setFont(f);
  
  p1.add(l1);
  p2.add(print);			p2.add(cancel1);
  
  cc.add(p1);
  
  
  add(fdate);    add(tdate);
  
  
  fD.setBounds(50,40,100,30);										tD.setBounds(330,40,80,30);
  fdate.setBounds(160,40,150,30);									tdate.setBounds(400,40,150,30);
  show_reports.setBounds(120,100,150,30);						cancel.setBounds(320,100,150,30);
  
  show_reports.setMnemonic('s');
  cancel.setMnemonic('c');
  cancel1.setMnemonic('r');
  print.setMnemonic('p');
  
  l1.setBounds(160,20,400,30);
  
  cancel1.setBounds(125,10,100,30);								print.setBounds(350,10,100,30);
  
  try
   {
     cn = DriverManager.getConnection("jdbc:mysql:///library","root","");
     stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
   }
    catch(Exception e)
   {
     e.printStackTrace();
   }
  
  show_reports.addActionListener(this);
  cancel.addActionListener(this);
  cancel1.addActionListener(this);
  print.addActionListener(this);
  
  setVisible(true);
 }
 
    public void actionPerformed(ActionEvent e)
	{
	  try
	  {
		if(e.getSource()==show_reports)
		{   
		  p1.setVisible(true);
		  p2.setVisible(true);			 
		  updateTable();	 
	   }	    
	   
  if(e.getSource()==cancel)
  {
    dispose();
	//new Mainpage();
  }
   if(e.getSource()==cancel1)
  {
    p1.setVisible(false);
    p2.setVisible(false);	 
  }
   
   if(e.getSource()==print)
  {
     create_pdf();
     write_heading();
     create_tab_heading();
     add_rows();
     close_pdf();
   }  
 }
   catch(Exception ex)
  {ex.printStackTrace();
   }
 }
  
 void updateTable()
 {
   try
    {
	row=0;
	fd=fdate.getText();
	td=tdate.getText();
      //rs = stm.executeQuery("select * from Transaction where BorrowDate >= '2014-01-08' and BorrowDate <= '2016-03-08'");
	  rs = stm.executeQuery("select * from Bill Where BorrowDate >='"+fd+"' and BorrowDate <='"+td+"'");
      System.out.println(fd);
	  while(rs.next())
     {
      row++;
     }
	 data = new String[row][6];
	 rs.first();
  	 for( i=0;i<row;i++)
	 {
      	data[i][0]=rs.getString(1);
	    data[i][1]=rs.getString(2);
    	data[i][2]=rs.getString(3);
	 	data[i][3]=rs.getString(4);
	 	data[i][4]=rs.getString(5);
	 	data[i][5]=rs.getString(6);
	 	//data[i][4]=rs.getString(5);
	    
		rs.next();
	 }
	   rs.close();
	}
	   catch(Exception exp)
        {
            exp.printStackTrace();
        } 
		
		 tab = new JTable(data,colHead);
         p1.add(tab);
		 tab.setEnabled(false);	 
		jsp = new JScrollPane(tab);
		p1.add(jsp);
		jsp.setBounds(18,65,515,415);        

 } 
 
     void create_pdf()
    {
        try
        {
            path="E:\\Bill Report.pdf";
            file = new FileOutputStream(new File(path));

            document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            //document.newPage();   // for new page
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    void write_heading()
    {
        try
        {
            //Write in a PDF
			//p = new Paragraph("Motor Insurance Policy Date to Date Reports",big);
			p = new Paragraph("Library Management Bill Reports");
			p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph(" "));

            p = new Paragraph("Date = " + new java.util.Date());
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            document.add(new Paragraph(" "));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    void create_tab_heading()
    {
       
		float[] colsWidth = {50,50,50,50,50,50};
		table = new PdfPTable(colsWidth);
		table.setWidthPercentage(100);
  
        c1 = new PdfPCell(new Phrase("Bill ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        //c1.setFixedHeight(55);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Book ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        //c1.setFixedHeight(55);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Member ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Borrow Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Return Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

  

		
        table.setHeaderRows(2);
    }

    void add_rows()
    {  
       try
        {
		 cnt = 0;
		
      rs = stm.executeQuery("select * from Bill Where BorrowDate >='"+fd+"' and BorrowDate<='"+td+"'");
	  
	  
      //            for(int i=0;i<10;i++)
            {
                rs.beforeFirst();
                while(rs.next())
                {
          
                    c1 = new PdfPCell(new Phrase(rs.getString(1)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(rs.getString(2)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(rs.getString(3)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(rs.getString(4)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(rs.getString(5)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
					c1 = new PdfPCell(new Phrase(rs.getString(6)));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

					


					cnt = cnt+ rs.getInt(6);
					}
            }
           rs.close();

            // Coloumn Spanning
            PdfPCell cell = new PdfPCell(new Paragraph("Total record = "+cnt));
            cell.setColspan(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);
            document.add(new Paragraph(" "));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    void close_pdf()
    {
        try
        {
            document.close();
            file.close();

			//To open the pdf file in linux & windows
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new java.io.File(path));
        }
        catch (Exception e)
        {  e.printStackTrace();
        }
    }
    
/* public static void main(String args[])
 {
  new DateReport();
 }*/
}