import java.sql.*;

public class Borrow
 {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	private int bookID;
	private int memberID;
	private Date dayOfBorrowed;
	private Date dayOfReturn;
	private String URL = "jdbc:mysql:///library","root","";

	public Borrow()
	{
	}

	public void connection()
	{
		try
		{
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Borrow");
			while (resultSet.next()) 
			{
				bookID = resultSet.getInt(1);
				memberID = resultSet.getInt(2);
				dayOfBorrowed = resultSet.getDate(3);
				dayOfReturn = resultSet.getDate(4);
			}
			resultSet.close();
			statement.close();
			connection.close();
		}
		catch (SQLException SQLe)
		{
			SQLe.printStackTrace();
		}
	}

	public void update(String Query)
	{
		try
		{
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			statement.executeUpdate(Query);
			statement.close();
			connection.close();
		}
		catch (SQLException SQLe)
		{
			SQLe.printStackTrace();
		}
	}
}