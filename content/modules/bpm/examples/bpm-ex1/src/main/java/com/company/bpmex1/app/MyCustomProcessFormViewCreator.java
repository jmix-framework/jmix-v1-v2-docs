package com.company.bpmex1.app;

import com.company.bpmex1.view.forms.AcceptsProcessDefinition;
import com.company.bpmex1.view.forms.AcceptsTask;
import io.jmix.bpm.data.form.FormData;
import io.jmix.bpmflowui.processform.viewcreator.ProcessFormViewCreator;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.view.DialogWindow;
import io.jmix.flowui.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


// tag::custom[]
@Component("MyCustomProcessFormViewCreator")
@Order(1) // <1>
public class MyCustomProcessFormViewCreator implements ProcessFormViewCreator {

    @Autowired
    private DialogWindows dialogWindows;

    @Override
    public String isApplicableFor() {
        return "custom";
    }

    @Override
    public DialogWindow<?> createStartProcessView(CreationContext creationContext) { // <2>
        FormData formData = creationContext.getFormData();
        View origin = creationContext.getOrigin();
        String viewId = formData.getScreenId();

        DialogWindow dialog = dialogWindows
                .view(origin, viewId)
                .open();

        if (dialog.getView() instanceof AcceptsProcessDefinition) { // <3>
            ((AcceptsProcessDefinition) dialog.getView())
                    .setProcessDefinition(creationContext.getProcessDefinition());
        }
        return dialog;
    }

    @Override
    public DialogWindow<?> createUserTaskView(CreationContext creationContext) { // <4>
        FormData formData = creationContext.getFormData();
        View origin = creationContext.getOrigin();

        String viewId = formData.getScreenId();
        DialogWindow dialog = dialogWindows
                .view(origin, viewId)
                .build();

        if (dialog.getView() instanceof AcceptsTask) { // <5>
            ((AcceptsTask) dialog.getView())
                    .setTask(creationContext.getTask());
        }
        return dialog;
    }
}

// end::custom[]