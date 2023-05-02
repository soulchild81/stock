package com.pay.stock.model;

import com.pay.stock.entity.StockIncrease;
import com.pay.stock.entity.StockView;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Builder
@Data
public class ChartHome {
    private List<StockDto> view_chart_list;
    private List<StockDto> increase_chart_list;
    private List<StockDto> down_chart_list;
    private List<StockDto> volume_chart_list;
}
