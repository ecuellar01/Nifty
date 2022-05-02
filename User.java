import java.io.*;
import java.util.*;
import java.sql.*;
import java.lang.*;
public class User {
	private String userName;
	private String password;
	private String firstName;
	private String lastName;

	User() {
	}

	User(String userName, String password, String firstName, String lastName) {
		this.setUserName(userName);
		this.setPassword(password);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void insertDatabase() throws SQLException, ClassNotFoundException {
		String query = "INSERT INTO USERS (userName, password, firstName, lastName)"
				+ " values(?, ?, ?, ?)";
		Connection data = null;
		PreparedStatement preState = null;

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			preState.setString(1, userName);
			preState.setString(2, password);
			preState.setString(3, firstName);
			preState.setString(4, lastName);

			preState.executeUpdate();
		}
		finally{
			try{data.close();} catch(Exception ex){}
			try{preState.close();} catch(Exception ex){}
		}
	}

	private static Connection InitializeData() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");      // Load the JDBC driver
		// Establish a connection

		return DriverManager.getConnection
				("jdbc:mysql://localhost/javabook", "user1", "user1");
		}

	//needs implementation only if we create GUI for this option
	public void updateDatabase() {
	}

	public static Boolean queryUser(String userName, String password) throws SQLException, ClassNotFoundException {
		String query = "SELECT Count(*) AS \"Count\" FROM USERS WHERE username = ? AND password = ?";

		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		int count = 0;

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			preState.setString(1, userName);
			preState.setString(2, password);
			rs = preState.executeQuery();

			while(rs.next())
				count = rs.getInt("Count");

			return (count > 0);
		} finally {
			try { data.close(); } catch (Exception ex) {}
			try { preState.close(); } catch (Exception ex) {}
			try { rs.close(); } catch (Exception ex) {}
		}
	}

	public static User initializeUser(String userName, String password) throws SQLException, ClassNotFoundException {
		String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";

		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		User user = new User();

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			preState.setString(1, userName);
			preState.setString(2, password);
			rs = preState.executeQuery();

			while (rs.next()) {
				user.userName = rs.getString("username");
				user.password = rs.getString("password");
				user.firstName = rs.getString("firstName");
				user.lastName = rs.getString("lastName");
			}

			return user;

		} 
		finally {
			//System.out.println(rs.getString(1) + "\t" +
			         // rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
			try { data.close(); } catch (Exception ex) {}
			try { preState.close(); } catch (Exception ex) {}
			try { rs.close(); } catch (Exception ex) {}
		}
	}
	
	public static Boolean duplicateUser(String userName) throws SQLException, ClassNotFoundException {
		String query = "SELECT Count(*) AS \"Count\" FROM USERS WHERE username = ?";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		int count = 0;

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			preState.setString(1, userName);
			rs = preState.executeQuery();

			while(rs.next())
				count = rs.getInt("Count");

			return (count > 0);

		} finally {
			try { data.close();} catch (Exception ex) {}
			try { preState.close(); } catch (Exception ex) {}
			try { rs.close(); } catch (Exception ex) {}
		}
	}

	public void insertLastUser() throws SQLException, ClassNotFoundException{
		String query = "INSERT INTO USERLOG (userName, password, firstName, lastName)"
				+ " values(?, ?, ?, ?)";

		Connection connection = InitializeData();
		PreparedStatement preState = connection.prepareStatement(query);

		try {
			preState.setString(1, userName);
			preState.setString(2, password);
			preState.setString(3, firstName);
			preState.setString(4, lastName);

			preState.executeUpdate();
		}
		finally{
			try { connection.close(); } catch (Exception ex) {}
			try { preState.close(); } catch (Exception ex) {}
		}
	}

	public static User initializeLastUser() throws SQLException, ClassNotFoundException{
		String query = "SELECT userName, password, firstName, lastName FROM USERLOG WHERE id = (SELECT MAX(id) FROM USERLOG)";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		User user = new User();

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			rs = preState.executeQuery();

			while (rs.next()) {
				user.userName = rs.getString("username");
				user.password = rs.getString("password");
				user.firstName = rs.getString("firstName");
				user.lastName = rs.getString("lastName");
			}

			return user;
		}
		finally{
			try{data.close();} catch (Exception ex){}
			try{preState.close();} catch (Exception ex){}
			try{rs.close();} catch (Exception ex){}
		}
	}
}