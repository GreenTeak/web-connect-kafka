package com.exampleAPI.zooKeeperAPI.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.Instant;

@Component
@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
@EqualsAndHashCode
public class LogType {
    String data;
    String url;
    String type;
    String parmater;
    String reponse;
}
