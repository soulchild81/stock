package com.pay.stock.api.service.stock;

import com.pay.stock.model.ChartHome;
import com.pay.stock.model.StockChart;
import com.pay.stock.model.StockDto;

import java.util.List;

public interface StockService {

    //메인 홈화면 조회
    public ChartHome getChartMain(int page , int size);

    //종목 개별 등록
    public void addStock(StockDto stocksDto);

    //종목 리스트 조회
    public List<StockDto> getStockList(int page , int size);

    //종목 차트 정보 조회
    public StockChart getStockChart(int page , int size , String type);

    //종목 개별 조회
    public StockDto getStock(Long stockId);

    //테스트 데이터 생성 및 변경
    public void setTestData(List<StockDto> list);

    //많이 본 , 오른 , 내린 , 거래된 종목 차트 조회
    public List<StockDto> getStockListType(int page , int size , String type);

}
