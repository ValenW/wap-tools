package com.worksap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * SpringBoot-MyBatis Application.
 *
 * @author Xiaoyue Xiao
 */
@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
