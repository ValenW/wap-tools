package com.wap.tools;

import jodd.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static jodd.io.FileUtil.readLines;

/**
 * Created by zuo_ji on 17-9-8.
 */
public class Generate {

    private static Logger log = LoggerFactory.getLogger(Generate.class);

    public static void main(String[] args) {
        String csvPath="/home/zuo_ji/Desktop/text.csv";
        String jsonPath = "/home/zuo_ji/Desktop/";
        new Generate().genTextJson(csvPath,jsonPath);
    }

    public void genTextJson(String csvPath,String jsonPath){
        List<TextDefDto> textDefDtos = getTextDefDtos(csvPath);
        String json = getTextDefJson(textDefDtos);
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
            String text = String.format(template, item.getTextId(), item.getLanguage(), item.getValue());
            stringBuilder.append(text);
            if (i != textDefDtos.size() - 1) {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static List<TextDefDto> getTextDefDtos(String path) {
        List<TextDefDto> textDefDtos = new ArrayList<>();
        try {

            String[] lines = readLines(path);
            for (int i = 1; i < lines.length; i++) {
                String textid = lines[i].split(",", -1)[0].trim();
                String entext = lines[i].split(",", -1)[1].trim();
                String jatext = lines[i].split(",", -1)[2].trim();
                textDefDtos.add(new TextDefDto(textid, "en", entext));
                textDefDtos.add(new TextDefDto(textid, "ja", jatext));
            }
        } catch (IOException e) {
            log.error("read csv file error {}",e);
            throw  new RuntimeException("读csv文件失败,("+e.getMessage()+")");
        }
        log.info("get {} text id", textDefDtos.size());
        return textDefDtos;
    }


}
