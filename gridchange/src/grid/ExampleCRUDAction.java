package grid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ExampleCRUDAction extends ActionSupport implements SessionAware
{
	
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private List<ExampleCRUDBean> myCustomers;
	private String oper = "";
	
	
	private String id;
	private String username;
	private String password;
	private String address;
	
	private String userMessage;
	public static String errorMessage;
	
	private List<ExampleCRUDBean> gridModel;
	
	public List<ExampleCRUDBean> getMyCustomers()
	{
		return myCustomers;
	}
	
	public void setMyCustomers(List<ExampleCRUDBean> myCustomers)
	{
		this.myCustomers = myCustomers;
	}
	
	private Integer rows = 0;
	
	private Integer page = 0;
	
	// sorting order - asc or desc
	private String sord;
	
	// get index row - i.e. user click to sort.
	private String sidx;
	
	@SuppressWarnings("unused")
	private String searchField;
	
	// The Search String
	private String searchString;
	
	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;
	
	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;
	
	// Your Total Pages
	private Integer total = 0;
	
	// All Records
	private Integer records = 0;
	private boolean loadonce = false;
	
	@SuppressWarnings("unchecked")
	public String execute()
	{
		Object list = session.get("mylist");
		if (list != null)
		{
			myCustomers = (List<ExampleCRUDBean>) list;
		}
		else
		{
			myCustomers = ExampleCRUDDAO.buildList();
		}
		
		if (oper != null)
		{
			if (oper.equalsIgnoreCase("add"))
			{
				oper = null;
				ExampleCRUDBean example = new ExampleCRUDBean(id, username, password,address);
				try
				{
					boolean b = ExampleCRUDDAO.add(example);
					if (b)
					{
						userMessage = "success";
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					userMessage = "Can't create because a record with same Username already exist.";
				}
				
			}
			else if (oper.equalsIgnoreCase("edit"))
			{
				oper = null;
				ExampleCRUDBean example = new ExampleCRUDBean(id, username, password,address);
				try
				{
					boolean b = ExampleCRUDDAO.update(example);
					if (b)
					{
						userMessage = "success";
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					if (e.getErrorCode() == 2292)
					{
						userMessage = "Can't update the record because its being used some where.";
					}
					else
					{
						userMessage = "Can't update because a record with same Username already exist.";
					}
				}
				
			}
			else if (oper.equalsIgnoreCase("del"))
			{
				int i = 0;
				String[] searchValues = id.split(",");
				for (String search : searchValues)
				{
					ExampleCRUDBean example = new ExampleCRUDBean(search, "", "","");
					try
					{
						boolean b = ExampleCRUDDAO.delete(example);
						if (b && i < 1)
						{
							userMessage = "success";
						}
						else
						{
							userMessage = "Can't delete the record because its being used some where.";
							i++;
						}
					}
					catch (SQLException e)
					{
						e.printStackTrace();
						userMessage = "Can't delete the record because its being used some where.";
						i++;
					}
				}
			}
			oper = null;
		}
		
		// Count all record
		records = ExampleCRUDDAO.getExampleCRUDBeansCount(myCustomers);
		
		if (totalrows != null)
		{
			records = totalrows;
		}
		
		// Calculate until rows ware selected
		int to = (rows * page);
		
		// Calculate the first row to read
		int from = to - rows;
		
		// Set to = max rows
		if (to > records)
			to = records;
		
		if (loadonce)
		{
			if (totalrows != null && totalrows > 0)
			{
				setGridModel(myCustomers.subList(0, totalrows));
			}
			else
			{
				setGridModel(myCustomers);
			}
		}
		else
		{
			if (searchString != null && searchOper != null)
			{
				if (searchOper.equalsIgnoreCase("eq"))
				{
					List<ExampleCRUDBean> cList = new ArrayList<ExampleCRUDBean>();
					cList = ExampleCRUDDAO.findById(myCustomers, searchString, searchField);
					setGridModel(cList);
				}
				else
				{
					List<ExampleCRUDBean> cList = new ArrayList<ExampleCRUDBean>();
					cList = ExampleCRUDDAO.findById(myCustomers, searchString, searchField);
					setGridModel(cList);
				}
			}
			else
			{
				myCustomers = ExampleCRUDDAO.buildList();
				
				if (sord != null && sord.equalsIgnoreCase("asc"))
				{
					if (sidx.equalsIgnoreCase("username"))
					{
						ExampleCRUDBean.name = "username";
						Collections.sort(myCustomers);
					}
					if (sidx.equalsIgnoreCase("password"))
					{
						ExampleCRUDBean.name = "password";
						Collections.sort(myCustomers);
					}
					if (sidx.equalsIgnoreCase("address"))
					{
						ExampleCRUDBean.name = "address";
						Collections.sort(myCustomers);
					}
				}
				if (sord != null && sord.equalsIgnoreCase("desc"))
				{
					if (sidx.equalsIgnoreCase("username"))
					{
						ExampleCRUDBean.name = "username";
						Collections.sort(myCustomers);
						Collections.reverse(myCustomers);
					}
					if (sidx.equalsIgnoreCase("password"))
					{
						ExampleCRUDBean.name = "password";
						Collections.sort(myCustomers);
						Collections.reverse(myCustomers);
					}
					if (sidx.equalsIgnoreCase("address"))
					{
						ExampleCRUDBean.name = "address";
						Collections.sort(myCustomers);
						Collections.reverse(myCustomers);
					}
				}
				setGridModel(ExampleCRUDDAO.getExample(myCustomers, from, to));
			}
			
		}
		
		// Calculate total Pages
		total = (int) Math.ceil((double) records / (double) rows);
		
		myCustomers = ExampleCRUDDAO.buildList();
		session.put("mylist", myCustomers);
		return SUCCESS;
	}
	
	public String getJSON()
	{
		return execute();
	}
	
	public Integer getRows()
	{
		return rows;
	}
	
	public void setRows(Integer rows)
	{
		this.rows = rows;
	}
	
	public Integer getPage()
	{
		return page;
	}
	
	public void setPage(Integer page)
	{
		this.page = page;
	}
	
	public Integer getTotal()
	{
		return total;
	}
	
	public void setTotal(Integer total)
	{
		this.total = total;
	}
	
	public Integer getRecords()
	{
		return records;
	}
	
	public void setRecords(Integer records)
	{
		
		this.records = records;
		
		if (this.records > 0 && this.rows > 0)
		{
			this.total = (int) Math.ceil((double) this.records / (double) this.rows);
		}
		else
		{
			this.total = 0;
		}
	}
	
	public List<ExampleCRUDBean> getGridModel()
	{
		return gridModel;
	}
	
	public void setGridModel(List<ExampleCRUDBean> gridModel)
	{
		this.gridModel = gridModel;
	}
	
	public String getSord()
	{
		return sord;
	}
	
	public void setSord(String sord)
	{
		this.sord = sord;
	}
	
	public String getSidx()
	{
		return sidx;
	}
	
	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}
	
	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}
	
	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}
	
	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}
	
	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public void setTotalrows(Integer totalrows)
	{
		this.totalrows = totalrows;
	}
	
	public String getOper()
	{
		return oper;
	}
	
	public void setOper(String oper)
	{
		this.oper = oper;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserMessage()
	{
		return userMessage;
	}
	
	public void setUserMessage(String userMessage)
	{
		this.userMessage = userMessage;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
