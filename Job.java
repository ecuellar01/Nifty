import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Job {

	private int hours;
	private double cost;
	private double invoice;
	private double profit;
	private double hourlyPay;
	private int jobNum;
	private java.sql.Date date;
	private User currentUser;

	public Job() {
	}
	public Job(User currentUser, int hours, double cost, double invoice, java.sql.Date date) {
		this.invoice = invoice;
		this.cost = cost;
		this.hours = hours;
		this.date = date;
		profit = invoice - cost;
		hourlyPay = profit / hours;
		this.currentUser = currentUser;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getInvoice() {
		return invoice;
	}
	public void setInvoice(double invoice) {
		this.invoice = invoice;
	}
	public void setProfit(){ profit = invoice - cost; }
	public double getProfit() {
		return profit;
	}
	public double getHourlyPay() {
		return hourlyPay;
	}
	public void setHourlyPay(){
		if(hours == 0)
			hourlyPay = 0;
		else
			hourlyPay = profit / hours;
	}
	public int getJobNum() {
		return jobNum;
	}
	public void setJobNum(int jobNum) {
		this.jobNum = jobNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	public void setUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	private static Connection InitializeData() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");      // Load the JDBC driver
		// Establish a connection

        return DriverManager.getConnection
				("jdbc:mysql://localhost/javabook", "user1", "user1");

	}

	public void insertDatabase()  throws SQLException, ClassNotFoundException{

	String query = "INSERT INTO JOBS (invoice, cost, hours, profit, date, user)"
			+ " values(?, ?, ?, ?, ?, ?)";

        Connection connection = InitializeData();
        PreparedStatement preState = connection.prepareStatement(query);

	try {
        preState.setDouble(1, invoice);
        preState.setDouble(2, cost);
        preState.setInt(3, hours);
        preState.setDouble(4, profit);
        preState.setDate(5, date);
        preState.setString(6, currentUser.getUserName());

        preState.executeUpdate();
    }
    finally{
        try { connection.close(); } catch (Exception ex) {}
        try { preState.close(); } catch (Exception ex) {}
    }
}

public void updateDatabase(int refNum)  throws SQLException, ClassNotFoundException{
	String query = "Update JOBS SET invoice = ?, cost = ?, hours = ?, profit = ?, date = ? WHERE RefNum = ?";
	Connection connection = null;
    PreparedStatement preState = null;
    
    try {
    	   connection = InitializeData();
    	   preState = connection.prepareStatement(query);
    	   preState.setDouble(1, invoice);
           preState.setDouble(2, cost);
           preState.setInt(3, hours);
           preState.setDouble(4, profit);
           preState.setDate(5, date);
           preState.setInt(6, refNum);
           preState.executeUpdate();
        }
    finally {
    try { connection.close(); } catch (Exception ex) {}
    try { preState.close(); } catch (Exception ex) {}
}
}
public static boolean queryJob(int jobNum) throws SQLException, ClassNotFoundException {
	String query = "SELECT COUNT(*) AS \"Count\" FROM JOBS WHERE RefNum = ?";
	Connection data = null;
	PreparedStatement preState = null;
	ResultSet rs = null;
	int count = 0;

	try {
		data = InitializeData();
		preState = data.prepareStatement(query);
		preState.setInt(1, jobNum);
		rs = preState.executeQuery();

		while(rs.next())
			count = rs.getInt("Count");

        return (count > 0);
    }
    finally{
	    try{data.close();} catch (Exception ex){}
        try{preState.close();} catch (Exception ex){}
        try{rs.close();} catch (Exception ex){}
    }
}

public static Job initializeJob(int jobNum, User user) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM JOBS WHERE RefNum = ?";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		Job job = new Job();

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			preState.setInt(1, jobNum);
			rs = preState.executeQuery();

			while (rs.next()) {
                job.hours = rs.getInt("hours");
                job.cost = rs.getDouble("cost");
                job.invoice = rs.getDouble("invoice");
                job.date = rs.getDate("date");
                job.profit = rs.getDouble("profit");
                job.jobNum = rs.getInt("RefNum");
        }

            job.currentUser = user;

			return job;
    }
    finally{
        try{data.close();} catch (Exception ex){}
        try{preState.close();} catch (Exception ex){}
        try{rs.close();} catch (Exception ex){}
    }
	}
	public static Job initializeJob(User user) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM JOBS WHERE RefNum = (SELECT MAX(RefNum) FROM JOBS)";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		Job job = new Job();

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			rs = preState.executeQuery();

			while (rs.next()) {
				job.hours = rs.getInt("hours");
				job.cost = rs.getDouble("cost");
				job.invoice = rs.getDouble("invoice");
				job.date = rs.getDate("date");
				job.profit = rs.getDouble("profit");
				job.jobNum = rs.getInt("RefNum");
			}

			job.currentUser = user;
			return job;
		}
		finally{
			try{data.close();} catch (Exception ex){}
			try{preState.close();} catch (Exception ex){}
			try{rs.close();} catch (Exception ex){}
		}
	}

public static boolean queryDates(java.sql.Date date1, java.sql.Date date2, String username)
		throws SQLException, ClassNotFoundException{
	String query = "SELECT COUNT(*) AS \"Count\" FROM JOBS WHERE user = ? AND date >= ? AND date <= ?";

	Connection data = null;
	PreparedStatement preState = null;
	ResultSet rs = null;
	int count = 0;

	try {
		data = InitializeData();
		preState = data.prepareStatement(query);
		preState.setString(1, username);
		preState.setDate(2, date1);
		preState.setDate(3, date2);
		rs = preState.executeQuery();

		while(rs.next())
			count = rs.getInt("Count");

        return (count > 0);
    }
    finally{
        try{data.close();} catch (Exception ex){}
        try{preState.close();} catch (Exception ex){}
        try{rs.close();} catch (Exception ex){}
    }
}

public double queryProfits(java.sql.Date date1, java.sql.Date date2) throws SQLException, ClassNotFoundException{
	String query = "SELECT SUM(profit) as \"Total Profits\" FROM JOBS WHERE user = ? AND date >= ? AND date <= ?";
	Connection data = null;
	PreparedStatement preState = null;
	ResultSet rs = null;
	
	try {
		data = InitializeData();
		preState = data.prepareStatement(query);
		preState.setString(1, currentUser.getUserName());
		preState.setDate(2, date1);
		preState.setDate(3, date2);
		rs = preState.executeQuery();

        return rs.getDouble("Total Profits");
    	}
	
    finally{
        try{data.close();} catch (Exception ex){}
        try{preState.close();} catch (Exception ex){}
        try{rs.close();} catch (Exception ex){}
    	   }
}

public int queryHours(java.sql.Date date1, java.sql.Date date2) throws SQLException, ClassNotFoundException{
	String query = "SELECT SUM(hours) as \"Total Hours\" FROM JOBS WHERE user = ? AND date >= ? AND date <= ?";
	Connection data = null;
	PreparedStatement preState = null;
	ResultSet rs = null;

	try {
		data = InitializeData();
		preState = data.prepareStatement(query);
		preState.setString(1, currentUser.getUserName());
		preState.setDate(2, date1);
		preState.setDate(3, date2);
		rs = preState.executeQuery();

		return rs.getInt("Total Hours");
	}

	finally{
		try{data.close();} catch (Exception ex){}
		try{preState.close();} catch (Exception ex){}
		try{rs.close();} catch (Exception ex){}
	}
}

	public void insertLastJob() throws SQLException, ClassNotFoundException{
		String query = "INSERT INTO JOBLOG (RefNum, invoice, cost, hours, profit, date, user)"
				+ " values(?, ?, ?, ?, ?, ?, ?)";

		Connection connection = InitializeData();
		PreparedStatement preState = connection.prepareStatement(query);

		try {
			preState.setInt(1, jobNum);
			preState.setDouble(2, invoice);
			preState.setDouble(3, cost);
			preState.setInt(4, hours);
			preState.setDouble(5, profit);
			preState.setDate(6, date);
			preState.setString(7, currentUser.getUserName());

			preState.executeUpdate();
		}
		finally{
			try { connection.close(); } catch (Exception ex) {}
			try { preState.close(); } catch (Exception ex) {}
		}
	}

	public static Job initializeLastJob(User user) throws SQLException, ClassNotFoundException{
		String query = "SELECT hours, cost, invoice, date, profit, RefNum FROM JOBLOG WHERE id = (SELECT MAX(id) FROM JOBLOG)";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		Job job = new Job();

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			rs = preState.executeQuery();

			while (rs.next()) {
				job.hours = rs.getInt("hours");
				job.cost = rs.getDouble("cost");
				job.invoice = rs.getDouble("invoice");
				job.date = rs.getDate("date");
				job.profit = rs.getDouble("profit");
				job.jobNum = rs.getInt("RefNum");
			}

			job.currentUser = user;
			return job;
		}
		finally{
			try{data.close();} catch (Exception ex){}
			try{preState.close();} catch (Exception ex){}
			try{rs.close();} catch (Exception ex){}
		}
	}
	public static int refNum() throws SQLException, ClassNotFoundException{
		String query = "SELECT RefNum FROM JOBS WHERE RefNum = (SELECT MAX(RefNum) FROM JOBS)";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		int reference = 0;

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			rs = preState.executeQuery();

			while (rs.next()) {
				reference = rs.getInt("RefNum");
			}
			return reference;
		}
		finally{
			try{data.close();} catch (Exception ex){}
			try{preState.close();} catch (Exception ex){}
			try{rs.close();} catch (Exception ex){}
		}
	}


	public static int lastrefNum() throws SQLException, ClassNotFoundException{
		String query = "SELECT RefNum FROM JOBLOG WHERE id = (SELECT MAX(id) FROM JOBLOG)";
		Connection data = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		int reference = 0;

		try {
			data = InitializeData();
			preState = data.prepareStatement(query);
			rs = preState.executeQuery();

			while (rs.next()) {
				reference = rs.getInt("RefNum");
			}
			return reference;
		}
		finally{
			try{data.close();} catch (Exception ex){}
			try{preState.close();} catch (Exception ex){}
			try{rs.close();} catch (Exception ex){}
		}
	}
}

/*
public void setUserFromData() throws SQLException, ClassNotFoundException{
	    String query = "SELECT USERS.* WHERE USERS.userName = JOBS.user";

        Connection data = null;
        PreparedStatement preState = null;
        ResultSet rs = null;

        try {
            data = InitializeData();
            preState = data.prepareStatement(query);
            rs = preState.executeQuery();

            while (rs.next()) {
                currentUser.setUserName(rs.getString("userName"));
                currentUser.setPassword(rs.getString("password"));
                currentUser.setFirstName(rs.getString("firstName"));
                currentUser.setLastName(rs.getString("userName"));
            }
        }
        finally{
            try{data.close();} catch (Exception ex){}
            try{preState.close();} catch (Exception ex){}
            try{rs.close();} catch (Exception ex){}
        }
    }
}
*/

