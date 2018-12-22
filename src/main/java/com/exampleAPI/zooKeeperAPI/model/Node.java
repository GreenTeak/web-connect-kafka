package com.exampleAPI.zooKeeperAPI.model;

import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
public class Node {
    private String path;

    private String content;

    public Node() {
    }

    public Node(String path, String content) {
        this.path = path;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getPath() {
        return path;
    }
}
