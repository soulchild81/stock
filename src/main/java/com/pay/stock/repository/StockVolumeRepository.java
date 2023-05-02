package com.pay.stock.repository;

import com.pay.stock.entity.StockView;
import com.pay.stock.entity.StockVolume;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockVolumeRepository extends PagingAndSortingRepository<StockVolume, Long> {
}
