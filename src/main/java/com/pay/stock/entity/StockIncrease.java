package com.pay.stock.entity;

import com.pay.stock.entity.entityKey.StockKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class StockIncrease extends BaseTimeEntity{
    @EmbeddedId
    private StockKey stockKey;

    @Column(name = "up_rank")
    private int upRank;

    @Column(name = "fluctuate")
    private int chartFluctuate;
}
