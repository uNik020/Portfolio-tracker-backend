package com.example.portfolio.service;

import com.example.portfolio.model.Stock;
import java.util.List;

public interface StockService {
    Stock addStock(Stock stock);
    Stock updateStock(Long id, Stock stock);
    void deleteStock(Long id);
    List<Stock> getAllStocks();
    double calculatePortfolioValue();
}
