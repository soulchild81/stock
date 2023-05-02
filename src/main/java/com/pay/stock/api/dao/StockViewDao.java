package com.pay.stock.api.dao;

import com.pay.stock.entity.StockLower;
import com.pay.stock.entity.StockView;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;


public interface StockViewDao {
    public void addStockView(StockView view);

    public Page<StockView> getStockViewList(int page , int size);
}
