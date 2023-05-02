package com.pay.stock.repository;

import com.pay.stock.entity.Stock;
import com.pay.stock.entity.StockIncrease;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockIncreaseRepository extends PagingAndSortingRepository<StockIncrease, Long> {

}
