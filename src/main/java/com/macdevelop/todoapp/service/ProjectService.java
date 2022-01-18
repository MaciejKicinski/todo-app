package com.macdevelop.todoapp.service;

import com.macdevelop.todoapp.configuration.TaskConfigurationProperties;
import com.macdevelop.todoapp.model.Project;
import com.macdevelop.todoapp.model.TaskGroup;
import com.macdevelop.todoapp.model.projection.GroupReadModel;
import com.macdevelop.todoapp.model.projection.GroupTaskWriteModel;
import com.macdevelop.todoapp.model.projection.GroupWriteModel;
import com.macdevelop.todoapp.repository.ProjectRepository;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(final Project toSave) {
        projectRepository.save(toSave);
        return toSave;
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId) && !config.getTemplate().isAllowMultipleTasks()) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        GroupWriteModel groupWriteModel = projectRepository.findById(projectId)
                .map(project -> {
                    var result = new GroupWriteModel();
                    result.setDescription(project.getDescription());
                    result.setTasks(project.getProjectSteps().stream()
                            .map(projectStep -> new GroupTaskWriteModel(
                                    projectStep.getDescription(),
                                    deadline.plusDays(projectStep.getDaysToDeadline())))
                            .collect(Collectors.toSet())
                    );
                    return result;
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return taskGroupService.createGroup(groupWriteModel);
    }
}
