package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.support.JsonAndObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD_IS_WRONG_OR_NOT_HAVE_THIS_USER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PATH;


@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserService {

    @Autowired
    public ZookeeperService zookeeperService;

    @Autowired
    public JsonAndObject jsonAndObject;

    public final Logger logger = Logger.getLogger(UserService.class);

    public UserService(ZookeeperService zookeeperService) {
        this.zookeeperService = zookeeperService;
    }

    private String generatePath(User user) {
        return PATH + user.getEmail();
    }

    public boolean addUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = jsonAndObject.ObjectToJson(user);
        Node node = new Node(generatePath(user), userJson);
        if(!validateUserExistOrNot(user.getEmail())) {
            zookeeperService.addNodeIfNotExists(node);
            return true;
        }
        return false;
    }

    public Integer userLogin(String email, String password) throws KeeperException, InterruptedException, IOException {
        //Stat stat = zookeeperService.getStat(PATH + email);
        boolean isExistNode = validateUserExistOrNot(email);
        User user = zookeeperService.getData(PATH + email);
        if (!isExistNode || user != null || !user.getPassword().equals(password)) {
            logger.error(PASSWORD_IS_WRONG_OR_NOT_HAVE_THIS_USER);
            return -1;
        }
        return user.getAge();
    }

    public void updateUser(User user) throws JsonProcessingException, KeeperException, InterruptedException {
        String userJson = jsonAndObject.ObjectToJson(user);
        Node node = new Node(generatePath(user), userJson);
        zookeeperService.updateNodeData(node);
    }

    public void deleteUser(String email) throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(PATH + email);
    }

    private boolean validateUserExistOrNot(String email) throws KeeperException, InterruptedException {
        String nodeList = zookeeperService.listNodeData();
        return nodeList.contains(email);
    }
}
