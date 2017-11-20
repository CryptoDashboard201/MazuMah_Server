package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBC {
	
//For testing purposes save user
public void saveUser(int id, String username, String password, double notifyPrice) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Inside save price");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "INSERT INTO User(id, username, password, notifyprice) VALUES(?,?,?,?)";
		
		
        try (
        	Connection connection = this.connect();
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	pstmt.setString(2, username);
        	pstmt.setString(3, password);
        	pstmt.setDouble(4, notifyPrice);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	

	 
	 public double findById(int id) {
		 Double notifyPrice = 0.0;
	 	String sql = "SELECT * FROM User WHERE id='" + id + "'";
	 	try (Connection conn = this.connect();
	              Statement stmt  = conn.createStatement();
	              ResultSet rs    = stmt.executeQuery(sql)){
	             // loop through the result set
	             while (rs.next()) {
	            	 //set notify price
	            	notifyPrice = rs.getDouble("notifyPrice");
	             }
	         } catch (SQLException e) {
	             System.out.println(e.getMessage());
	         }
	 	return notifyPrice;
	 }
	 
	public void savePrice(double currentPrice, double highestPrice, double lowestPrice) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Inside save price");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "INSERT INTO PriceData(current, highest, lowest) VALUES(?,?,?)";
		
		
        try (
        	Connection connection = this.connect();
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql)) {
        	pstmt.setDouble(1, currentPrice);
        	pstmt.setDouble(2, highestPrice);
        	pstmt.setDouble(3, lowestPrice);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void saveLink(double x, double y) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "INSERT INTO BitcoinOwnerMetric(x,y) VALUES(?,?)";
		
        try (
        		
        	Connection connection = this.connect();
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql)) {
        	pstmt.setDouble(1, x);
        	pstmt.setDouble(2, y);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	private Connection connect() {
		String urlMySQL = "jdbc:mysql://localhost/mazumah?user=root&password=root";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(urlMySQL);
		
			
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return connection;
	}
}
