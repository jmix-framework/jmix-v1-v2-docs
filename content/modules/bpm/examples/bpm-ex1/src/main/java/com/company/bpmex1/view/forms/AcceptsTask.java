package com.company.bpmex1.view.forms;


import org.flowable.task.api.Task;

// tag::custom[]
public interface AcceptsTask {
    void setTask(Task task);
}
// end::custom[]