package com.kwgdev.msscbeerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * created by kw on 1/3/2021 @ 1:20 PM
 */
@Configuration
@EnableScheduling
@EnableAsync
public class TaskConfig {

    @Bean // inject into Spring Context
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
