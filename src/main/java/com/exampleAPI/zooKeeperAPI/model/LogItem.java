package com.exampleAPI.zooKeeperAPI.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static com.exampleAPI.zooKeeperAPI.aspect.Producer.COMMA;


@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class LogItem {
    String date;
    String urlType;
    String type;
    String parameter;
    String response;

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrlType(String url) {
        this.urlType = url.substring(url.lastIndexOf("/") + 1);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter.replace(COMMA, " ");
    }

    public void setResponse(String response) {
        this.response = response.replace(COMMA, " ");
    }
}
