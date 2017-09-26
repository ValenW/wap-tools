package com.wap.tools;

import com.wap.tools.service.TextResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zuo_ji on 17-9-8.
 */
public class Application {
    private static Logger log = LoggerFactory.getLogger(Application.class);

    private TextResourceService textResourceService;

    public Application(){
        this.textResourceService=new TextResourceService();
    }

    public void genJson() {
        String csvPath = System.getProperty("user.dir") + "/csv/output.csv";
        String jsonPath = "json/";
        textResourceService.genTextJson(csvPath, jsonPath);
    }

    public void readFromJson() {
//        String path = "/home/zuo_ji/HUE/WorkSpace/Develop/hue-hr-recruiting/hue-hr-recruiting-version-up/src/main/resources/cassandra/data/TextDefDto";
        String path = "/home/zuo_ji/Desktop/json/";
        try {
            textResourceService.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Application().genJson();
//        new Application().readFromJson();

    }
}

