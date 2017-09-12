package com.wap.tools;

import com.wap.tools.dao.CSVReader;
import com.wap.tools.dto.TextCsvDto;
import com.wap.tools.dto.TextDefDto;
import jodd.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zuo_ji on 17-9-8.
 */
public class Generate {

    private static Logger log = LoggerFactory.getLogger(Generate.class);

    public static void main(String[] args) {

        String csvPath= System.getProperty("user.dir")+"/csv/output.csv";
        File file=new File(csvPath);
        if(file.exists()){
            log.info(file.getAbsolutePath());
        }
        String jsonPath = "json/";
        new Generate().genTextJson(csvPath,jsonPath);
    }

    public void genTextJson(String csvPath,String jsonPath){
//        List<TextDefDto> textDefDtos = getTextDefDtos(csvPath);
        List<TextCsvDto> textDefDtos = CSVReader.loadObjectList(TextCsvDto.class,csvPath);
        List<TextDefDto> dtos=textDefDtos.stream()
                .map(it->it.toTextDefDtos())
                .flatMap(it-> it.stream())
                .collect(Collectors.toList());
        String json = getTextDefJson(dtos);

        try {
            String fileName= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+"_INSERT_com.worksap.company.hue.core.dto.TextDefDto.json";
            FileUtil.writeString(jsonPath+"/"+fileName, json);
            log.info("success write content to json file {}", jsonPath+fileName);
        } catch (IOException e) {
            log.error("write json to file error {}",e);
            throw new RuntimeException("写入文件时错误");
        }
    }

    private static String getTextDefJson(List<TextDefDto> textDefDtos) {
       final String template = "{\"class\":\"com.worksap.company.hue.core.dto.TextDefDto\",\"pl\":\"{\\\"key\\\":" +
                "{\\\"class\\\":\\\"com.worksap.company.framework.textresource.TextResourceKey\\\",\\\"pl\\\":" +
                "\\\"{\\\\\\\"textId\\\\\\\":\\\\\\\"%s\\\\\\\"," +
                "\\\\\\\"locale\\\\\\\":\\\\\\\"%s\\\\\\\",\\\\\\\"plural\\\\\\\":\\\\\\\"\\\\\\\"}\\\"},\\\"value\\\":" +
                "\\\"%s\\\",\\\"systemFlg\\\":false}\"}";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < textDefDtos.size(); i++) {
            TextDefDto item = textDefDtos.get(i);
            item.setValue(item.getValue().replace("\"","\\\\\\\""));
            String text = String.format(template, item.getTextId(), item.getLanguage(), item.getValue());
            stringBuilder.append(text);
            if (i != textDefDtos.size() - 1) {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }




}
