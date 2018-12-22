package com.exampleAPI.zooKeeperAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Node {
    private String path;
    private String content;
}
