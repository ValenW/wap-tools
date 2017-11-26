package com.shawn.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

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
