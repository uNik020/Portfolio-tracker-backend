package com.example.portfolio.controller;


import com.example.portfolio.service.*;
import com.example.portfolio.dto.*;
import com.example.portfolio.model.Stock;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins ="http://localhost:3000")

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/api/dashboard")
    public DashboardStats getDashboardStats() {
        BigDecimal totalPortfolioValue = dashboardService.calculateTotalPortfolioValue();
        Stock topStock = dashboardService.calculateTopPerformingStock();
        List<StockDistribution> distribution = dashboardService.calculatePortfolioDistribution();
        
        return new DashboardStats(totalPortfolioValue, topStock, distribution);
    }
}

