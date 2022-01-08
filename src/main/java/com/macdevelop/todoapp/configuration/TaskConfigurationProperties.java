package com.macdevelop.todoapp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
    private Template template;

    @Setter
    @Getter
    public static class Template {
        private boolean allowMultipleTasks;
    }
}
