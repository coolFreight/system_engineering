package com.examples.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockPriceUpdate {
    private String dateTime;
    private String stockCode;
    private BigDecimal stockPrice;
    private BigDecimal stockPriceChange;


    public StockPriceUpdate(String stockCode, LocalDateTime dateTime, BigDecimal stockPrice, BigDecimal stockPriceChange) {
        this.dateTime = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        this.stockCode = stockCode;
        this.stockPrice = stockPrice;
        this.stockPriceChange = stockPriceChange;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getStockPriceChange() {
        return stockPriceChange;
    }

    public void setStockPriceChange(BigDecimal stockPriceChange) {
        this.stockPriceChange = stockPriceChange;
    }
}
