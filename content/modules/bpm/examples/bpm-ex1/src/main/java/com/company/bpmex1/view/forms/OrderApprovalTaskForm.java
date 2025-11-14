package com.company.bpmex1.view.forms;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::task-example[]
@ViewController("OrderApprovalTaskForm")
@ViewDescriptor("order-approval-task-form.xml")
// tag::outcomes[]
@ProcessForm(
        outcomes = { // <1>
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        }
)
// end::outcomes[]
public class OrderApprovalTaskForm extends StandardView {

    @ViewComponent
    @ProcessVariable // <2>
    private TypedTextField<String> orderNumber;

    @ViewComponent
    @ProcessVariable(name = "comment") // <3>
    private TypedTextField<String> commentField;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .saveInjectedProcessVariables() // <4>
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("reject")
                .addProcessVariable("comment", commentField.getValue()) // <5>
                .complete();
        closeWithDefaultAction();
    }
}
// end::task-example[]