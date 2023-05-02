package com.pay.stock.entity.entityKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Data
public class StockKey implements Serializable {

    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "stock_code")
    private String stockCode;
}
