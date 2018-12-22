package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public ZookeeperService zookeeperService;

    public ObjectMapper mapper = new ObjectMapper();

    public static final String PATH = "/test/";

    public ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    private String generatePath(User user) {
        return PATH + user.getEmail();
    }

    public void addUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = ow.writeValueAsString(user);
        Node node = new Node(generatePath(user), userJson);
        zookeeperService.addNodeIfNotExists(node);
    }

    public Integer userLogin(String email, String password) throws KeeperException, InterruptedException {
        User user = zookeeperService.getData(PATH + email);
        if (user.getPassword().equals(password)) {
            return user.getAge();
        }
        return -1;
    }

    public void updateUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = ow.writeValueAsString(user);
        Node node = new Node(generatePath(user), userJson);
        zookeeperService.updateNodeData(node);
    }

    public void deleteUser(String email) throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(PATH + email);
    }
}
