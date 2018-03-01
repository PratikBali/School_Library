import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.sql.*;

public class ListMembers extends JInternalFrame
{
	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JLabel label = new JLabel("THE LIST FOR THE MEMBER");
	private JButton printButton;
	private JTable table;
	private TableColumn column = null;
	private JScrollPane scrollPane;
	private ResultSetTableModel tableModel;
	 
	private static final String DEFAULT_QUERY = "SELECT MemberID, Name, Roll, Class, Dept, Phone FROM Members";

	public ListMembers()
	{
		super("Members", false, true, false, true);
		setFrameIcon(new ImageIcon("images/List16.gif"));
		Container cp = getContentPane();

		try
		{
			tableModel = new ResultSetTableModel(DEFAULT_QUERY);
			try
			{
				tableModel.setQuery(DEFAULT_QUERY);
			}
			catch (SQLException sqlException) {	}
		}
		catch (ClassNotFoundException classNotFound) {
		}
		catch (SQLException sqlException) {	}

		//for setting the table with the information
		table = new JTable(tableModel);
		//for setting the size for the table
		table.setPreferredScrollableViewportSize(new Dimension(700, 200));
		//for setting the font
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for setting the scrollpane to the table
		scrollPane = new JScrollPane(table);

		//for setting the size for the table columns
		for (int i = 0; i < 6; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) //MemberID
				column.setPreferredWidth(30);
			if (i == 1) //name
				column.setPreferredWidth(150);
			if (i == 2) //roll
				column.setPreferredWidth(20);
			if (i == 3) //class
				column.setPreferredWidth(20);
			if (i == 4) //dept
				column.setPreferredWidth(50);
			if (i == 5) //phone
				column.setPreferredWidth(40);
		}
		//for setting the font to the label
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		//for setting the layout to the panel
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//for adding the label to the panel
		northPanel.add(label);
		//for adding the panel to the container
		cp.add("North", northPanel);

		//for setting the layout to the panel
		centerPanel.setLayout(new BorderLayout());
		//for creating an image for the button
		ImageIcon printIcon = new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif"));
		//for adding the button to the panel
		printButton = new JButton("print the members", printIcon);
		//for setting the tip text
		printButton.setToolTipText("Print");
		//for setting the font to the button
		printButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for adding the button to the panel
		centerPanel.add(printButton, BorderLayout.NORTH);
		//for adding the scrollpane to the panel
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		//for setting the border to the panel
		centerPanel.setBorder(BorderFactory.createTitledBorder("Members:"));
		//for adding the panel to the container
		cp.add("Center", centerPanel);

		//for adding the actionListener to the button
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Thread runner = new Thread() {
					public void run() {
						try {
							PrinterJob prnJob = PrinterJob.getPrinterJob();
							prnJob.setPrintable(new PrintingMembers(DEFAULT_QUERY));
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
	
	/**public static void main(String a[])
	{new ListMembers();
	}**/	
}