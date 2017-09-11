package com.wap.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zuo_ji on 17-9-8.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextDefDto {
    private String textId;
    private String language;
    private String value;

}
