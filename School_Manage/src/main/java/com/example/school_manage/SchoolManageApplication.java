package com.example.school_manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author aone
 */
@SpringBootApplication
@MapperScan("com.example.school_manage.mapper")
@EnableScheduling
public class SchoolManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolManageApplication.class, args);
	}

}
