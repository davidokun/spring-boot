package com.singletonapps.demospringcache.model;

import lombok.Data;

import java.util.Date;

@Data
public class Post {

    private Long id;
    private String body;
    private Author author;
    private Date postedOn;
}
