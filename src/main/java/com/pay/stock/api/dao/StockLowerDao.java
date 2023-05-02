package com.pay.stock.api.dao;

import com.pay.stock.entity.StockIncrease;
import com.pay.stock.entity.StockLower;
import org.springframework.data.domain.Page;

public interface StockLowerDao {
    public void addStockLower(StockLower lower);

    public Page<StockLower> getStockLowerList(int page , int size);
}
