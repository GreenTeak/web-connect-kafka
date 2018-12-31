package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.exampleAPI.zooKeeperAPI.support.JsonAndObject.ObjectToJson;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD_IS_WRONG_OR_NOT_HAVE_THIS_USER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PATH;


@Service
@NoArgsConstructor
@Data
public class UserService {

    @Autowired
    public ZookeeperService zookeeperService;

    public final Logger logger = Logger.getLogger(UserService.class);

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

    public Integer userLogin(String email, String password) throws KeeperException, InterruptedException, IOException {

        //Stat stat = zookeeperService.getStat(PATH + email);
        //if (stat == null) return -1;
        if(!validateUserExistOrNot(email)){
            return -1;
        }

        User user = zookeeperService.getData(PATH + email);
        if (user != null || !user.getPassword().equals(password)) {
            return user.getAge();
        } else {
            logger.error(PASSWORD_IS_WRONG_OR_NOT_HAVE_THIS_USER);
            return -1;
        }
    }

    public boolean updateUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = ObjectToJson(user);
        Node node = new Node(generatePath(user), userJson);
        zookeeperService.updateNodeData(node);
        return true;

    }

    public boolean deleteUser(String email) throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(PATH + email);
        return true;
    }

    private boolean validateUserExistOrNot(String email) throws KeeperException, InterruptedException {
        String nodeList = zookeeperService.listNodeData();
        return nodeList.contains(email);
    }
}
