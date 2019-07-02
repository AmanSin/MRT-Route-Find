package com.test.learn.controller;

import com.test.learn.Application;
import com.test.learn.model.request.RouteInput;
import com.test.learn.model.response.RouteDetailResponse;
import com.test.learn.model.response.RouteList;
import com.test.learn.service.RouteService;
import com.test.learn.util.Constant;
import com.test.learn.validation.RouteValidation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class RouteControllerTest {

    @MockBean
    private RouteService routeService;

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    @Autowired
    private RouteValidation routeValidation;

    @Autowired
    private RouteController controller;

    private MockMvc mockMvc;

    private final static String DATA_FILE_PATH  = "src/test/resources/StationMap.csv";

    @BeforeClass
    public static void beforeClass() {
        File file = new File(DATA_FILE_PATH);
        System.setProperty("station.data.file", file.getAbsolutePath());
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty("station.data.file");
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(exceptionHandlerController).build();
    }

    @Test
    public void testGetRouteByTime_Success() throws Exception {
        //ASSIGN
        String data = new String(Files.readAllBytes(Paths.get(this.getClass()
                .getResource("/testData1.json").toURI())));
        RouteList routeList = new RouteList();
        routeList.setRouteList(Arrays.asList(new RouteDetailResponse("abc", "pqr", 2, null, null, "10")));
        when(routeService.getRouteListByTime(any(RouteInput.class))).thenReturn(routeList);

        //ACT
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(Constant.HTTP_GET_ROUTE_BY_TIME_REQUEST_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)).andReturn();
        String content = result.getResponse().getContentAsString();

        //ASSERT
        int status = result.getResponse().getStatus();
        verify(routeService, times(1)).getRouteListByTime(any(RouteInput.class));
        Assert.assertEquals("Failure - Expected HTTP Status 200", 200, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value", content.trim().length() > 0);
        Assert.assertTrue("Failure - expected HTTP response body to have valid value", content.trim().contains("abc"));
    }

    @Test
    public void testGetRouteByTime_InvalidDestinationStation() throws Exception {
        //ASSIGN
        String data = new String(Files.readAllBytes(Paths.get(this.getClass()
                .getResource("/testData2.json").toURI())));
        RouteList routeList = new RouteList();
        routeList.setRouteList(Arrays.asList(new RouteDetailResponse("abc", "pqr", 2, null, null, "10")));
        when(routeService.getRouteListByTime(any(RouteInput.class))).thenReturn(routeList);

        //ACT
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(Constant.HTTP_GET_ROUTE_BY_TIME_REQUEST_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)).andReturn();
        String content = result.getResponse().getContentAsString();

        //ASSERT
        int status = result.getResponse().getStatus();
        verify(routeService, times(0)).getRouteListByTime(any(RouteInput.class));
        Assert.assertEquals("Failure - Expected HTTP Status 400", 400, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value", content.trim().length() > 0);
        Assert.assertTrue("Failure - expected HTTP response body to contain error", content.contains("ERR_API_INP_003"));
    }

    @Test
    public void testGetRouteByTime_InvalidStartingTime() throws Exception {
        //ASSIGN
        String data = new String(Files.readAllBytes(Paths.get(this.getClass()
                .getResource("/testData3.json").toURI())));
        RouteList routeList = new RouteList();
        routeList.setRouteList(Arrays.asList(new RouteDetailResponse("abc", "pqr", 2, null, null, "10")));
        when(routeService.getRouteListByTime(any(RouteInput.class))).thenReturn(routeList);

        //ACT
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(Constant.HTTP_GET_ROUTE_BY_TIME_REQUEST_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)).andReturn();
        String content = result.getResponse().getContentAsString();

        //ASSERT
        int status = result.getResponse().getStatus();
        verify(routeService, times(0)).getRouteListByTime(any(RouteInput.class));
        Assert.assertEquals("Failure - Expected HTTP Status 400", 400, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value", content.trim().length() > 0);
        Assert.assertTrue("Failure - expected HTTP response body to contain error", content.contains("ERR_API_INP_004"));
    }
}
