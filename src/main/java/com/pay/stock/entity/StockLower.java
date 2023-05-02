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
public class StockLower extends BaseTimeEntity{
    @EmbeddedId
    private StockKey stockKey;

    @Column(name = "down_rank")
    private int downRank;

    @Column(name = "fluctuate")
    private int chartFluctuate;
}