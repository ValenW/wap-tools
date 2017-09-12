package com.wap.tools.service;

import com.wap.tools.dao.CSVReader;
import com.wap.tools.dto.TextCsvDto;
import com.wap.tools.dto.TextDefDto;
import jodd.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by zuo_ji on 17-9-12.
 */
@Service
public class TextResourceService {
    private Logger log= LoggerFactory.getLogger(this.getClass());

    public void genTextJson(String csvPath,String jsonPath){
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



    public void read(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在在，请检查路径");
        }

        Set<TextDefDto> textDefDtos = new HashSet<>();
        List<String> filePaths=new ArrayList<>();
        for(File f:file.listFiles()){
            filePaths.add(f.getAbsolutePath());
        }

        filePaths.sort(Comparator.reverseOrder());

        for (String filePath: filePaths) {
            log.info("start to read {}",filePath);
            String jsonStr = FileUtil.readString(filePath);
            JsonParser parser = new JacksonJsonParser();
            List<Object> objects = parser.parseList(jsonStr);
            objects.forEach(item -> {
                LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) item;
                String pl = map.get("pl");
                TextDefDto textDefDto = readjson(pl);
                textDefDtos.add(textDefDto);
            });
        }
        writeToCsv(fromTextDefSet(textDefDtos));
    }

    /**
     * write to csv file.
     * @param dtos
     */
    private void writeToCsv(List<TextCsvDto> dtos) {
        StringBuilder stringBuilder = new StringBuilder("textId,en,ja\n");
        for (TextCsvDto text : dtos) {
            stringBuilder.append('"').append(text.getTextId()).append('"').append(",")
                    .append('"').append(text.getEn()).append('"').append(",")
                    .append('"').append(text.getJa()).append('"').append(",\n");
        }
        try {
            FileUtil.writeString("csv/output.csv",stringBuilder.toString());
            log.info("write to csv file:{}","csv/output.csv");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("write to file,",e);
        }

    }

    private List<TextCsvDto> fromTextDefSet(Set<TextDefDto> set) {
        Map<String, TextCsvDto> map = new HashMap<>();
        for (TextDefDto dto : set) {
            if (map.get(dto.getTextId()) == null) {
                if (dto.getLanguage().equals("en")) {
                    map.put(dto.getTextId(), new TextCsvDto(dto.getTextId(), dto.getValue(), null));
                }
                if (dto.getLanguage().equals("ja")) {
                    map.put(dto.getTextId(), new TextCsvDto(dto.getTextId(), null, dto.getValue()));
                }
            } else {
                if (dto.getLanguage().equals("en")) {
                    map.get(dto.getTextId()).setEn(dto.getValue());
                }
                if (dto.getLanguage().equals("ja")) {
                    map.get(dto.getTextId()).setJa(dto.getValue());
                }
            }
        }
        return map.keySet().stream()
                .map(it -> map.get(it))
                .sorted()
                .collect(Collectors.toList());

    }


    /**
     * sample json:  {"key":{"class":"com.worksap.company.framework.textresource.TextResourceKey","pl":"{\"textId\":\"recruiting.applicant_communication.mail_sending.recommended_reason_often_used\",\"locale\":\"ja\",\"plural\":\"\"}"},"value":"よく使用される","systemFlg":false}
     *
     * @param json
     */
    private TextDefDto readjson(String json) {
        TextDefDto textDefDto = new TextDefDto();
        String regex = "textId\\\\\\\":\\\\\\\"(.*?)\\\\\\\",\\\\\\\"locale\\\\\\\":\\\\\\\"(.*)\\\\\\\",\\\\\\\"plural.*value\\\":\\\"(.*)\\\",\\\"systemFlg";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            //   log.info("textid:{},locale:{},value:{}", matcher.group(1), matcher.group(2), matcher.group(3));
            String value=matcher.group(3);
            //字段中的双引号用连续2个双引号表示。同时，该字段必须用双引号包住。
            value=value.replace("\\\"","\"\"");
            textDefDto = new TextDefDto(matcher.group(1), matcher.group(2), value);
        }
        return textDefDto;
    }

    /**
     * format to json string
     * @param textDefDtos
     * @return json string
     */
    private  String getTextDefJson(List<TextDefDto> textDefDtos) {
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
