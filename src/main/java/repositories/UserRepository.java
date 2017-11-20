package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.PreparedStatement;

import models.Price;
import models.User;

public class UserRepository {
	public UserRepository() {
		
	}
	private Connection connect() {
		String url = "jdbc:mysql://localhost/MazuMah?user=root&password=root";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url);
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return connection;
	}
	
	public User findByUsername(String username) {
		User user = null;
		String sql = "SELECT * FROM User WHERE username='" + username + "'";
		try (Connection conn = this.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)){  
	            // loop through the result set
	            while (rs.next()) {
	            		String userId = rs.getString("id");
					String password = rs.getString("password");
					user = new User(userId, username, password);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return user;
	}
	
	public User findById(String id) {
		User user = null;
		Double notifyP = -1.0;
		String sql = "SELECT * FROM User WHERE id='" + id + "'";
		try (Connection conn = this.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){  
	            // loop through the result set
	            while (rs.next()) {
	            		String userId = rs.getString("id");
	            		String username = rs.getString("username");
					String password = rs.getString("password");
					notifyP = rs.getDouble("notifyPrice");
					user = new User(userId, username, password, notifyP);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return user;
	}
	
	

	public int comparePrice(String id) {
		double notifyPrice = findById(id).getNotifyPrice();
		double currentPrice = getPrice().getCurrentPrice();
		int notify = 0;
		if (notifyPrice != -1) {
			if (currentPrice >= notifyPrice) {
				notify = 1;
				 updateNotifyPriceById(Integer.parseInt(id), -1.0);
			} else {
				notify = 0;
			}
		}
		return notify;		
	}
	
	public Price getPrice() {
		Double currentPrice = 0.0;
		Double highestPrice = 0.0;
		Double lowestPrice = 0.0;
		Price price = null;
		String sql = "SELECT * FROM PriceData WHERE id=(SELECT max(id) FROM PriceData)";
			try (Connection conn = this.connect();
		              Statement stmt  = conn.createStatement();
		              ResultSet rs    = stmt.executeQuery(sql)){
		             // loop through the result set
		             while (rs.next()) {
		            	 //set notify price
		            	currentPrice = rs.getDouble("current");
		            	highestPrice = rs.getDouble("highest");
		            	lowestPrice = rs.getDouble("lowest");
		            	price = new Price(currentPrice, highestPrice, lowestPrice);
		             }
		         } catch (SQLException e) {
		             System.out.println(e.getMessage());
		         }
			return price;
	}
	
	 public void updateNotifyPriceById(int id, double price) {

		 	String sql = "UPDATE User SET notifyprice='" + price + "' WHERE id='" + id + "'";
		 	
		 	Connection conn = this.connect();
		 	try {
		 		 Statement stmt  = conn.createStatement();
		 		 stmt.executeUpdate(sql);
	         } catch (SQLException e) {
	             System.out.println(e.getMessage());
	         }
		 
		 }

	
	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList<>();
		String sql = "SELECT * FROM User";	        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){  
            // loop through the result set
            while (rs.next()) {
            		String userId = rs.getString("id");
            		String username = rs.getString("username");
				String password = rs.getString("password");
				allUsers.add(new User(userId, username, password));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return allUsers;
	}
	
	public void saveUser(User user) {
		String sql = "INSERT INTO User(username,password) VALUES(?,?)";
        try (Connection connection = this.connect();
                PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql)) {
        		pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	

	
}
