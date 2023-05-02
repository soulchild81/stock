package com.pay.stock.api.dao;

import com.pay.stock.entity.Stock;
import com.pay.stock.entity.StockIncrease;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StockIncreaseDao {
    public void addStockIncrease(StockIncrease increase);

    public Page<StockIncrease> getStockIncreaseList(int page , int size);
}
