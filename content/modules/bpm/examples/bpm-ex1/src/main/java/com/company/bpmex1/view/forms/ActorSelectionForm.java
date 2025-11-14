package com.company.bpmex1.view.forms;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpm.data.form.FormParam;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Param;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessFormParam;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ViewController("ActorSelectionForm")
@ViewDescriptor("actor-selection-form.xml")
// tag::actor-form1[]
// tag::params[]
@ProcessForm(
        params = {
                @Param(name = "nextActor"),
                @Param(name = "entityPickerCaption")
        }
)
// end::params[]
public class ActorSelectionForm extends StandardView {

    @Autowired
    private ProcessFormContext processFormContext;

    @ViewComponent
    private EntityPicker<String> userEntityPicker;

    // tag::params-annotation[]

    //...

    @ProcessFormParam
    private String nextActor;

    @ProcessFormParam
    private String entityPickerCaption;
    // end::params-annotation[]

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        userEntityPicker.setLabel(entityPickerCaption);
    }

    @Subscribe("completeTaskBtn")
    private void onCompleteTaskBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .addProcessVariable(nextActor, userEntityPicker.getValue())
                .complete();
        closeWithDefaultAction();
    }
    // end::actor-form1[]

    private void getParametersList() {
        // tag::param-list[]
        List<FormParam> formParams = processFormContext.getFormData().getFormParams();
        //end::param-list[]
    }

    // tag::actor-form2[]
}
// end::actor-form2[]