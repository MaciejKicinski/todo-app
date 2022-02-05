package com.macdevelop.todoapp.model.event;

import com.macdevelop.todoapp.model.Task;

import java.time.Clock;

public class TaskUnDone extends TaskEvent {
    TaskUnDone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
