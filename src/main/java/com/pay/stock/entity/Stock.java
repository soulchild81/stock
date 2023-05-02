package com.pay.stock.entity;

import com.pay.stock.entity.entityKey.StockKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Stock extends BaseTimeEntity{
    @EmbeddedId
    private StockKey stockKey;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "stock_price")
    private BigDecimal stockPrice;
}
