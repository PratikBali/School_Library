import javax.swing.*;

public class Toolbar extends JToolBar 
{
	public JButton[] button;
	public String[] imageName24 = {"images/Add24.gif", "images/List24.gif", "images/List24.gif", "images/List24.gif", "images/Edit24.gif", 								"images/Delete24.gif",
	                                "images/Add24.gif", "images/List24.gif", "images/Edit24.gif", "images/Delete24.gif",
	                               "images/Find24.gif","images/Find24.gif", "images/Export24.gif", "images/Import24.gif", 
								   "images/Help24.gif","images/About24.gif",
								   "images/Add24.gif", "images/Exit24.gif"
								   };
	public String[] tipText = {"Add Books", "List All Books", "List Available Books", "List Borrowed Books", "Edit Books", "Remove Books",
	                           "Add Members", "List Members", "Edit Members", "Remove Members", 
	                           "Search", "Search By Book","Borrow Books", "Return Books",
							   "Help", "About",
							   "Add New User","Exit"
							   };

	public Toolbar()
	{
		button = new JButton[18];
		for (int i = 0; i < imageName24.length; i++) 
		{
			if (i == 6 || i == 10 || i == 14 || i == 16)
			addSeparator();
			addSeparator();
			add(button[i] = new JButton(new ImageIcon(imageName24[i])));
			button[i].setToolTipText(tipText[i]);
			//button[8].setVisible(false);
		}
	}
}