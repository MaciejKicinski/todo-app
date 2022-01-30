package com.macdevelop.todoapp.service;

import com.macdevelop.todoapp.model.Project;
import com.macdevelop.todoapp.model.TaskGroup;
import com.macdevelop.todoapp.model.projection.GroupReadModel;
import com.macdevelop.todoapp.model.projection.GroupWriteModel;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import com.macdevelop.todoapp.repository.TaskRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source, null);
    }

    GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first.");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }


}
