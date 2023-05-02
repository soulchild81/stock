package com.pay.stock.api.dao;

import com.pay.stock.entity.Stock;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StockDao {
    public void addStock(Stock stocks);

    public Page<Stock> getStockList(int page , int size);

    public Stock getStocks(Long stockId);

    public List<Stock> getStockListIds(List<Long> stockIds);
}
