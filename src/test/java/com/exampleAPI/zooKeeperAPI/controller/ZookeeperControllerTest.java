package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
    public static final String TEST_TEST_3 = "/test2";
    public static final String TEST_3 = "test2";
    public static final String JSON_CONTENT = "{\"path\":\"/test2\",\"context\":\"test2\"}";
    public static final String TEST_1 = "test2";

    @MockBean
    private ZookeeperService zookeeperService;

    @Autowired
    protected MockMvc mvc;

    private Node node = new Node(TEST_TEST_3, TEST_3);

    @Before
    public void setUp() throws KeeperException, InterruptedException {
        when(zookeeperService.listNodeData()).thenReturn(TEST_1);
    }

    @Test
    public void shouldBeReturnStatusIsCreatedWhenAddNode() throws Exception {
        Node node = new Node("/test2","test2");

        mvc.perform(post(API_NODE)
                .content(JSON_CONTENT)
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
        verify(zookeeperService, times(1))
                .addNodeIfNotExists(node);

    }

    @Test
    public void shouldBeReturnStatusIsAcceptedWhenUpdateNode() throws Exception {
        Node node = new Node("/test2","test2");

        mvc.perform(put(API_NODE)
                .content(JSON_CONTENT)
                .contentType(MediaType.ALL))
                .andExpect(status().isAccepted());
        verify(zookeeperService, times(1))
                .updateNodeData(node);
    }

    @Test
    public void shouldBeReturnNodeListWhenGetNode() throws Exception {
        DocumentContext response = JsonPath.parse(mvc.perform(get(API_NODE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        verify(zookeeperService, times(1))
                .listNodeData();

        assertEquals(response.toString(), TEST_1);

    }

    @Test
    public void shouldBeReturnStatusIsAcceptedWhenDeleteNode() throws Exception {

        mvc.perform(delete(API_NODE)
                .content(TEST_TEST_3)
                .contentType(MediaType.ALL))
                .andExpect(status().isAccepted());

        verify(zookeeperService, times(1))
                .deleteNode(TEST_TEST_3);
    }
}