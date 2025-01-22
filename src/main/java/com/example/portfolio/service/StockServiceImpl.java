package com.example.portfolio.service;

import com.example.portfolio.model.Stock;
import com.example.portfolio.repository.StockRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Long id, Stock stock) {
        Stock existingStock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        existingStock.setName(stock.getName());
        existingStock.setTicker(stock.getTicker());
        existingStock.setBuyPrice(stock.getBuyPrice()); // Updated from `setPrice`
        existingStock.setCurrentPrice(stock.getCurrentPrice());
        existingStock.setQuantity(stock.getQuantity());
        return stockRepository.save(existingStock);
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public double calculatePortfolioValue() {
        return stockRepository.findAll()
                .stream()
                .mapToDouble(stock -> stock.getBuyPrice().doubleValue() * stock.getQuantity()) // Uses `buyPrice`
                .sum();
    }

    /**
     * Fetch real-time stock price from Alpha Vantage API.
     *
     * @param ticker the stock ticker symbol
     * @return the current price as a BigDecimal
     */
    private BigDecimal fetchRealTimePrice(String ticker) {
        //String apiKey = "IOW2CU52KSZ5IUA9"; // Replace with your actual API key
	String apiKey = "NJ9DX4CNHL7AZ5Z4";
	//String apiKey = "30CO7P3MM54Y0GK3";
	//String apiKey = "A7ABH0K2MWHX9I78";
	//String apiKey = "TNCP2RGNPOAGN9ST";        



	String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            System.out.println("API Response: " + response);

            Map<String, String> globalQuote = (Map<String, String>) response.get("Global Quote");
            if (globalQuote == null) {
                throw new RuntimeException("Global Quote not found in response.");
            }

            String price = globalQuote.get("05. price");
            if (price == null || price.isEmpty()) {
                throw new RuntimeException("Price not found in Global Quote.");
            }

            return new BigDecimal(price);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch stock price for ticker: " + ticker, e);
        }
    }

    /**
     * Scheduled task to update stock prices.
     * Runs every 60 seconds to fetch and update real-time prices.
     */
    @Scheduled(fixedRate = 600000) // Runs every 10 minutes
    public void updateStockPrices() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            try {
                BigDecimal updatedPrice = fetchRealTimePrice(stock.getTicker());
                stock.setCurrentPrice(updatedPrice); // Update `currentPrice`
                stockRepository.save(stock);
            } catch (Exception e) {
                System.err.println("Error updating price for ticker: " + stock.getTicker() + " - " + e.getMessage());
            }
        }
    }
}
