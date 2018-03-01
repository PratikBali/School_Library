import javax.swing.*;
import java.awt.event.*;
import javax.management.JMException;

public class Menubar extends JMenuBar
{
    public JMenu fileMenu, bookMenu, memberMenu, searchMenu, loanMenu, helpMenu, reportMenu;

    public JMenuItem printBook, exit, addBook, listBook, listAvailableBook, listBorrowedBook, editBook, removeBook;
    public JMenuItem addMember, listMember, editMember, removeMember;
    public JMenuItem searchBooksAndMembers, searchByBook, borrowBook, returnBook, listissuedbooks, help, about;
	public JMenuItem dateReport;

    public ImageIcon[] icons;
    public String[] imageName16 = 	{"images/Print16.gif",//0
									"images/Exit16.gif",//1
									 "images/Add16.gif",//2
									 "images/List16.gif",//3
									 "images/Edit16.gif", //4
									 "images/Delete16.gif",//5
									  "images/Find16.gif",//6
									 "images/Export16.gif",//7
									 "images/Import16.gif",//8
									 "images/Help16.gif",//9
									 "images/About16.gif"//10
									};

    public Menubar()
	{
        this.add(fileMenu = new JMenu("File"));
        this.add(bookMenu = new JMenu("Books"));
        this.add(memberMenu = new JMenu("Members"));
        this.add(searchMenu = new JMenu("Search"));
        this.add(loanMenu = new JMenu("Loan"));
        this.add(helpMenu = new JMenu("Help"));
        this.add(reportMenu = new JMenu("Report"));

        fileMenu.setMnemonic('f');
        bookMenu.setMnemonic('b');
        memberMenu.setMnemonic('m');
        searchMenu.setMnemonic('s');
        loanMenu.setMnemonic('l');
        helpMenu.setMnemonic('h');
        reportMenu.setMnemonic('r');

        icons = new ImageIcon[11];
        for (int i = 0; i < imageName16.length; i++)
		{
            icons[i] = new ImageIcon(imageName16[i]);
        }

       // fileMenu.add(printBook = new JMenuItem("Print Books", icons[0]));
		fileMenu.add(exit = new JMenuItem("Exit", icons[1]));

        bookMenu.add(addBook = new JMenuItem("Add Book", icons[2]));
        bookMenu.add(listBook = new JMenuItem("List All Books", icons[3]));
        bookMenu.add(listAvailableBook = new JMenuItem("List Available Books", icons[3]));
        bookMenu.add(listBorrowedBook = new JMenuItem("List Borrowed Books", icons[3]));
        bookMenu.add(editBook = new JMenuItem("Edit Books", icons[4]));
        bookMenu.add(removeBook = new JMenuItem("Remove Book", icons[5]));

        memberMenu.add(addMember = new JMenuItem("Add Member", icons[2]));
        memberMenu.add(listMember = new JMenuItem("List All Members", icons[3]));
        memberMenu.add(editMember = new JMenuItem("Edit Members", icons[4]));
        memberMenu.add(removeMember = new JMenuItem("Remove Member", icons[5]));

        searchMenu.add(searchBooksAndMembers = new JMenuItem("Search", icons[6]));
        searchMenu.add(searchByBook = new JMenuItem("Search by book", icons[6]));

        loanMenu.add(borrowBook = new JMenuItem("Borrow a Book", icons[7]));
        loanMenu.add(returnBook = new JMenuItem("Return a Book", icons[8]));
        loanMenu.add(listissuedbooks=new JMenuItem("Issued book details",icons[3]));

        helpMenu.add(help = new JMenuItem("Help", icons[9]));
        helpMenu.add(about = new JMenuItem("About", icons[10]));
		
        reportMenu.add(dateReport = new JMenuItem("Date to Date Report", icons[3]));

        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        searchBooksAndMembers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        searchByBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));

        addBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        listBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        editBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        removeBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        listissuedbooks.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
        listAvailableBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK));

        addMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        listMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        editMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        removeMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));

        borrowBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        returnBook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		
        help.setAccelerator(KeyStroke.getKeyStroke("F1"));
        
    }
}
