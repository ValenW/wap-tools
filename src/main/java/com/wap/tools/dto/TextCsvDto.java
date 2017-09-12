package com.wap.tools.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuo_ji on 17-9-12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextCsvDto implements Comparable{
    private String textId;
    private String en;
    private String ja;


    @Override
    public int compareTo(Object o) {
        TextCsvDto other= (TextCsvDto) o;
       return this.textId.compareTo(((TextCsvDto) o).getTextId());
    }

    public List<TextDefDto> toTextDefDtos(){
        List<TextDefDto> dtos=new ArrayList<>();
        if(StringUtils.isNotBlank(this.getJa())){
            dtos.add(new TextDefDto(this.getTextId(),"ja",this.getJa()));
        }
        if(StringUtils.isNotBlank(this.getEn())){
            dtos.add(new TextDefDto(this.getTextId(),"en",this.getEn()));
        }
        return dtos;
    }
}
