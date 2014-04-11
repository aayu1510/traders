package grid;

import java.io.Serializable;

public class ExampleCRUDBean implements Comparable<ExampleCRUDBean>, Serializable 
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String address;
	public static String name = "";

	public String getName() {
		return name;
	}

	public ExampleCRUDBean(String id, String uomSName, String uomFName,String addr) {
		this.id = id;
		username = uomSName;
		password = uomFName;
		address=addr;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public int compareTo(ExampleCRUDBean o) {
		int i = 0;

		if (name.equalsIgnoreCase("password")) 
		{
			i = this.password.toLowerCase().compareTo(o.password.toLowerCase());
		}
		
		if (name.equalsIgnoreCase("username")) 
		{
			i = this.username.toLowerCase().compareTo(o.username.toLowerCase());
		}
		if (name.equalsIgnoreCase("address")) 
		{
			i = this.address.toLowerCase().compareTo(o.address.toLowerCase());
		}
		return i;
	}

	
}
