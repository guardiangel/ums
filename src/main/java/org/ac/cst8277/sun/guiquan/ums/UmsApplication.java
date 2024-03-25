package org.ac.cst8277.sun.guiquan.ums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication()
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ConfigurationPropertiesScan
public class UmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class, args);
    }

}
