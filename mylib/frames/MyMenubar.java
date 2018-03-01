import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.management.JMException;

class MyMenubar extends JFrame
{
    JMenu fileMenu, bookMenu, memberMenu, searchMenu, loanMenu, helpMenu;

    JMenuItem printBook, exit, addBook, listBook, listAvailbleBook, listBorrowedBook, editBook, removeBook;
    JMenuItem addMember, listMember, editMember, removeMember, memberInfo;
    JMenuItem  searchBooksAndMembers, borrowBook, returnBook, listissuedbooks, help, about;

    ImageIcon[] icons;
    String[] imageName16 = 	{"images/Print16.gif", "images/Exit16.gif",
									 "images/Add16.gif", "images/List16.gif",
									 "images/Edit16.gif", "images/Delete16.gif",
									 "images/Information16.gif", "images/Find16.gif",
									 "images/Export16.gif", "images/Import16.gif",
									 "images/Help16.gif", "images/About16.gif"
									};
									
	JMenuBar mbr;

    MyMenubar()
	{
		setTitle("MenuBar");
		setSize(600,300);
		setLocation(100,100);
		setLayout(null);
		
		mbr = new JMenuBar();
		
		mbr.setBounds(0,0,600,20);	
		
        fileMenu = new JMenu("File");
        bookMenu = new JMenu("Books");
        memberMenu = new JMenu("Members");
        searchMenu = new JMenu("Search");
        loanMenu = new JMenu("Loan");
        helpMenu = new JMenu("Help");

        fileMenu.setMnemonic('f');
        bookMenu.setMnemonic('b');
        memberMenu.setMnemonic('m');
        searchMenu.setMnemonic('s');
        loanMenu.setMnemonic('l');
        helpMenu.setMnemonic('h');

        icons = new ImageIcon[12];
		
        for (int i = 0; i < imageName16.length; i++)
		{
            icons[i] = new ImageIcon(imageName16[i]);
        }
		
		add(mbr);
		
        fileMenu.add(printBook = new JMenuItem("Print Books", icons[0]));
		fileMenu.add(exit = new JMenuItem("Exit", icons[1]));

        bookMenu.add(addBook = new JMenuItem("Add Book", icons[2]));
        bookMenu.add(listBook = new JMenuItem("List All Books", icons[3]));
        bookMenu.add(listAvailbleBook = new JMenuItem("List Availble Books", icons[3]));
        bookMenu.add(listBorrowedBook = new JMenuItem("List Borrowed Books", icons[3]));
        bookMenu.add(editBook = new JMenuItem("Edit Books", icons[4]));
        bookMenu.add(removeBook = new JMenuItem("Remove Book", icons[5]));

        memberMenu.add(addMember = new JMenuItem("Add Member", icons[2]));
        memberMenu.add(listMember = new JMenuItem("List All Members", icons[3]));
        memberMenu.add(editMember = new JMenuItem("Edit Members", icons[4]));
        memberMenu.add(removeMember = new JMenuItem("Remove Member", icons[5]));
        memberMenu.add(memberInfo = new JMenuItem("Member Information", icons[6]));

        searchMenu.add(searchBooksAndMembers = new JMenuItem("Search", icons[7]));

        loanMenu.add(borrowBook = new JMenuItem("Borrow a Book", icons[8]));
        loanMenu.add(returnBook = new JMenuItem("Return a Book", icons[9]));
        loanMenu.add(listissuedbooks=new JMenuItem("Issued book details",icons[3]));

        helpMenu.add(help = new JMenuItem("Help", icons[10]));
        helpMenu.add(about = new JMenuItem("About", icons[11]));

		mbr.add(fileMenu); mbr.add(bookMenu); mbr.add(memberMenu); mbr.add(searchMenu); mbr.add(loanMenu); mbr.add(helpMenu);
		
        printBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        searchBooksAndMembers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        addBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        listBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        editBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        removeBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        addMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        listMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        editMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        removeMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

        borrowBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        returnBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        listissuedbooks.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
        help.setAccelerator(KeyStroke.getKeyStroke("F1"));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	public static void main(String a[])
	{
		new MyMenubar();
	}
	
}
