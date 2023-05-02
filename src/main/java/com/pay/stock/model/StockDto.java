package com.pay.stock.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StockDto{
    private Long stock_id;
    private String stock_code;
    private String stock_name;
    private Long stock_price;
    private int rank;
    private int chart_fluctuate;
}
