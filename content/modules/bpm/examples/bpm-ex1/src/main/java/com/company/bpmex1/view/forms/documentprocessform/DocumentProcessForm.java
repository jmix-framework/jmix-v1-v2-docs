package com.company.bpmex1.view.forms.documentprocessform;


import com.company.bpmex1.entity.Document;
import com.company.bpmex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.*;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ProcessForm(outcomes = {
        @Outcome(id = "submit"),
        @Outcome(id = "reject")
}, outputVariables = {
        @OutputVariable(name = "documentVar", type = Document.class)
})
@Route(value = "document-process-form", layout = MainView.class)
@ViewController(id = "DocumentProcessForm")
@ViewDescriptor(path = "document-process-form.xml")
public class DocumentProcessForm extends StandardView {

    @Autowired
    private ProcessFormContext processFormContext;


    // tag::process-variable-param-example[]
    @ProcessVariable(name="documentVar", params = {
            @ProcessVariableParam(key = ProcessVariableConstants.LOAD_DYN_ATTR_PARAM, value = "true")
    })
    private Document documentVar;
    // end::process-variable-param-example[]

    @ViewComponent
    DataContext dataContext;
    @ViewComponent
    private InstanceContainer<Document> documentDc;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if (documentVar == null) {
            documentVar = dataContext.create(Document.class);
        }
        documentDc.setItem(dataContext.merge(documentVar));
    }

    @Subscribe(id = "submitBtn", subject = "clickListener")
    protected void onSubmitBtnClick(ClickEvent<JmixButton> event) {
        dataContext.save();
        processFormContext.taskCompletion()
                .withOutcome("submit")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe(id = "rejectBtn", subject = "clickListener")
    protected void onRejectBtnClick(ClickEvent<JmixButton> event) {
        dataContext.save();
        processFormContext.taskCompletion()
                .withOutcome("reject")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }
}