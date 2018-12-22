package com.exampleAPI.zooKeeperAPI.model;

import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class Node {
    private String path;

    private String content;

    public Node() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(getPath(), node.getPath()) &&
                Objects.equals(getContent(), node.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getContent());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Node(String path, String content) {
        this.path = path;
        this.content = content;
    }
}
