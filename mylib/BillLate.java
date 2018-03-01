import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class BillLate extends JFrame implements ActionListener
{
	private JPanel northPanel = new JPanel();
	private JLabel northLabel = new JLabel("Late Return Fine");
	private JLabel lbl_time = new JLabel("t");
	private JLabel lbl_date = new JLabel("T");;
	Date date;
	DateFormat df;
	private JPanel centerPanel = new JPanel();
	
	private JPanel infoPanel = new JPanel();
	private JLabel[] infoLabel = new JLabel[7];
	private String[] infoString = {" Bill No.", " Book Id",	" Member ID", " Borrow Date", " Return Date", " Fine Amount","Total Amount"};
	private JTextField[] infoTextField = new JTextField[7];
	
	private JPanel ButtonPanel = new JPanel();
	private JButton printButton = new JButton("Print");
	private JButton backButton = new JButton("Back");
	private JButton addButton = new JButton("Add Bill");
	
	private JPanel southPanel = new JPanel();
	private JButton cancelButton = new JButton("Cancel");
	
	private Connection cn = null;
	private Statement stm = null;
	private PreparedStatement ps = null;
	private ResultSet rsBill = null;
	private ResultSet rsBorrow = null;
	
	String path;
    OutputStream file ;
    Document document;
    Paragraph p;

    PdfPTable table;
    PdfPCell c1;
	//new BillLate(bid,mid,bd,rd,ld,amnt);
	public BillLate(String bid, String mid, String fine, String amnt)
	{
		super("Late Return of Book Fine Bill");
		
		Container cp = getContentPane();
		
		date=new Date();	
		lbl_time=new JLabel(""+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
		df=new SimpleDateFormat("yyyy-MM-dd");
		lbl_date=new JLabel(""+df.format(date));

		
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northPanel.add(northLabel);
		//northPanel.add(lbl_time);
		northPanel.add(lbl_date);
		cp.add("North", northPanel);
		
		centerPanel.setLayout(new BorderLayout());
		infoPanel.setLayout(new GridLayout(7, 1, 1, 5));
		
		for (int i = 0; i < infoLabel.length; i++)
		{
			infoPanel.add(infoLabel[i] = new JLabel(infoString[i]));
			infoPanel.add(infoTextField[i] = new JTextField(13));
			infoTextField[i].setEditable(false);
		}
		centerPanel.add("Center", infoPanel);
		
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ButtonPanel.add(printButton);
		ButtonPanel.add(backButton);
		ButtonPanel.add(addButton);
			printButton.setVisible(false);
		centerPanel.add("South", ButtonPanel);
		
		centerPanel.setBorder(BorderFactory.createTitledBorder("Fine Details"));
		cp.add("Center", centerPanel);
		
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		southPanel.add(cancelButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		cp.add("South", southPanel);
		
		printButton.addActionListener(this);
		backButton.addActionListener(this);
		cancelButton.addActionListener(this);
		addButton.addActionListener(this);
		
		String bookId = bid;
		String memberId = mid;
		String finePerDay = fine;
		String amount = amnt;
		//String latedays = finedays.toString();
		
		infoTextField[1].setText(bookId);
		infoTextField[2].setText(memberId);
		infoTextField[5].setText(finePerDay);
		infoTextField[6].setText(amount);
		//infoTextField[8].setText(latedays);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//rsBill=stm.executeQuery("select * from  Bill");
			rsBorrow=stm.executeQuery("select * from  Borrow");
			
			rsBorrow.next();
			infoTextField[3].setText(rsBorrow.getString(3));
			infoTextField[4].setText(rsBorrow.getString(4));
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		getMax();
		
		setVisible(true);
		pack();
		setLocation(100,50);
	}
	
	void getMax()
	{	int bno;
		try
		{
                String sql = "select max(BillNo) from Bill";
                ResultSet rs = stm.executeQuery(sql);
                rs.next();
                 bno=rs.getInt(1);
				bno++;
				infoTextField[0].setText(""+bno);
		}
		catch(Exception e)
		{
				bno=1;
				infoTextField[0].setText(""+bno);			
		}
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource()==addButton)
			{
				int bid = Integer.parseInt(infoTextField[1].getText());
				int mid = Integer.parseInt(infoTextField[2].getText());
				String bd = infoTextField[3].getText();
				String rd = infoTextField[4].getText();
				int amnt = Integer.parseInt(infoTextField[6].getText());
				
				String sql2="insert into Bill(BookID,MemberID,BorrowDate,ReturnDate,Amount) values('"+bid+"','"+mid+"','"+bd+"','"+rd+"','"+amnt+"')";
				ps = cn.prepareStatement(sql2);
				ps.execute();
				ps.close();
				
				JOptionPane.showMessageDialog(null, "New Bill Added");
				printButton.setVisible(true);
				addButton.setVisible(false);

			}
			if(ae.getSource()==printButton)
			{
				 create_pdf();
				 write_heading();
				 create_tab_heading();
				 add_rows();
				 close_pdf();			
			}
			if(ae.getSource()==backButton)
			{
				new ReturnBooks();
				dispose();
			}
			if(ae.getSource()==cancelButton)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
     void create_pdf()
    {
        try
        {
            path="E:\\Bill Report of Member.pdf";
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

        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
  

		
        table.setHeaderRows(2);
    }

    void add_rows()
    {  
       try
        {
		 
           
                    c1 = new PdfPCell(new Phrase(infoTextField[0].getText()));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(infoTextField[1].getText()));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(infoTextField[2].getText()));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(infoTextField[3].getText()));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    c1 = new PdfPCell(new Phrase(infoTextField[4].getText()));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
					c1 = new PdfPCell(new Phrase(infoTextField[6].getText()));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

					
			 
           

            // Coloumn Spanning
            PdfPCell cell = new PdfPCell(new Paragraph(" Thank You "));
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
	/*public static void main(String a[])
	{
		new BillLate();
	}*/

}