package com.pay.stock.api.dao.impl;

import com.pay.stock.api.dao.StockLowerDao;
import com.pay.stock.common.Constants;
import com.pay.stock.entity.StockIncrease;
import com.pay.stock.entity.StockLower;
import com.pay.stock.repository.StockLowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockLowerDaoImpl implements StockLowerDao {

    private final StockLowerRepository stockLowerRepository;

    @Override
    public void addStockLower(StockLower lower){
        stockLowerRepository.save(lower);
    }

    @Override
    public Page<StockLower> getStockLowerList(int page , int size){
        PageRequest req = PageRequest.of(page-1, size , Sort.by(Constants.SORT_DOWN_RANK).ascending());
        return stockLowerRepository.findAll(req);
    }
}
