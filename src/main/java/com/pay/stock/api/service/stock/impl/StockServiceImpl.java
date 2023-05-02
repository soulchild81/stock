package com.pay.stock.api.service.stock.impl;

import com.pay.stock.api.dao.*;
import com.pay.stock.api.service.stock.StockService;
import com.pay.stock.common.Constants;
import com.pay.stock.entity.*;
import com.pay.stock.entity.entityKey.StockKey;
import com.pay.stock.model.ChartHome;
import com.pay.stock.model.ChartInfo;
import com.pay.stock.model.StockChart;
import com.pay.stock.model.StockDto;
import com.pay.stock.utils.StockUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ModelMapper mapper;
    private final StockDao stockDao;
    private final StockIncreaseDao stockIncreaseDao;
    private final StockLowerDao stockLowerDao;
    private final StockViewDao stockViewDao;
    private final StockVolumeDao stockVolumeDao;

    @Override
    public ChartHome getChartMain(int page , int size){
        return ChartHome.builder()
                .increase_chart_list(this.getStockListType(page , size , Constants.STOCK_LIST_TYPE.UP.getDesc()))
                .down_chart_list(this.getStockListType(page , size , Constants.STOCK_LIST_TYPE.DOWN.getDesc()))
                .view_chart_list(this.getStockListType(page , size , Constants.STOCK_LIST_TYPE.VIEW.getDesc()))
                .volume_chart_list(this.getStockListType(page , size , Constants.STOCK_LIST_TYPE.VOLUME.getDesc()))
                .build();
    }

    @Override
    public void addStock(StockDto stocksDto){
        StockKey key = StockKey.builder().stockId(stocksDto.getStock_id()).stockCode(stocksDto.getStock_code()).build();
        Stock stocks = Stock.builder().stockKey(key).stockName(stocksDto.getStock_name()).stockPrice(new BigDecimal(stocksDto.getStock_price())).build();
        stockDao.addStock(stocks);
    }

    @Override
    public List<StockDto> getStockList(int page , int size){
        Page<Stock> list = stockDao.getStockList(page , size);
        return list.getContent().stream().map(s -> mapper.map(s , StockDto.class)).collect(Collectors.toList());
    }

    @Override
    public StockDto getStock(Long stockId){
        Stock stock = stockDao.getStocks(stockId);
        return mapper.map(stock , StockDto.class);
    }

    @Override
    public void setTestData(List<StockDto> list){
        List<Integer> arr = new ArrayList<>();
        list.forEach(s -> arr.add(Integer.parseInt(s.getStock_id().toString())));

        Collections.shuffle(arr);
        list.forEach(s -> {StockKey key = StockKey.builder().stockId(s.getStock_id()).stockCode(s.getStock_code()).build();
                           stockIncreaseDao.addStockIncrease(StockIncrease.builder()
                                   .stockKey(key)
                                   .upRank(arr.get(Integer.parseInt(s.getStock_id().toString())-1))
                                   .chartFluctuate(StockUtils.randomFluctuateGenerate()).build());});

        Collections.shuffle(arr);
        list.forEach(s -> {StockKey key = StockKey.builder().stockId(s.getStock_id()).stockCode(s.getStock_code()).build();
                           stockLowerDao.addStockLower(StockLower.builder()
                                   .stockKey(key)
                                   .downRank(arr.get(Integer.parseInt(s.getStock_id().toString())-1))
                                   .chartFluctuate(StockUtils.randomFluctuateGenerate()).build());});

        Collections.shuffle(arr);
        list.forEach(s -> {StockKey key = StockKey.builder().stockId(s.getStock_id()).stockCode(s.getStock_code()).build();
                           stockViewDao.addStockView(StockView.builder()
                                   .stockKey(key)
                                   .viewCount(arr.get(Integer.parseInt(s.getStock_id().toString())-1))
                                   .chartFluctuate(StockUtils.randomFluctuateGenerate()).build());});

        Collections.shuffle(arr);
        list.forEach(s -> {StockKey key = StockKey.builder().stockId(s.getStock_id()).stockCode(s.getStock_code()).build();
                           stockVolumeDao.addStockVolume(StockVolume.builder()
                                   .stockKey(key)
                                   .manyRank(arr.get(Integer.parseInt(s.getStock_id().toString())-1))
                                   .chartFluctuate(StockUtils.randomFluctuateGenerate()).build());});
    }

    @Override
    public List<StockDto> getStockListType(int page , int size , String type){
        List<Long> ids = new ArrayList<>();
        Map<Long , Integer> fluctuate = new HashMap<Long , Integer>();
        Map<Long , Integer> rank = new HashMap<Long , Integer>();

        //많이 본 종목 조회
        if(type.equals(Constants.STOCK_LIST_TYPE.VIEW.getDesc())) {
            Page<StockView> list = stockViewDao.getStockViewList(page, size);
            list.getContent().forEach(s -> {ids.add(s.getStockKey().getStockId());
                                            fluctuate.put(s.getStockKey().getStockId() , s.getChartFluctuate());
                                            rank.put(s.getStockKey().getStockId() , s.getViewCount());});
        //많이 오른 종목 조회
        }else if(type.equals(Constants.STOCK_LIST_TYPE.UP.getDesc())){
            Page<StockIncrease> list = stockIncreaseDao.getStockIncreaseList(page , size);
            list.getContent().forEach(s -> {ids.add(s.getStockKey().getStockId());
                                            fluctuate.put(s.getStockKey().getStockId() , s.getChartFluctuate());
                                            rank.put(s.getStockKey().getStockId() , s.getUpRank());});
        //많이 내린 종목 조회
        }else if(type.equals(Constants.STOCK_LIST_TYPE.DOWN.getDesc())){
            Page<StockLower> list = stockLowerDao.getStockLowerList(page , size);
            list.getContent().forEach(s -> {ids.add(s.getStockKey().getStockId());
                                            fluctuate.put(s.getStockKey().getStockId() , s.getChartFluctuate());
                                            rank.put(s.getStockKey().getStockId() , s.getDownRank());});
        //많이 거래된 종목 조회
        }else if(type.equals(Constants.STOCK_LIST_TYPE.VOLUME.getDesc())){
            Page<StockVolume> list = stockVolumeDao.getStockVolumeList(page , size);
            list.getContent().forEach(s -> {ids.add(s.getStockKey().getStockId());
                                            fluctuate.put(s.getStockKey().getStockId() , s.getChartFluctuate());
                                            rank.put(s.getStockKey().getStockId() , s.getManyRank());});
        }

        List<StockDto> stockList = stockDao.getStockListIds(ids).stream().map(s -> mapper.map(s , StockDto.class)).collect(Collectors.toList());
        StockUtils.sortIdList(ids , stockList);

        stockList.forEach(s -> {s.setChart_fluctuate(fluctuate.get(s.getStock_id()));
                                s.setRank(rank.get(s.getStock_id()));});

        return stockList;
    }

    @Override
    public StockChart getStockChart(int page , int size , String type){
        List<StockDto> list = this.getStockListType(page , size , type);
        return StockChart.builder().stock_chart_list(list).chart_info(ChartInfo.builder().chart_type(type).build()).build();
    }
}
