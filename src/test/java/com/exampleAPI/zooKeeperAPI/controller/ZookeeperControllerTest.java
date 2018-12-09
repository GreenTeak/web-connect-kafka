package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    public static final String API_NODE_CREATE = "/api/node/create";
    public static final String TEST_TEST_3 = "/test/test3";
    public static final String TEST_3 = "test3";
    public static final String TEST_0 = "test0";
    public static final String TEST_TEST_3_TEST_3 = "/test/test3:test3";
    public static final String API_NODE_UPDATE = "/api/node/update";
    public static final String API_NODE_GET = "/api/node/get";
    public static final String API_NODE_DELETE = "/api/node/delete";
    @MockBean
    private ZookeeperService zookeeperService;

    @Autowired
    protected MockMvc mvc;

    @Test
    public void zkCreate() throws Exception {
        mvc.perform(post(API_NODE_CREATE)
                .content(TEST_TEST_3_TEST_3)
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
        verify(zookeeperService,times(1))
                .addNodeData(TEST_TEST_3, TEST_3);

    }

    @Test
    public void zkUpdate() throws Exception {
        mvc.perform(put(API_NODE_UPDATE)
                .content(TEST_TEST_3_TEST_3)
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
        verify(zookeeperService,times(1))
                .updateNodeData(TEST_TEST_3,TEST_3);
    }

    @Test
    public void zkGet() throws Exception {
        mvc.perform(get(API_NODE_GET))
            .andExpect(status().isOk());
        verify(zookeeperService,times(1))
                .listNodeData();
    }

    @Test
    public void zkDelete() throws Exception {
        mvc.perform(delete(API_NODE_DELETE)
                .content(TEST_TEST_3)
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
        verify(zookeeperService,times(1))
                .deleteNode(TEST_TEST_3);
    }
}