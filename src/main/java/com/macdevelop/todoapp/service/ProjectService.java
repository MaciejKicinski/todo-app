package com.macdevelop.todoapp.service;

import com.macdevelop.todoapp.configuration.TaskConfigurationProperties;
import com.macdevelop.todoapp.model.Project;
import com.macdevelop.todoapp.model.projection.GroupReadModel;
import com.macdevelop.todoapp.model.projection.GroupTaskWriteModel;
import com.macdevelop.todoapp.model.projection.GroupWriteModel;
import com.macdevelop.todoapp.model.projection.ProjectWriteModel;
import com.macdevelop.todoapp.repository.ProjectRepository;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(final ProjectWriteModel toSave) {
        return projectRepository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getProjectSteps().stream()
                                    .map(projectStep -> {
                                                var task = new GroupTaskWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toSet())
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }
}
