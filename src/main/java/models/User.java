package models;

public class User {
	private final String id;
	private final String username;
	private final String password;
	private final double notifyPrice;
	
	public User(String username, String password) {
		this(null, username, password);
	}
	
	public User(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.notifyPrice = -1.0;
	}
	
	public User(String id, String username, String password, double price) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.notifyPrice = price;
	}
	
	
	public double getNotifyPrice() {
		return notifyPrice;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
