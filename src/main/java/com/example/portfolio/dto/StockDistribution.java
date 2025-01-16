package com.example.portfolio.dto;


public class StockDistribution {
    private String stockName;
    private double percentage;
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public StockDistribution(String stockName, double percentage) {
		super();
		this.stockName = stockName;
		this.percentage = percentage;
	}
	public StockDistribution() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Constructor, getters, and setters

}
