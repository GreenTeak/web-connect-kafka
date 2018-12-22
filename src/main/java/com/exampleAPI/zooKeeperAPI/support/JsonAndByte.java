package com.exampleAPI.zooKeeperAPI.support;

import com.exampleAPI.zooKeeperAPI.model.User;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Component
public class JsonAndByte {
    public User toObject(byte[] bytes) {
        User user = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            user = (User) objectInputStream.readObject();
            byteArrayInputStream.close();// 关闭输入流
            objectInputStream.close();// 关闭输入流
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
