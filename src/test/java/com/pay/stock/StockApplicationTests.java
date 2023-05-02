package com.pay.stock;

import com.pay.stock.api.dao.*;
import com.pay.stock.api.service.stock.StockService;
import com.pay.stock.entity.StockView;
import com.pay.stock.model.StockDto;
import com.pay.stock.utils.StockUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs()
@AutoConfigureMockMvc
@DisplayName("#종목")
@SpringBootTest
public class StockApplicationTests {

    @Resource
    private MockMvc mockMvc;
    @Resource
    private StockService stockService;
    @Resource
    private ModelMapper mapper;
    @Resource
    private StockDao stockDao;
    @Resource
    private StockViewDao stockViewDao;

    @DisplayName("DB 연결 확인 - DB connection timeout")
    @Test
    public void connectionCheck(){
        // connectionTimeout: 3000 이 3초 이기 때문에 3000초 동안 결과가 오지 않는다면 연결에 문제가 있는것으로 간주한다.
        assertTimeout(Duration.ofMillis(3000), () -> stockService.getStock(25L));
    }

    @DisplayName("데이터 세팅 - 데이터 변경 DB connection timeout")
    @Test
    public void setData(){
        // csv 파일 을 해당 경로에서 읽어 DB 에 적재 한다. resources/file/SampleData.csv
        assertTimeout(Duration.ofMillis(3000), () -> stockService.setTestData(stockService.getStockList(1 , 120)) );
    }

    @DisplayName("종목조회 - 종목 개별 조회")
    @Test
    public void getStock(){
        assertNotNull(stockService.getStock(25L));
    }

    @DisplayName("종목조회 - 종목 리스트 조회")
    @Test
    public void getStockList(){
        assertNotNull(stockService.getStockList(1 , 20));
        assertEquals(stockService.getStockList(1 , 20).size() , 20);
    }

    @DisplayName("function - 재정렬")
    @Test
    public void sortTest(){
        List<Long> ids = new ArrayList<>();
        Map<Long , Integer> fluctuate = new HashMap<Long , Integer>();
        Map<Long , Integer> rank = new HashMap<Long , Integer>();
        // 많이 본 종목 1page 조회
        Page<StockView> list = stockViewDao.getStockViewList(1, 20);

        list.getContent().forEach(s -> {ids.add(s.getStockKey().getStockId());
                                        fluctuate.put(s.getStockKey().getStockId() , s.getChartFluctuate());
                                        rank.put(s.getStockKey().getStockId() , s.getViewCount());});

        // 많이 본 종목 id in 절로 조회
        List<StockDto> stockList = stockDao.getStockListIds(ids).stream().map(s -> mapper.map(s , StockDto.class)).collect(Collectors.toList());
        //map
        Map<Long, Integer> map = IntStream.range(0, ids.size()).boxed().collect(Collectors.toMap(i -> ids.get(i), i -> i));
        //map 에 있는 ID 로 sort
        Collections.sort(stockList, Comparator.comparingInt(s -> map.get(s.getStock_id())));

        for(int i = 0;i < list.getContent().size();i++){
            System.out.println("#SORT : " + stockList.get(i).getStock_id() + ":" + list.getContent().get(i).getStockKey().getStockId());
            assertEquals(stockList.get(i).getStock_id() , list.getContent().get(i).getStockKey().getStockId());
        }
    }

    @DisplayName("function - random 넘버 취득")
    @Test
    public void randomNumberTest(){
        //등락폭 테스트 데이터를 넣기위한 랜덤 등락폭 생성 function
        int ran = StockUtils.randomFluctuateGenerate();
        System.out.println("#RANDOM NUMBER --> " + ran);
        assertThat(ran , greaterThan(-101));
        assertThat(ran , lessThan(101));
    }

    @DisplayName("종목조회 - 종목 차트 타입별 리스트 조회")
    @Test
    public void getStockListType(){
        int page = 1;
        int size = 20;
        // "view" , "up" , "down" , "volume"
        assertNotNull(stockService.getStockListType(page , size , "up"));
        assertNotNull(stockService.getStockListType(page , size , "down"));
        assertNotNull(stockService.getStockListType(page , size , "down"));
        assertNotNull(stockService.getStockListType(page , size , "volume"));
    }

    @DisplayName("종목조회 - 종목 차트 타입별 조회 RestDocs 생성")
    @Test
    public void chartTypeListTest() throws Exception {
        String type = "view";
        ResultActions result = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stock/chart/{type}/list" , type)).andDo(print()).andExpect(status().isOk());
        result.andExpect(status().isOk()).andDo(document("chartList",
                        pathParameters(
                                parameterWithName("type").description("view:많이본 up:많이오른 down:많이내린 volume:거래량많은")
                        ),
                        responseFields(
                                beneathPath("data") ,
                                fieldWithPath("stock_chart_list.[].stock_id").type(JsonFieldType.NUMBER).description("종목 ID"),
                                fieldWithPath("stock_chart_list.[].stock_code").type(JsonFieldType.STRING).description("종목 코드"),
                                fieldWithPath("stock_chart_list.[].stock_name").type(JsonFieldType.STRING).description("종목 명"),
                                fieldWithPath("stock_chart_list.[].stock_price").type(JsonFieldType.NUMBER).description("종목 가격"),
                                fieldWithPath("stock_chart_list.[].chart_fluctuate").type(JsonFieldType.NUMBER).description("등락 폭"),
                                fieldWithPath("stock_chart_list.[].rank").type(JsonFieldType.NUMBER).description("순위"),
                                fieldWithPath("chart_info.chart_type").type(JsonFieldType.STRING).description("차트 타입")



                        )
                )
        ).andDo(print());
    }

    @DisplayName("종목조회 - 종목 차트 홈 조회 RestDocs 생성")
    @Test
    public void chartTest() throws Exception {
        ResultActions result = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stock/chart/home")).andDo(print()).andExpect(status().isOk());
        result.andExpect(status().isOk()).andDo(document("chartHome",
                        responseFields(
                                beneathPath("data") ,
                                fieldWithPath("view_chart_list.[].stock_id").type(JsonFieldType.NUMBER).description("종목 ID"),
                                fieldWithPath("view_chart_list.[].stock_code").type(JsonFieldType.STRING).description("종목 코드"),
                                fieldWithPath("view_chart_list.[].stock_name").type(JsonFieldType.STRING).description("종목 명"),
                                fieldWithPath("view_chart_list.[].stock_price").type(JsonFieldType.NUMBER).description("종목 가격"),
                                fieldWithPath("view_chart_list.[].chart_fluctuate").type(JsonFieldType.NUMBER).description("종목 리스트 등락폭"),
                                fieldWithPath("view_chart_list.[].rank").type(JsonFieldType.NUMBER).description("순위"),

                                fieldWithPath("increase_chart_list.[].stock_id").type(JsonFieldType.NUMBER).description("종목 ID"),
                                fieldWithPath("increase_chart_list.[].stock_code").type(JsonFieldType.STRING).description("종목 코드"),
                                fieldWithPath("increase_chart_list.[].stock_name").type(JsonFieldType.STRING).description("종목 명"),
                                fieldWithPath("increase_chart_list.[].stock_price").type(JsonFieldType.NUMBER).description("종목 가격"),
                                fieldWithPath("increase_chart_list.[].chart_fluctuate").type(JsonFieldType.NUMBER).description("종목 리스트 등락폭"),
                                fieldWithPath("increase_chart_list.[].rank").type(JsonFieldType.NUMBER).description("순위"),

                                fieldWithPath("down_chart_list.[].stock_id").type(JsonFieldType.NUMBER).description("종목 ID"),
                                fieldWithPath("down_chart_list.[].stock_code").type(JsonFieldType.STRING).description("종목 코드"),
                                fieldWithPath("down_chart_list.[].stock_name").type(JsonFieldType.STRING).description("종목 명"),
                                fieldWithPath("down_chart_list.[].stock_price").type(JsonFieldType.NUMBER).description("종목 가격"),
                                fieldWithPath("down_chart_list.[].chart_fluctuate").type(JsonFieldType.NUMBER).description("종목 리스트 등락폭"),
                                fieldWithPath("down_chart_list.[].rank").type(JsonFieldType.NUMBER).description("순위"),

                                fieldWithPath("volume_chart_list.[].stock_id").type(JsonFieldType.NUMBER).description("종목 ID"),
                                fieldWithPath("volume_chart_list.[].stock_code").type(JsonFieldType.STRING).description("종목 코드"),
                                fieldWithPath("volume_chart_list.[].stock_name").type(JsonFieldType.STRING).description("종목 명"),
                                fieldWithPath("volume_chart_list.[].stock_price").type(JsonFieldType.NUMBER).description("종목 가격"),
                                fieldWithPath("volume_chart_list.[].chart_fluctuate").type(JsonFieldType.NUMBER).description("종목 리스트 등락폭"),
                                fieldWithPath("volume_chart_list.[].rank").type(JsonFieldType.NUMBER).description("순위")
                        )
                )
        ).andDo(print());
    }

    @DisplayName("테스트 - 데이터 변경 RestDocs 생성")
    @Test
    public void dataSetTest() throws Exception {
        ResultActions result = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/stock/init")).andDo(print()).andExpect(status().isOk());
        result.andExpect(status().isOk()).andDo(document("testSet")).andDo(print());
    }

}
