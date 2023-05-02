package com.pay.stock.api.dao.impl;

import com.pay.stock.api.dao.StockDao;
import com.pay.stock.entity.Stock;
import com.pay.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockDaoImpl implements StockDao {

    private final StockRepository stocksRepository;

    @Override
    public void addStock(Stock stock){
        try{
            stocksRepository.save(stock);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Page<Stock> getStockList(int page , int size){
        PageRequest req = PageRequest.of(page, size , Sort.by("stockKey.stockId").ascending());
        return stocksRepository.findAll(req);
    }

    @Override
    public Stock getStocks(Long stockId){
        return stocksRepository.findByStockKey_StockId(stockId);
    }

    @Override
    public List<Stock> getStockListIds(List<Long> stockIds){
        return stocksRepository.findByStockKey_StockIdIn(stockIds);
    }
}
