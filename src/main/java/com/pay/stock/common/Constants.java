package com.pay.stock.common;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static String STOCK_ID = "stockKey.stockId";
    public static String STOCK_CODE = "stockKey.stockCode";

    public static String SORT_UP_RANK = "upRank";
    public static String SORT_DOWN_RANK = "downRank";
    public static String SORT_VIEW_COUNT = "viewCount";
    public static String SORT_MANY_RANK = "manyRank";

    public static String FILE_PATH = "src/main/resources/file/SampleData.csv";

    public enum STOCK_LIST_TYPE {
        VIEW("view"),
        UP("up"),
        DOWN("down"),
        VOLUME("volume");

        private final String desc;
        STOCK_LIST_TYPE(String desc) {this.desc = desc;}
        public String getDesc() {
            return desc;
        }
    }
}
