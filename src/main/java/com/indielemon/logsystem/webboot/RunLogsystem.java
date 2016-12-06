package com.indielemon.logsystem.webboot;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication
public class RunLogsystem {

	public static void main(String[] args) throws Exception {

		Object[] configurations = new Object[] { com.indielemon.logsystem.webboot.config.LogsystemConfig.class,
				com.indielemon.logsystem.webboot.config.WebSecurityConfig.class, com.indielemon.logsystem.webboot.config.LdapConfig.class,
				com.indielemon.logsystem.webboot.config.I18nConfig.class, com.indielemon.logsystem.webboot.config.ContentResolverConfig.class };
		ConfigurableApplicationContext context = SpringApplication.run(configurations, new String[] {});
	}

}