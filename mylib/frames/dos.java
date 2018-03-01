import java.io.*;
import java.sql.*;

class dos
{
	public static void main(String arg[])throws Exception
	{
		String name;
		int roll,age;
		
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new InputStreamReader (System.in());
		
		Connection cn=null;
		PreparedStatement prstm;
		String sql;
		
		try
		{
		  cn=DriverManager.getConnection("jdbc:mysql:///temp","root","");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("\n\t Enter the Roll no :");
			roll=Integer.parseInt(br.readLine());
		System.out.println("\n\t Enter the Name :");
			name=br.readLine();
		System.out.println("\n\t Enter the Age :");
			age=Integer.parseInt(br.readLine());
		
		try
		{
			sql="insert into mytable values("+roll+",'"+name+"',"+age+")";
			prstm=cn.prepareStatement(sql);
			prstm.execute();	prstm.close();
			System.out.println("\n\t ** Record Successfully Inserted **:"); 	
		}
		catch(SQLException e1)
		{ System.out.println(e1);  }
				
	}
}
