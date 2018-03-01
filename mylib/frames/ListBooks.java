//import the packages for using the classes in them into the program

import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
//import java.awt.print.PrinterJob;

/**
 *A public class
 */
public class ListBooks extends JFrame
{
//for creating the North Panel
	private JPanel northPanel = new JPanel();
	//for creating the Center Panel
	private JPanel centerPanel = new JPanel();
	//for creating the label
	private JLabel northLabel = new JLabel("THE LIST FOR THE BOOKS");
	//for creating the button
	private JButton printButton;
	//for creating the table
	private JTable table;
	//for creating the TableColumn
	private TableColumn column = null;
	//for creating the JScrollPane
	private JScrollPane scrollPane;

	//for creating an object for the ResultSetTableModel class
	private ResultSetTableModel tableModel;


	//private static final String JDBC_DRIVER = "jdbc.mysql.JdbcOdbcDriver";
	//private static final String DATABASE_URL = "jdbc:mysql:Library";
	private static final String DEFAULT_QUERY = "SELECT BookID, Subject, Language, Title, Version, Author, Publisher, Copyright, Edition, Pages,	NumberOfBooks FROM Books";

	//constructor of listBooks
	public ListBooks()
	{
		//for setting the title for the internal frame
		super("Books");
		//for setting the icon
		//setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/List16.gif")));
		//setLocale(new java.util.Locale("ar", "SA", ""));

		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		//for bassing the required information to the ResultSetTableModel object
		try
		{
			tableModel = new ResultSetTableModel(DEFAULT_QUERY);
			//tableModel = new ResultSetTableModel("jdbc:mysql:///library","root","");
			//for setting the Query
			try {
				tableModel.setQuery(DEFAULT_QUERY);
			}
			catch (SQLException sqlException)
			{
			}
		}
		catch (ClassNotFoundException classNotFound)
		{
			System.out.println(classNotFound.toString());
		}
		catch (SQLException sqlException)
		{
			System.out.println(sqlException.toString());
		}
		//for setting the table with the information
		table = new JTable(tableModel);
		//for setting the size for the table
		table.setPreferredScrollableViewportSize(new Dimension(990, 200));
		//for setting the font
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for setting the scrollpane to the table
		scrollPane = new JScrollPane(table);

		//for setting the size for the table columns
		for (int i = 0; i < 11; i++)
		{
			column = table.getColumnModel().getColumn(i);
			//BookID, Subject, Language, Title, Version, Author, Publisher, Copyright, Edition, Pages,	NumberOfBooks
			if (i == 0) //BookID
				column.setPreferredWidth(20);
			if (i == 1) //Subject 
				column.setPreferredWidth(100);
			if (i == 2) // Language
				column.setPreferredWidth(150);
			if (i == 3) // Title
				column.setPreferredWidth(50);
			if (i == 4) // Version
				column.setPreferredWidth(70);
			if (i == 5) // Author
				column.setPreferredWidth(40);
			if (i == 6) // Publisher
				column.setPreferredWidth(40);
			if (i == 7) // Copyright
				column.setPreferredWidth(40);
			if (i == 8) // Edition
				column.setPreferredWidth(80);
			if (i == 9) // Pages
				column.setPreferredWidth(70);
			if (i == 10) // No.of books
				column.setPreferredWidth(30);
			
		}
		//for setting the font to the label
		northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for setting the layout to the panel
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for adding the label to the panel
		northPanel.add(northLabel);
		//for adding the panel to the container
		cp.add("North", northPanel);

		//for setting the layout to the panel
		centerPanel.setLayout(new BorderLayout());
		//for creating an image for the button
		ImageIcon printIcon = new ImageIcon("images/Print16.gif");
		//for adding the button to the panel
		printButton = new JButton("print the books", printIcon);
		//for setting the tip text
		printButton.setToolTipText("Print");
		//for setting the font to the button
		printButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for adding the button to the panel
		centerPanel.add(printButton, BorderLayout.NORTH);
		//for adding the scrollpane to the panel
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		//for setting the border to the panel
		centerPanel.setBorder(BorderFactory.createTitledBorder("Books:"));
		//for adding the panel to the container
		cp.add("Center", centerPanel);

		//for adding the actionListener to the button
		printButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Thread runner = new Thread()
				{
					public void run()
					{
						try
						{
							PrinterJob prnJob = PrinterJob.getPrinterJob();
							prnJob.setPrintable(new PrintingBooks(DEFAULT_QUERY));
							if (!prnJob.printDialog())
								return;
							setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							prnJob.print();
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
						catch (PrinterException ex) {
							System.out.println("Printing error: " + ex.toString());
						}
					}
				};
				runner.start();
			}
		});
		//for setting the visible to true
		setVisible(true);
		//to show the frame
		pack();
	}
	public static void main (String a[])
	{
		new ListBooks();
	}
}