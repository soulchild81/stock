package com.pay.stock.repository;

import com.pay.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends PagingAndSortingRepository<Stock, Long>{
    public List<Stock> findByOrderByStockKeyAsc();

    public Stock findByStockKey_StockId(Long stockId);

    Page<Stock> findAllBy(Pageable page);

    public List<Stock> findByStockKey_StockIdIn(@Param("stockids") List<Long> stockIds);
}
