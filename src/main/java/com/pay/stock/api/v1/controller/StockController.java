package com.pay.stock.api.v1.controller;

import com.pay.stock.api.service.stock.StockService;
import com.pay.stock.common.Constants;
import com.pay.stock.common.Exception.CommonException;
import com.pay.stock.common.ExceptionMsg;
import com.pay.stock.common.annotation.StockApi;
import com.pay.stock.common.result.ServiceResult;
import com.pay.stock.model.ChartHome;
import com.pay.stock.model.StockChart;
import com.pay.stock.model.StockDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags="종목", value="종목 API")
@RequestMapping("/api/v1/stock")
@Slf4j
@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stocksService;

    @PostMapping("/init")
    @ApiOperation(value="테스트 데이터 세팅", notes="테스트 데이터 세팅")
    @StockApi
    public ServiceResult<?> setData(){
        List<StockDto> list = stocksService.getStockList(0 , 120);
        stocksService.setTestData(list);
        return new ServiceResult<>();
    }

    @GetMapping("/chart/{type}/list")
    @ApiOperation(value="종목 리스트 조회", notes="종목 리스트 조회")
    @StockApi
    public ServiceResult<StockChart> getStockChartList(@ApiParam(value = "요청 페이지", example = "1")  @Min(1) @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                       @ApiParam(value = "요청 사이즈", example = "20") @Min(1) @Max(100)@RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                       @ApiParam(value = "리스트 타입", example = "view,up,down,volume") @NotBlank @PathVariable(value = "type") String type){

        if((page * size) > 100){
            throw new CommonException(ExceptionMsg.RESULT_CODE.RANGE_EXCEED);
        }

        StockChart chart = stocksService.getStockChart(page , size , type);

        return new ServiceResult<>(chart);
    }

    @GetMapping("/chart/home")
    @ApiOperation(value="홈", notes="차트 홈 조회")
    @StockApi
    public ServiceResult<ChartHome> getChartHome(@ApiParam(value="요청 페이지", example = "1") @Min(1) @Min(1)@RequestParam(value="page", required=false , defaultValue="1") int page,
                                                 @ApiParam(value="요청 사이즈", example = "5") @Min(1) @Max(5)@RequestParam(value="size", required=false , defaultValue="5") int size){
        ChartHome home = stocksService.getChartMain(page , size);
        return new ServiceResult<>(home);
    }
}
