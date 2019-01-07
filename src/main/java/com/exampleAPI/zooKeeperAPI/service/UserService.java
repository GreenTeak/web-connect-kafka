package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.exampleAPI.zooKeeperAPI.support.JsonAndObject.ObjectToJson;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD_IS_WRONG_OR_NOT_HAVE_THIS_USER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PATH;


@Service
@Data
public class UserService {

    @Autowired
    public ZookeeperService zookeeperService;

    public final Logger logger = Logger.getLogger(UserService.class);

    public UserService() throws InterruptedException, IOException, KeeperException {
        zookeeperService = new ZookeeperService();
    }

    public UserService(ZookeeperService zookeeperService) {
        this.zookeeperService = zookeeperService;
    }

    private String generatePath(User user) {
        return PATH + user.getEmail();
    }

    public boolean addUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = ObjectToJson(user);
        Node node = new Node(generatePath(user), userJson);
        if (validateUserExistOrNot(user.getEmail())) {
            logger.error("user is exists!");
            return false;
        }
        zookeeperService.addNodeIfNotExists(node);
        return true;
    }

    public String userLogin(String email, String password) throws KeeperException, InterruptedException, IOException {
        if (!validateUserExistOrNot(email)) {
            return null;
        }
        User user = zookeeperService.getData(PATH + email);
        if (user != null && user.getPassword().equals(password)) {
            return user.getEmail();
        } else {
            logger.error(PASSWORD_IS_WRONG_OR_NOT_HAVE_THIS_USER);
            return null;
        }
    }

    public boolean updateUser(User user, String newPassword) throws JsonProcessingException, KeeperException, InterruptedException {
        if (!validateUserExistOrNot(user.getEmail())) {
            logger.error("user is not exists!");
            return false;
        }
        user.setPassword(newPassword);
        String userJson = ObjectToJson(user);
        Node node = new Node(generatePath(user), userJson);
        zookeeperService.updateNodeData(node);
        return true;

    }

    public void deleteUser(String email) throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(PATH + email);
    }

    private boolean validateUserExistOrNot(String email) throws KeeperException, InterruptedException {
        String nodeList = zookeeperService.listNodeData();
        return nodeList.contains(email);
    }
}
