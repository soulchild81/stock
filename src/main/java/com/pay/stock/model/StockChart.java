package com.pay.stock.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StockChart {
    private List<StockDto> stock_chart_list;
    private ChartInfo chart_info;
}
