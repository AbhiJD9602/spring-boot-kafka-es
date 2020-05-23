package com.project.kafka.newsclient.client.dto;

import lombok.Data;

import java.util.Date;

@Data
public class News {

    private String id;
    private String title;
    private String text;
    private Date datetime;
    private String category;

}
