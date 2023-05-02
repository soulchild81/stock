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
public class StockVolume extends BaseTimeEntity{
    @EmbeddedId
    private StockKey stockKey;

    @Column(name = "many_rank")
    private int manyRank;

    @Column(name = "fluctuate")
    private int chartFluctuate;
}