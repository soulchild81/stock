package com.pay.stock.utils;

import com.pay.stock.model.StockDto;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StockUtils {
    public static int[] getIntArray(int size){
        int[] intArr = new int[size];
        for(int i=1;i <= intArr.length;i++){
            intArr[i-1] = i;
        }

        Random random = new Random();
        int rank = random.nextInt(size);
        return Arrays.stream(intArr).filter(i -> i != rank).toArray();
    }

    public static void sortIdList(List<Long> ids , List<StockDto> stockList){
        Map<Long, Integer> map = IntStream.range(0, ids.size()).boxed().collect(Collectors.toMap(i -> ids.get(i), i -> i));
        Collections.sort(stockList, Comparator.comparingInt(s -> map.get(s.getStock_id())));
    }

    public static int randomFluctuateGenerate(){
        int min = -100;
        int max = 100;
        Random rand = new Random();
        return rand.ints(min, (max + 1)).findFirst().getAsInt();
    }
}
