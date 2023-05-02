package com.pay.stock.common;

public class ExceptionMsg {
    public static enum RESULT_CODE {
        SUCCESS(0, "성공하였습니다."),
        INVALID_PARAMS(100, "잘못된 파라미터입니다."),
        INVALID_ACCESS(101, "잘못된 접근입니다."),
        NONE_RESULT(102, "결과가 존재하지 않습니다."),
        RANGE_EXCEED(103, "조회범위를 초과하였습니다."),
        NOT_LOGGED(200, "로그인 후 이용할 수 있습니다."),
        CONNECTION_ERROR(300,"통신에 실패 했습니다."),
        UNKNOWN_ERROR(1000 , "기타 오류가 발생하였습니다.");

        private int code;
        private String msg;

        private RESULT_CODE(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
        public int getCode() {
            return code;
        }
        public String getMsg() {return msg;
        }
    };
}
