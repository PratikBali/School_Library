import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

class SearchByBook extends JFrame implements ActionListener,ItemListener
{
	private JLabel lblSearch,lblTitle;
	private JTextField tfSearch;
	private JButton searchButton,clrButton;
	
	private JPanel searchPanel,bookPanel;
	private List searchList,bookList;
	
	private ResultSet rs=null;
	private Connection cn=null;
	private Statement stm=null;
	private PreparedStatement ps=null;
	
	SearchByBook()
	{
		super("Book Record Search");
		setLocation(200,100);
		setSize(800,500);
		setLayout(null);

		lblTitle = new JLabel("Search Books By Name");
		add(lblTitle);
		lblTitle.setFont(new Font("Times New Roman",Font.BOLD,20));
		lblTitle.setBounds(300,10,300,50);
		
		lblSearch = new JLabel("Enter Book Name");
		add(lblSearch);
		lblSearch.setBounds(200,60,100,20);
		
		tfSearch = new JTextField();
		add(tfSearch);
		tfSearch.setBounds(310,60,200,20);
		
		searchButton = new JButton("Search");
		add(searchButton);
		searchButton.setBounds(520,60,100,20);
		
		clrButton = new JButton("Clear");
		add(clrButton);
		clrButton.setBounds(345,420,100,30);

		searchPanel = new JPanel();
		add(searchPanel);
		searchPanel.setLayout(null);
		searchPanel.setBackground(Color.PINK);
		searchPanel.setBounds(40,100,350,300);
		searchPanel.setVisible(true);
		
		bookPanel = new JPanel();
		add(bookPanel);
		bookPanel.setLayout(null);
		bookPanel.setBackground(Color.YELLOW);
		bookPanel.setBounds(400,100,350,300);
		bookPanel.setVisible(true);
		
		searchList = new List();
		searchPanel.add(searchList);
		searchList.setBounds(10,10,330,280);
		//searchList.add("prince");
		
		bookList = new List();
		bookPanel.add(bookList);
		bookList.setBounds(10,10,330,280);
		//bookList.add("snehkavi");
		
		searchButton.addActionListener(this);
		clrButton.addActionListener(this);
		searchList.addItemListener(this);
		bookList.addItemListener(this);

		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		tfSearch.addKeyListener(new KeyAdapter()
        {   public void keyPressed(KeyEvent e)
            {   if(e.getKeyChar() == KeyEvent.VK_ENTER)
                { try
                    {   searchList.clear();
                        rs = stm.executeQuery("select * from Books where Title like '%" + tfSearch.getText() + "%'");
                        while(rs.next())
                            searchList.addItem(rs.getString(4));
                    }
                    catch(Exception ee){ee.printStackTrace();}
                }
            }
        });


		setVisible(true);
	}
	
	public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource() == searchList)
        {
            try
            {
                String sql = "select Title,Status from Books where Title='"+ searchList.getSelectedItem() + "'";
                rs = stm.executeQuery(sql);
                rs.next();
                bookList.addItem(rs.getString(1));
                bookList.addItem(rs.getString(2));
            }
            catch (SQLException e1){e1.printStackTrace();}
        }
    }

	
	public void actionPerformed(ActionEvent ae)
	{int flag = 0;
		try
		{
			if(ae.getSource()==searchButton)
			{
				if(tfSearch.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Please Enter Book name","Warning",JOptionPane.WARNING_MESSAGE);
					tfSearch.requestFocus();				
				}
				else
				{
					//searchList.add(tfSearch.getText());
					
					String name = tfSearch.getText();
					searchList.clear();
                    rs = stm.executeQuery("select * from Books where Title like '%" + name + "%'");
                    while(rs.next())
                        searchList.addItem(rs.getString(4));				
				}
			}
			if(ae.getSource()==clrButton)
			{
				//searchList.clear();
				String item[] = searchList.getItems();
				if(item.length!=0)
				{
					for(int i=0;i<item.length;i++)
					{
						//l2.add(item[i]);
						searchList.remove(item[i]);
					}
				}
				bookList.clear();
				tfSearch.setText(null);
				tfSearch.requestFocus();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String a[])
	{
		new SearchByBook();
	}
}
