import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ReturnBooks extends JFrame implements ActionListener
{
    private JPanel northPanel = new JPanel();
    private JLabel title = new JLabel("BOOK INFORMATION");
    private JPanel centerPanel = new JPanel();
    private JPanel informationPanel = new JPanel();
    private JLabel[] informationLabel = new JLabel[2];
    private String[] informationString = {" Write the Book ID", " Write the Member ID"};
    private JTextField[] informationTextField = new JTextField[2];
    private String[] data;
    private JLabel lblFinePerDay = new JLabel("Fine/Day");
    private JTextField txtFinePerDay = new JTextField();
    private JLabel lblTotalFineAmt = new JLabel("Total fine amount");
    private JTextField txtTotalFineAmt = new JTextField();
    private JPanel returnButtonPanel = new JPanel();
    private JButton returnButton = new JButton("Return");
    private JPanel southPanel = new JPanel();
    private JButton cancelButton = new JButton("Cancel");
	
	//private Borrow borrow;
	
		private String query;
	private Connection cn = null;
	private Statement stm = null;
	private PreparedStatement ps = null;
	private ResultSet rsBook = null;
	private ResultSet rsMember = null;
	private ResultSet rsTrans = null;

    public boolean isCorrect()
	{
        data = new String[2];
        for (int i = 0; i < informationLabel.length; i++)
		{
            if (!informationTextField[i].getText().equals(""))
			{
                data[i] = informationTextField[i].getText();
            } else
			{
                return false;
            }
        }
        return true;
    }

    public void clearTextField()
	{
        for (int i = 0; i < informationTextField.length; i++)
		{
            if (i != 2)
			{
                informationTextField[i].setText(null);
            }
            txtFinePerDay.setText(null);
            txtTotalFineAmt.setText(null);
        }
    }

    public ReturnBooks()
	{
        super("Return books");
        //super("Return books", false, true, false, true);
        //setFrameIcon(new ImageIcon("images/Import16.gif"));
        Container cp = getContentPane();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        title.setFont(new Font("Tahoma", Font.BOLD, 14));
        northPanel.add(title);
        cp.add("North", northPanel);

        centerPanel.setLayout(new BorderLayout());
        informationPanel.setLayout(new GridLayout(4, 2, 1, 1));
        for (int i = 0; i < informationLabel.length; i++)
		{
            informationPanel.add(informationLabel[i] = new JLabel(informationString[i]));
            informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
            informationPanel.add(informationTextField[i] = new JTextField());
            informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
        }
        informationPanel.add(lblFinePerDay);
        informationPanel.add(txtFinePerDay);
        informationPanel.add(lblTotalFineAmt);
        informationPanel.add(txtTotalFineAmt);
        txtTotalFineAmt.setEditable(false);
        txtFinePerDay.addKeyListener(new keyListener());
        centerPanel.add("Center", informationPanel);
		
        returnButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        returnButtonPanel.add(returnButton);
        returnButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        centerPanel.add("South", returnButtonPanel);
        centerPanel.setBorder(BorderFactory.createTitledBorder("Return a book:"));
        cp.add("Center", centerPanel);

        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(cancelButton);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql:///library","root","");
			stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rsBook=stm.executeQuery("select * from  Books");
			rsMember=stm.executeQuery("select * from  Members");
			rsTrans=stm.executeQuery("select * from  Transaction");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

        returnButton.addActionListener(this);
        cancelButton.addActionListener(this);
        setVisible(true);
        pack();
    }

    public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if (ae.getSource() == returnButton)
			{
				//for checking if there is a missing information
				if (isCorrect())
				{
	
							rsBook=stm.executeQuery("SELECT * FROM Books WHERE BookID = " + data[0]);
							rsMember=stm.executeQuery("SELECT * FROM Members WHERE MemberID = " + data[1]);
							
							int numberOfBooks = rsBook.getInt(11);

							if (numberOfBooks >= 0)
							{
								numberOfBooks += 1;
								
								query=("UPDATE Books SET NumberOfBooks =" + numberOfBooks + ",Status = null WHERE BookID =" + data[0]);
								ps = cn.prepareStatement(query);
								ps.execute();
								ps.close();
								//borrow.update("DELETE FROM Borrow WHERE BookID =" + data[0] + " AND MemberID =" + data[1]);
							   
								clearTextField();
							} 
							else
							{
								JOptionPane.showMessageDialog(null, "The book is not borrowed", "Warning", JOptionPane.WARNING_MESSAGE);
							}
				}
			 
				else
				{
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
			if (ae.getSource() == cancelButton)
			{
				dispose();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    }

    class keyListener extends KeyAdapter
	{
        public void keyPressed(KeyEvent k)
		{
            java.sql.Date da = null;
            if (k.getKeyCode() == KeyEvent.VK_ENTER)
			{
                try 
				{
                    int fineamt = Integer.parseInt(txtFinePerDay.getText());
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    Connection con = DriverManager.getConnection("jdbc:odbc:JLibrary");
                    Statement st = con.createStatement();
                    int bookid = Integer.parseInt(informationTextField[0].getText());
                    int memid = Integer.parseInt(informationTextField[1].getText());
                    try
					{
                        String sql = "SELECT ReturnDate from Transaction where MemberID=" + memid + " and BookID=" + bookid;
                        ResultSet rs = st.executeQuery(sql);
                        if (rs.next()) {
                            
                            da = rs.getDate(1);                            
                            java.util.Date today = new java.util.Date();                            
                            /*java.util.Date retdate=new java.util.Date(da.getYear(),da.getMonth(),da.getDate());
                            JOptionPane.showMessageDialog(null, "today=" + today + "\nRet date=" + retdate);*/
                            
                            System.out.println(today.after(da));
                            
                            if (today.after(da))
							{
                                long finedays = today.getTime() - da.getTime();
                                int days = (int) (finedays / (1000 * 60 * 60 * 24));
                                System.out.println(days);
                                txtTotalFineAmt.setText(String.valueOf(fineamt * days));
                            }
							else
							{
                                txtTotalFineAmt.setText("0");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Member ID entered not found on databse");
                        }
                        
                    } catch (Exception ex1)
					{
                        JOptionPane.showMessageDialog(null, "Error, Cannot retrieve date value from table" + ex1.toString());
                    }

                } catch (Exception ex)
				{
                    JOptionPane.showMessageDialog(null, "Error, cannot connect to database" + ex.toString());
                }
            }
        }
    }//inner class closed
		public static void main(String a[])
	{
		new ReturnBooks();
	}
}//class closed
