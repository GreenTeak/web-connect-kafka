package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ZookeeperControllerTest {

    public static final String API_NODE = "/api/node";
    public static final String TEST2_PATH = "/test2";
    public static final String TEST2 = "test2";

    @MockBean
    private ZookeeperService zookeeperService;

    @Autowired
    protected MockMvc mvc;

    private Node node = new Node(TEST2_PATH, TEST2);

    private String requestJson;

    @Before
    public void setUp() throws KeeperException, InterruptedException, JsonProcessingException {
        when(zookeeperService.listNodeData()).thenReturn(TEST2);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(node);
    }

    @Test
    public void shouldBeReturnStatusIsCreatedWhenAddNode() throws Exception {
        mvc.perform(post(API_NODE)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(zookeeperService, times(1))
                .addNodeIfNotExists(node);

    }

    @Test
    public void shouldBeReturnStatusIsAcceptedWhenUpdateNode() throws Exception {
        mvc.perform(put(API_NODE)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        verify(zookeeperService, times(1))
                .updateNodeData(node);
    }

    @Test
    public void shouldBeReturnNodeListWhenGetNode() throws Exception {
        String contentAsString = mvc.perform(get(API_NODE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(zookeeperService, times(1))
                .listNodeData();

        assertEquals(contentAsString,TEST2);

    }

    @Test
    public void shouldBeReturnStatusIsAcceptedWhenDeleteNode() throws Exception {

        mvc.perform(delete(API_NODE)
                .content(TEST2_PATH)
                .contentType(MediaType.ALL))
                .andExpect(status().isAccepted());

        verify(zookeeperService, times(1))
                .deleteNode(TEST2_PATH);
    }
}