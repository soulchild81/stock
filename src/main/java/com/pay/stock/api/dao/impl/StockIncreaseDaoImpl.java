package com.pay.stock.api.dao.impl;

import com.pay.stock.api.dao.StockIncreaseDao;
import com.pay.stock.common.Constants;
import com.pay.stock.entity.Stock;
import com.pay.stock.entity.StockIncrease;
import com.pay.stock.repository.StockIncreaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockIncreaseDaoImpl implements StockIncreaseDao {

    private final StockIncreaseRepository stockIncreaseRepository;

    @Override
    public void addStockIncrease(StockIncrease increase){
        stockIncreaseRepository.save(increase);
    }

    @Override
    public Page<StockIncrease> getStockIncreaseList(int page , int size){
        PageRequest req = PageRequest.of(page-1, size , Sort.by(Constants.SORT_UP_RANK).ascending());
        return stockIncreaseRepository.findAll(req);
    }
}
