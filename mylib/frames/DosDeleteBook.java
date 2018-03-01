// dos program to delete data from table
import java.io.*;
import java.sql.*;

class DosDeleteBook
{
	public static void main(String a[])throws Exception
	{
		String sql;
		int flag=0;
		
		Connection cn=null;
		Statement stm=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
		try
		{cn = DriverManager.getConnection("jdbc:mysql:///library","root","");
		stm=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(Exception ex)
		{ex.printStackTrace();
		}
		
		System.out.println("\n\t Enter the BookID to delete");
		int data=Integer.parseInt(br.readLine());
		rs=stm.executeQuery("select * from  Books where BookID=" + data);
		
		try
		{
			rs.first();
			int numberOfBooks = rs.getInt(11);
			System.out.println(numberOfBooks);
			
			if (numberOfBooks == 1)
			{
				sql = "delete from Books where BookID=" + data;
				ps = cn.prepareStatement(sql);
				ps.execute();
				ps.close();
			}
			if (numberOfBooks > 1)
			{
				numberOfBooks -= 1;
				String query = "UPDATE Books SET NumberOfBooks = " + numberOfBooks + " WHERE BookID = " + data;
				ps = cn.prepareStatement(query);
				ps.execute();
				ps.close();
			}
		}
		catch(Exception ex)	{ex.printStackTrace();}
	}
}
