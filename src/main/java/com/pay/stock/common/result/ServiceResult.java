package com.pay.stock.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper=false)
@Data
public class ServiceResult<T> extends CommonResponse<T> implements Serializable {
    public ServiceResult() {
        super();
    }

    public ServiceResult(T result) {
        super();
        this.result = result;
    }

    @JsonIgnore
    private T result;
}