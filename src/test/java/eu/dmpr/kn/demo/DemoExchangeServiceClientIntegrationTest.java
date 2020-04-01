package eu.dmpr.kn.demo;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.math.BigDecimal;
import java.net.URI;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.dmpr.kn.demo.model.ExchangeRate;
import eu.dmpr.kn.demo.model.ExchangeRates;
import eu.dmpr.kn.demo.service.DemoExchangeService;
import eu.dmpr.kn.demo.service.ExchangeServiceCallinglService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles("test")
public class DemoExchangeServiceClientIntegrationTest {

    MockRestServiceServer mockRestServiceServer;

    @Value("${exchange_service_url}")
    private String BASE_URL;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExchangeServiceCallinglService exchangeServiceCallinglService;

    @Autowired
    private DemoExchangeService demoExchangeService;


    @Before
    public void setUp() throws Exception {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenCalling_Latest_thenClientExecutesCorrectCalls() throws Exception {
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        String rate = "0.9127418766";
        String date = "2020-03-31";

        URI targetUrl = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/latest")
                .queryParam("base", baseCurrency)
                .queryParam("symbols", targetCurrency)
                .build()
                .encode()
                .toUri();

        String body = "{\"rates\":{\"" + targetCurrency + "\":" + rate + "},\"base\":\"" + baseCurrency + "\",\"date\":\"" + date + "\"}";

        mockRestServiceServer.expect(once(), requestTo(targetUrl.toString()))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        ExchangeRate latest = demoExchangeService.latest(baseCurrency, targetCurrency);
        mockRestServiceServer.verify();

        assertEquals(latest.getBaseCurrency(), baseCurrency);
        assertEquals(latest.getTargetCurrency(), targetCurrency);
        assertEquals(latest.getRate(), new BigDecimal(rate));
        assertEquals(latest.getDate(), date);
    }

//    @Ignore
//    @Test
//    public void whenCalling_LowestRateOfCurrentMonth_thenClientExecutesCorrectCalls() throws Exception {
//
//        ExchangeRate rate = ExchangeRate.from(ExchangeRates.builder().base("USD").date())
//        String baseCurrency = "USD";
//        String targetCurrency = "EUR";
//        String rate = "0.9127418766";
//        String fromDate = "2020-03-01";
//        String toDate = "2020-03-31";
//
//        String url = "/history?base=" + baseCurrency + "&symbols=" + targetCurrency + "&start_at=" + fromDate + "&end_at=" + toDate;
//
////        String body = "{\"rates\":{\"" + targetCurrency + "\":" + rate + "},\"base\":\"" + baseCurrency + "\",\"date\":\"" + date + "\"}";
//
//        String body1 = "{\"rates\":{\"2020-03-24\":{\"EUR\":0.9222539887},\"2020-03-11\":{\"EUR\":0.8821453776},\"2020-03-31\":{\"EUR\":0.9127418766},\"2020-03-16\":{\"EUR\":0.8962982881},\"2020-03-09\":{\"EUR\":0.8729050279},\"2020-03-04\":{\"EUR\":0.8988764045},\"2020-03-27\":{\"EUR\":0.9109957183},\"2020-03-10\":{\"EUR\":0.8779631255},\"2020-03-17\":{\"EUR\":0.9105809506},\"2020-03-18\":{\"EUR\":0.9145783794},\"2020-03-05\":{\"EUR\":0.8938946992},\"2020-03-23\":{\"EUR\":0.9273856997},\"2020-03-20\":{\"EUR\":0.9339684319},\"2020-03-26\":{\"EUR\":0.910663874},\"2020-03-25\":{\"EUR\":0.9236168837},\"2020-03-06\":{\"EUR\":0.8821453776},\"2020-03-19\":{\"EUR\":0.9258402},\"2020-03-13\":{\"EUR\":0.9005763689},\"2020-03-12\":{\"EUR\":0.8896797153},\"2020-03-30\":{\"EUR\":0.9062896502},\"2020-03-02\":{\"EUR\":0.8991188635},\"2020-03-03\":{\"EUR\":0.8995232527}},\"start_at\":\"2020-03-01\",\"base\":\"USD\",\"end_at\":\"2020-03-31\"}";
//
//        mockRestServiceServer.expect(once(), requestTo(BASE_URL + url))
//                .andRespond(withSuccess(
//                        body1, MediaType.APPLICATION_JSON));
//
////        exchangeServiceCallinglService.;
//
//        ExchangeRate latest = demoExchangeService.lowestRateOfCurrentMonth(baseCurrency, targetCurrency);
//        mockRestServiceServer.verify();
//
//        assertEquals(latest.getBaseCurrency(), baseCurrency);
//        assertEquals(latest.getTargetCurrency(), targetCurrency);
//        assertEquals(latest.getRate(), new BigDecimal(rate));
////        assertEquals(latest.getDate(), "");
//    }
}
