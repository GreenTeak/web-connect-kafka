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
public class LogType {
    String date;
    String url;
    String type;
    String parameter;
    String response;
}
