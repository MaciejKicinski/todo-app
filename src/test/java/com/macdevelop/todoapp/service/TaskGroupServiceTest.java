package com.macdevelop.todoapp.service;

import com.macdevelop.todoapp.model.TaskGroup;
import com.macdevelop.todoapp.repository.TaskGroupRepository;
import com.macdevelop.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when undone tasks.")
    void toggleGroup_undoneTasks_throwsIllegalStateException() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        // and
        var toTest = new TaskGroupService(null, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        // then
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Group has undone tasks.");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when done tasks and no group for given id.")
    void toggleGroup_doneTasks_noTaskGroup_throwsIllegalArgumentException() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        TaskGroupRepository mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());

        // and
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("Should toggle group when tasks are done and group task exist for given id.")
    void toggleGroup_worksAsExpected() {
        // given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        // and
        var group = new TaskGroup();
        var beforeToggle = group.isDone();
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);

        //when
        toTest.toggleGroup(0);

        // then
        assertThat(group.isDone()).isNotEqualTo(beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean b) {
        var mockTaskpRepository = mock(TaskRepository.class);
        when(mockTaskpRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(b);
        return mockTaskpRepository;
    }
}