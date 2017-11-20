package models;

public class Price {
	private double currentPrice;
	private double lowestPrice;
	private double highestPrice;
	
	public Price(double price, double lowestPrice, double highestPrice) {
		this.currentPrice = price;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
	}
	
	public double getCurrentPrice() {
		return this.currentPrice;
	}
	
	public double getLowestPrice() {
		return this.lowestPrice;
	}
	
	public double getHighestPrice() {
		return this.highestPrice;
	}
}