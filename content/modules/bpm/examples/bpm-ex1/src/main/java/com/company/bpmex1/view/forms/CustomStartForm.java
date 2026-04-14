package com.company.bpmex1.view.forms;


import com.company.bpmex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;


// tag::custom[]
@ViewController(id = "CustomStartForm")
@ViewDescriptor(path = "custom-start-form.xml")
public class CustomStartForm extends StandardView implements AcceptsProcessDefinition {

    private ProcessDefinition processDefinition;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    @Subscribe(id = "startProcessBtn", subject = "clickListener")
    public void onStartProcessBtnClick(final ClickEvent<JmixButton> event) {
        runtimeService.startProcessInstanceByKey(processDefinition.getKey());
        closeWithDefaultAction();
    }
}
// end::custom[]