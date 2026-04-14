package com.company.bpmex1.view.forms;

import org.flowable.engine.repository.ProcessDefinition;

// tag::custom[]
public interface AcceptsProcessDefinition {
    void setProcessDefinition(ProcessDefinition processDefinition);
}
// end::custom[]