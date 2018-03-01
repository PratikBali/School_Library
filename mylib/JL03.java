import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JL03 extends JFrame implements ActionListener
{
    private JPanel libPanel = new JPanel();
    private JDesktopPane desktop = new JDesktopPane();

    private Menubar menu;
    private Toolbar toolbar;
    private StatusBar statusbar = new StatusBar();

    private AddBooks addBooks;
    private ListBooks listBooks;
	private ListAvailableBooks listAvailable;
    private ListBorrowedBooks listBorrowed;
	private EditBooks editBooks;
    private RemoveBooks removeBooks;
	
    private AddMembers addMembers;
    private ListMembers listMembers;
	private EditMembers editMembers;
    private RemoveMembers removeMembers;
	
	private SearchBooksAndMembers search;
	private BorrowBooks borrowBooks;
    private ReturnBooks returnBooks;
	private AddUser addUser;
	
	private DateReport dateTodate;
	//private auto sql;
	
    public JL03()
	{
        super("Library Management System");
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(ClassLoader.getSystemResource("images/Host16.gif"));
        setIconImage(image);

		/*sql = new auto();
		sql.createDatabase();*/
        menu = new Menubar();
        toolbar = new Toolbar();
        setJMenuBar(menu);
		
        //menu.printBook.addActionListener(this);
        menu.exit.addActionListener(this);
        menu.addBook.addActionListener(this);
        menu.listBook.addActionListener(this);
        menu.listAvailableBook.addActionListener(this);
        menu.listBorrowedBook.addActionListener(this);
        menu.editBook.addActionListener(this);
        menu.removeBook.addActionListener(this);
        menu.addMember.addActionListener(this);
        menu.listMember.addActionListener(this);
        menu.editMember.addActionListener(this);
        menu.removeMember.addActionListener(this);
        menu.searchBooksAndMembers.addActionListener(this);
        menu.borrowBook.addActionListener(this);
        menu.returnBook.addActionListener(this);
        menu.listissuedbooks.addActionListener(this);
        menu.help.addActionListener(this);
        menu.about.addActionListener(this);
        menu.dateReport.addActionListener(this);
        menu.searchByBook.addActionListener(this);

        Container cp = getContentPane();
        desktop.setBackground(Color.RED);
        cp.add("Center", desktop);
		
        libPanel.setLayout(new BorderLayout());
        libPanel.add("Center", toolbar);
        cp.add("North", libPanel);
		
        cp.add("South", statusbar);

        for (int i = 0; i < toolbar.imageName24.length; i++)
		{
            toolbar.button[i].addActionListener(this);
        }

		addWindowListener(new WindowAdapter()
		{
            public void windowClosing(WindowEvent e) 
			{
                System.exit(0);
            }
        });
        show();
    }

    public void actionPerformed(ActionEvent ae)
	{	//add books
        if (ae.getSource() == menu.addBook || ae.getSource() == toolbar.button[0])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    addBooks = new AddBooks();
                    desktop.add(addBooks);
                    try
					{
                        addBooks.setSelected(true);
                    } catch (Exception e) {}
                }
            };
            runner.start();
        }
		// list all books
		if (ae.getSource() == menu.listBook || ae.getSource() == toolbar.button[1])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    listBooks = new ListBooks();
                    desktop.add(listBooks);
                    try
					{
                        listBooks.setSelected(true);
                    } catch (Exception e) {}
                }
            };
            runner.start();
        }
		// list available books
		if (ae.getSource() == menu.listAvailableBook || ae.getSource() == toolbar.button[2])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    listAvailable = new ListAvailableBooks();
                    desktop.add(listAvailable);
                    try {
                        listAvailable.setSelected(true);
                    } catch (Exception e) {}
                }
            };
            runner.start();
        }
		// list borrowed books
		 if (ae.getSource() == menu.listBorrowedBook || ae.getSource() == toolbar.button[3])
		 {
            Thread runner = new Thread()
			{
                public void run()
				{
                    listBorrowed = new ListBorrowedBooks();
                    desktop.add(listBorrowed);
                    try
					{
                        listBorrowed.setSelected(true);
                    } catch (Exception e){}
                }
            };
            runner.start();
        }
		// edit books
		if (ae.getSource() == menu.editBook || ae.getSource() == toolbar.button[4])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    editBooks = new EditBooks();
                    desktop.add(editBooks);
                    try
					{
                        editBooks.setSelected(true);
                    } catch (Exception e)	{       }
                }
            };
            runner.start();
        }
		// remove book
		 if (ae.getSource() == menu.removeBook || ae.getSource() == toolbar.button[5])
		 {
            Thread runner = new Thread()
			{
                public void run()
				{
                    removeBooks = new RemoveBooks();
                    desktop.add(removeBooks);
                    try 
					{
                        removeBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {     }
                }
            };
            runner.start();
        }
		// add member
        if (ae.getSource() == menu.addMember || ae.getSource() == toolbar.button[6])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    addMembers = new AddMembers();
                    desktop.add(addMembers);
                    try
					{
                        addMembers.setSelected(true);
                    } catch (Exception e) {}
                }
            };
            runner.start();
        }
		//list member
       if (ae.getSource() == menu.listMember || ae.getSource() == toolbar.button[7])
	   {
            Thread runner = new Thread()
			{
                public void run()
				{
                    listMembers = new ListMembers();
                    desktop.add(listMembers);
                    try
					{
                        listMembers.setSelected(true);
                    } catch (Exception e){}
                }
            };
            runner.start();
        }
		// edit member
        if (ae.getSource() == menu.editMember || ae.getSource() == toolbar.button[8])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    editMembers = new EditMembers();
                    desktop.add(editMembers);
                    try
					{
                        editMembers.setSelected(true);
                    } catch (Exception e)	{ }
                }
            };
            runner.start();
        }
		//remove member
        if (ae.getSource() == menu.removeMember || ae.getSource() == toolbar.button[9]) {
            Thread runner = new Thread() {

                public void run() {
                    removeMembers = new RemoveMembers();
                    desktop.add(removeMembers);
                    try {
                        removeMembers.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }	
		//search
		if (ae.getSource() == menu.searchBooksAndMembers || ae.getSource() == toolbar.button[10])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    search = new SearchBooksAndMembers();
                    desktop.add(search);
                    try
					{
                        search.setSelected(true);
                    } catch (Exception e) {             }
                }
            };
            runner.start();
        }
		
		if (ae.getSource() == menu.searchByBook || ae.getSource() == toolbar.button[11])
		{
            new SearchByBook();
        }
		// borrow book
		if (ae.getSource() == menu.borrowBook || ae.getSource() == toolbar.button[12])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    borrowBooks = new BorrowBooks();
                    desktop.add(borrowBooks);
                    try
					{
                        borrowBooks.setSelected(true);
                    } catch (Exception e) {                    }
                }
            };
            runner.start();
        }
		//return book
		if (ae.getSource() == menu.returnBook || ae.getSource() == toolbar.button[13])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    returnBooks = new ReturnBooks();
                    desktop.add(returnBooks);
                    try
					{
                        returnBooks.setSelected(true);
                    } catch (Exception e)
					{
                    }
                }
            };
            runner.start();
        }
		//help
		if (ae.getSource() == menu.help || ae.getSource() == toolbar.button[14])
		{}
		//about
		if (ae.getSource() == menu.about || ae.getSource() == toolbar.button[15])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    JOptionPane.showMessageDialog(null, new About(), "About JLibrary", JOptionPane.PLAIN_MESSAGE);
                }
            };
            runner.start();
        }
		//add new user
		if (ae.getSource() == toolbar.button[16])
		{
            Thread runner = new Thread()
			{
                public void run()
				{
                    addUser = new AddUser();
                    desktop.add(addUser);
                    try
					{
                        addUser.setSelected(true);
                    } catch (Exception e)		{ }
                }
            };
            runner.start();
        }
		if (ae.getSource() == menu.dateReport)
		{
            new DateReport();   
        }

		// exit
		if (ae.getSource() == menu.exit || ae.getSource() == toolbar.button[17])
		{
            //dispose();
            System.exit(0);
        }
    }
}
