package com.example.portfolio.dto;


import com.example.portfolio.model.Stock;

import java.math.BigDecimal;
import java.util.List;

public class DashboardStats {
    private BigDecimal totalPortfolioValue;
    private Stock topPerformingStock;
    private List<StockDistribution> portfolioDistribution;
	public BigDecimal getTotalPortfolioValue() {
		return totalPortfolioValue;
	}
	public void setTotalPortfolioValue(BigDecimal totalPortfolioValue) {
		this.totalPortfolioValue = totalPortfolioValue;
	}
	public Stock getTopPerformingStock() {
		return topPerformingStock;
	}
	public void setTopPerformingStock(Stock topPerformingStock) {
		this.topPerformingStock = topPerformingStock;
	}
	public List<StockDistribution> getPortfolioDistribution() {
		return portfolioDistribution;
	}
	public void setPortfolioDistribution(List<StockDistribution> portfolioDistribution) {
		this.portfolioDistribution = portfolioDistribution;
	}
	public DashboardStats(BigDecimal totalPortfolioValue, Stock topPerformingStock,
			List<StockDistribution> portfolioDistribution) {
		super();
		this.totalPortfolioValue = totalPortfolioValue;
		this.topPerformingStock = topPerformingStock;
		this.portfolioDistribution = portfolioDistribution;
	}
	public DashboardStats() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    // Constructor, getters, and setters
}
