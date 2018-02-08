package com.worksap.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserIp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName,ip;
}
