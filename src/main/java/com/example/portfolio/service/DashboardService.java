package com.example.portfolio.service;

import com.example.portfolio.model.Stock;
import com.example.portfolio.repository.StockRepository;
import com.example.portfolio.dto.StockDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private StockRepository stockRepository;

    public BigDecimal calculateTotalPortfolioValue() {
        List<Stock> stocks = stockRepository.findAll();
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Stock stock : stocks) {
            BigDecimal stockValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity()));
            totalValue = totalValue.add(stockValue);
        }
        return totalValue;
    }

    public Stock calculateTopPerformingStock() {
        List<Stock> stocks = stockRepository.findAll();
        Stock topStock = null;
        BigDecimal highestGain = BigDecimal.valueOf(-Double.MAX_VALUE);

        for (Stock stock : stocks) {
            if (stock.getBuyPrice() == null || stock.getBuyPrice().compareTo(BigDecimal.ZERO) <= 0) {
                continue; // Skip stocks with invalid buy price
            }

            BigDecimal priceDifference = stock.getCurrentPrice().subtract(stock.getBuyPrice());
            BigDecimal percentageGain = priceDifference.divide(stock.getBuyPrice(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

            if (percentageGain.compareTo(highestGain) > 0) {
               highestGain = percentageGain;
                topStock = stock;
           }
        }
       return topStock;
    }
    


    public List<StockDistribution> calculatePortfolioDistribution() {
        List<Stock> stocks = stockRepository.findAll();
        BigDecimal totalPortfolioValue = calculateTotalPortfolioValue();
        List<StockDistribution> distributionList = new ArrayList<>();

        for (Stock stock : stocks) {
            BigDecimal stockValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity()));
            BigDecimal stockPercentage = stockValue.divide(totalPortfolioValue, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
            distributionList.add(new StockDistribution(stock.getName(), stockPercentage.doubleValue()));
        }
        return distributionList;
    }
}
