package com.company.bpmex1.security;
// tag::role[]
import io.jmix.bpm.entity.*;
import io.jmix.security.model.*;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "BPM: process task performer", code = BpmProcessTaskPerformerRole.CODE, scope = SecurityScope.UI)
public interface BpmProcessTaskPerformerRole {
    String CODE = "bpm-process-task-performer";

    @ViewPolicy(viewIds = {
            "AdvancedTaskListView", // <1>
            "bpm_DefaultStartProcessForm",
            "bpm_DefaultTaskProcessForm",
            "bpm_InputDialogStartProcessForm",
            "bpm_InputDialogTaskProcessForm"
    })
    @MenuPolicy(menuIds = {
            "AdvancedTaskListView" // <2>
    })
    @EntityPolicy(entityClass = ContentStorage.class, actions = {EntityPolicyAction.READ})
    @EntityPolicy(entityClass = ProcessDefinitionData.class, actions = {EntityPolicyAction.READ})
    @EntityPolicy(entityClass = TaskData.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = ContentStorage.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = ProcessDefinitionData.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = TaskData.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void bpmProcessTaskPerformer();
}
// end::role[]