package com.pay.stock.api.dao.impl;

import com.pay.stock.api.dao.StockVolumeDao;
import com.pay.stock.common.Constants;
import com.pay.stock.entity.StockView;
import com.pay.stock.entity.StockVolume;
import com.pay.stock.repository.StockVolumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockVolumeDaoImpl implements StockVolumeDao {
    private final StockVolumeRepository stockVolumeRepository;

    @Override
    public void addStockVolume(StockVolume volume){
        stockVolumeRepository.save(volume);
    }

    @Override
    public Page<StockVolume> getStockVolumeList(int page , int size){
        PageRequest req = PageRequest.of(page-1, size , Sort.by(Constants.SORT_MANY_RANK).ascending());
        return stockVolumeRepository.findAll(req);
    }
}
