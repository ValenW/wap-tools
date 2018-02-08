package com.worksap.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Xiaoyue Xiao
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class User implements Serializable {

    private static final long serialVersionUID = 7698862379923111158L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String ips;

}
