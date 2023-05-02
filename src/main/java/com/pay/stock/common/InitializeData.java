package com.pay.stock.common;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.pay.stock.api.service.stock.StockService;
import com.pay.stock.model.StockDto;
import com.pay.stock.utils.StockUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializeData {
    private final StockService stocksService;


    @PostConstruct
    public void init(){
        try{
            System.out.println("################ Initialize DATA SET");
            //csv 파일 read
            CSVReader reader = new CSVReader(new FileReader(Constants.FILE_PATH));
            List<String[]> lines = reader.readAll();

            //header 컬럼 제거
            lines.remove(0);
            lines.forEach(line -> {
                System.out.println(line[0]+":"+line[1]+":"+line[2]+":"+line[3]);
                StockDto dto = StockDto.builder()
                        .stock_id(Long.parseLong(line[0]))
                        .stock_code(line[1])
                        .stock_name(line[2])
                        .stock_price(Long.parseLong(line[3])).build();
                stocksService.addStock(dto);
            });
            System.out.println("################ DATA LOAD FINISH");

            System.out.println("################ TEST DATA SET");
            List<StockDto> list = stocksService.getStockList(0 , lines.size());
            stocksService.setTestData(list);
            System.out.println("################ TEST DATA SET FINISH");
        }catch(CsvException | IOException csv){
            csv.printStackTrace();
        }
    }
}
