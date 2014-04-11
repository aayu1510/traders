package grid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAO;


public class ExampleCRUDDAO
{
	public static List<ExampleCRUDBean> getExample(List<ExampleCRUDBean> list, int from, int to)
	{
		return list.subList(from, to);
	}
	
	public static List<ExampleCRUDBean> findById(List<ExampleCRUDBean> list, String searchText, String searchField)
	{
		List<ExampleCRUDBean> exampleList = new ArrayList<ExampleCRUDBean>();
		if (searchText.length() > 0)
		{
			System.out.println("searchText: "+searchText);
			System.out.println("searchField: "+searchField);
			String searchString = ".*" + searchText.toLowerCase().trim() + ".*";
			for (ExampleCRUDBean exampleBean : list)
			{
				try
				{
					if (searchField.equalsIgnoreCase("username"))
					{
						if (exampleBean.getUsername().toLowerCase().matches(searchString))
						{
							exampleList.add(exampleBean);
						}
					}
					else if (searchField.equalsIgnoreCase("password"))
					{
						if (exampleBean.getPassword().toLowerCase().matches(searchString))
						{
							exampleList.add(exampleBean);
						}
					}
					else if (searchField.equalsIgnoreCase("address"))
					{
						if (exampleBean.getAddress().toLowerCase().matches(searchString))
						{
							exampleList.add(exampleBean);
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (exampleList.size() > 0)
			{
				return exampleList;
			}
		}
		else
		{
			return list;
		}
		return null;
	}
	
	public static Integer getExampleCRUDBeansCount(List<ExampleCRUDBean> list)
	{
		return list.size();
	}
	
	public static List<ExampleCRUDBean> buildList()
	{
		List<ExampleCRUDBean> exampleList = new ArrayList<ExampleCRUDBean>();
		try
		{
			String sql = "select * from login2";
			PreparedStatement pstm = DAO.getInstance().getPstm(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next())
			{
				String username = rs.getString(1);
				String password = rs.getString(2);
				String address= rs.getString(3);
				
				exampleList.add(new ExampleCRUDBean(username,username, password, address));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return exampleList;
	}
	
	public static boolean add(ExampleCRUDBean example) throws SQLException
	{
		boolean result = false;
		
		DAO dao = DAO.getInstance();
		Connection con =dao.getConnection();
		PreparedStatement ps = dao.getPstm("insert into login2 values(?, ?,?)");
		ps.setString(1, example.getUsername());
		ps.setString(2, example.getPassword());
		ps.setString(3, example.getAddress());
		ps.executeUpdate();
		
		result = true;
		/*
		ps.close();
		con.close();
		*/
		return result;
	}
	
	public static boolean update(ExampleCRUDBean example) throws SQLException
	{
		boolean result = false;

		DAO dao = DAO.getInstance();
		Connection con =dao.getConnection();
		PreparedStatement ps = con.prepareCall("update login2 set username=?, password=?,address=? where username=?");
		ps.setString(1, example.getUsername());
		ps.setString(2, example.getPassword());
		ps.setString(3, example.getAddress());
		ps.setString(4, example.getId());
		ps.executeUpdate();
		/*
		ps.close();
		con.close();
		*/
		result = true;
		
		return result;
	}
	
	public static boolean delete(ExampleCRUDBean example) throws SQLException
	{
		boolean result = false;

		DAO dao = DAO.getInstance();
		Connection con =dao.getConnection();
		PreparedStatement ps = con.prepareCall("delete from login2 where username=?");
		ps.setString(1, example.getId());
		ps.executeUpdate();
		/*
		ps.close();
		con.close();
		*/
		result = true;
		
		return result;
	}
}