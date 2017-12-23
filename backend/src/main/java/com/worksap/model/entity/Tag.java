package com.worksap.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String color;
    //默认Tag
    private Boolean sys;

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
        this.sys=false;
    }

    public Tag(String name, String color, boolean sys) {
        this.name = name;
        this.color = color;
        this.sys = sys;
    }
}
