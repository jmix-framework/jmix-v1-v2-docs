package com.company.onboarding.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

@Tag("test-component")
public class TestComponent extends Component {

    public void setText(String text) {

        if (text != null && !text.isEmpty()) {
            getElement().appendChild(Element.createText(text));
        }
    }
}