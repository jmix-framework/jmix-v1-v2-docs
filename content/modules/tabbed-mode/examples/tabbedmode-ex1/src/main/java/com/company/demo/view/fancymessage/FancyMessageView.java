package com.company.demo.view.fancymessage;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

// tag::fancyMessageViewFull[]
@Route(value = "fancy-message-view", layout = DefaultMainViewParent.class)
@ViewController(id = "FancyMessageView")
@ViewDescriptor(path = "fancy-message-view.xml")
public class FancyMessageView extends StandardView {

    @ViewComponent
    private H1 fancyMessage;

    public void setMessage(String message) {
        fancyMessage.setText(message);
    }
}
// end::fancyMessageViewFull[]