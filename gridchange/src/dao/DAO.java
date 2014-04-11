package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO
{
	/*
	 * Can modify user name and password as per Oracle Server Installation.
	 */
	private final String driverClassName = "oracle.jdbc.driver.OracleDriver";	
	private final String connectionUrl = "jdbc:oracle:thin:@192.168.1.106:1521:xe";
	
	private final String dbUser = "BATCH_B_PC_9";
	private final String dbPwd = "BATCH_B_PC_9";

	/*
	private final String dbUser = "traders";
	private final String dbPwd = "traders";
	*/
	
	private static DAO dao = null;
	private Connection con = null;
	private PreparedStatement pstm = null;

	/*
	 * =================================================Restricted Area============================================= 
	 * ======================================Please do not modify below mention code========================================
	 */

	private DAO()
	{
		try
		{
			Class.forName(driverClassName);
			con = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public static DAO getInstance()
	{
		if (dao == null)
		{
			dao = new DAO();
		}
		return dao;
	}
	
	public void clearInstance()
	{
		DAO.dao = null;
	}

	public Connection getConnection() throws SQLException
	{
		getInstance();
		return con;
	}

	public PreparedStatement getPstm(String sql) throws SQLException
	{
		getConnection();
		pstm = con.prepareStatement(sql);
		return pstm;
	}

	public void closeConnection() throws SQLException
	{
		if (con != null)
		{
			con.close();
			con = null;
		}
	}
}