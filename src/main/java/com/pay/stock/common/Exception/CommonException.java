package com.pay.stock.common.Exception;

import com.pay.stock.common.ExceptionMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonException extends RuntimeException {

    public CommonException() {
        super();
    }

    public CommonException(ExceptionMsg.RESULT_CODE resultCode) {
        super();
        this.result_code = resultCode;
    }

    private ExceptionMsg.RESULT_CODE result_code;
}
