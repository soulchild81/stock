package com.pay.stock.repository;

import com.pay.stock.entity.StockIncrease;
import com.pay.stock.entity.StockLower;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockLowerRepository extends PagingAndSortingRepository<StockLower, Long> {

}
