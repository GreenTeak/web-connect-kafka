package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserService {

    @Autowired
    public ZookeeperService zookeeperService;

    public static final String PATH = "/test/";
    public ObjectMapper mapper = new ObjectMapper();

    public ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();


    public final Logger logger = Logger.getLogger(UserService.class);


    public UserService(ZookeeperService zookeeperService) {
        this.zookeeperService = zookeeperService;
    }

    private String generatePath(User user) {
        return PATH + user.getEmail();
    }

    public void addUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = ow.writeValueAsString(user);
        Node node = new Node(generatePath(user), userJson);
        zookeeperService.addNodeIfNotExists(node);
    }

    public Integer userLogin(String email, String password) throws KeeperException, InterruptedException, IOException {
        Stat stat = zookeeperService.getStat(PATH + email);
        User user = zookeeperService.getData(PATH + email);

        if (stat == null || user != null || !user.getPassword().equals(password)) {
            logger.error("password is wrong or don't have this user");
            return -1;
        }
        return user.getAge();
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
