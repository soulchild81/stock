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
public class StockView extends BaseTimeEntity{
    @EmbeddedId
    private StockKey stockKey;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "fluctuate")
    private int chartFluctuate;
}
