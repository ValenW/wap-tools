package com.shawn.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table
@Entity
public class TextResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String textId;
    private String en;
    private String ja;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public TextResource(String textId, String en, String ja) {
        this.textId = textId;
        this.en = en;
        this.ja = ja;
        this.createTime = LocalDateTime.now();
    }


}
