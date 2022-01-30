package com.macdevelop.todoapp.service;

import com.macdevelop.todoapp.configuration.TaskConfigurationProperties;
import com.macdevelop.todoapp.repository.ProjectRepository;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import com.macdevelop.todoapp.repository.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(final ProjectRepository repository,
                                  final TaskGroupRepository taskGroupRepository,
                                  final TaskConfigurationProperties config,
                                  final TaskGroupService taskGroupService) {
        return new ProjectService(repository, taskGroupRepository, config, taskGroupService);
    }

    @Bean
    TaskGroupService taskGroupService(final TaskGroupRepository taskGroupRepository,
                                      final TaskRepository taskRepository) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }

}
