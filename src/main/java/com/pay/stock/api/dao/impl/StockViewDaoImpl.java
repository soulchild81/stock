package com.pay.stock.api.dao.impl;

import com.pay.stock.api.dao.StockViewDao;
import com.pay.stock.common.Constants;
import com.pay.stock.entity.StockLower;
import com.pay.stock.entity.StockView;
import com.pay.stock.repository.StockViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockViewDaoImpl implements StockViewDao {
    private final StockViewRepository stockViewRepository;

    @Override
    public void addStockView(StockView view){
        stockViewRepository.save(view);
    }

    @Override
    public Page<StockView> getStockViewList(int page , int size){
        PageRequest req = PageRequest.of(page-1, size , Sort.by(Constants.SORT_VIEW_COUNT).ascending());
        return stockViewRepository.findAll(req);
    }
}
