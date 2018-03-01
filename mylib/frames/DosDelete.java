// dos program to delete data from table
import java.io.*;
import java.sql.*;

class DosDelete
{
	public static void main(String a[])throws Exception
	{
		String sql;
		
		Connection cn=null;
		Statement stm=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
		try
		{	cn = DriverManager.getConnection("jdbc:mysql:///temp","root","");
			stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs= stm.executeQuery("Select * from mytable");
		}
		catch(Exception ex){ex.printStacktrace();		}
		
		System.out.println("\n\t Enter the Roll no to delete");
		int roll=Integer.parseInt(br.readLine());
		
		try
		{	sql = "delete from mytable where roll="+roll;
			ps = cn.prepareStatement(sql);
			ps.execute();
			ps.close();
			System.out.println("\n \t Record Deleted Successfully");	
		}
		catch(Exception ex)	{ex.printStacktrace();}
	}
}
