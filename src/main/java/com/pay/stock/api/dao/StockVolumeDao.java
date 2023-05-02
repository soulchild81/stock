package com.pay.stock.api.dao;

import com.pay.stock.entity.StockIncrease;
import com.pay.stock.entity.StockVolume;
import org.springframework.data.domain.Page;

public interface StockVolumeDao {
    public void addStockVolume(StockVolume volume);

    public Page<StockVolume> getStockVolumeList(int page , int size);
}
