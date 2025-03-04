package com.company.demo.view.other;


import com.company.demo.view.action.MyCloseAction;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

// tag::otherViewFull[]
@Route(value = "other-view", layout = DefaultMainViewParent.class)
@ViewController(id = "OtherView")
@ViewDescriptor(path = "other-view.xml")
public class OtherView extends StandardView {

    // tag::resultField[]
    private String result;

    public String getResult() {
        return result;
    }

    @Subscribe(id = "saveBtn", subject = "clickListener")
    public void onSaveBtnClick(final ClickEvent<JmixButton> event) {
        result = "Save";
        close(StandardOutcome.SAVE);    // <1>
    }

    @Subscribe(id = "closeBtn", subject = "clickListener")
    public void onCloseBtnClick(final ClickEvent<JmixButton> event) {
        result = "Close";
        close(StandardOutcome.CLOSE);   // <2>
    }
    // end::resultField[]
    // tag::closeAction[]
    @Subscribe(id = "okBtn", subject = "clickListener")
    public void onOkBtnClick(final ClickEvent<JmixButton> event) {
        close(new MyCloseAction("Done"));   // <1>
    }

    @Subscribe(id = "cancelBtn", subject = "clickListener")
    public void onCancelBtnClick(final ClickEvent<JmixButton> event) {
        closeWithDefaultAction();   // <2>
    }
    // end::closeAction[]
}
// end::otherViewFull[]