package com.pay.stock.repository;

import com.pay.stock.entity.StockIncrease;
import com.pay.stock.entity.StockView;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockViewRepository  extends PagingAndSortingRepository<StockView, Long> {

}
