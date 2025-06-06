package com.company.masqueradeex1.test_support.view;


import com.vaadin.flow.component.textfield.TextArea;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.DropdownButton;
import io.jmix.masquerade.sys.View;

import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.TestView;
import io.jmix.masquerade.component.EntityComboBox;
import org.openqa.selenium.support.FindBy;


// tag::MyViewWrapper[]
@TestView(id = "MyView") // <1>
public class MyView extends View<MyView> { // <2>

    // tag::ComponentWrappers[]
    @TestComponent
    private EntityComboBox entityComboBox;

    @TestComponent(path = "myButton")
    private Button button;

    @FindBy(xpath = "//vaadin-text-area[@class='my-text-area']")
    private TextArea textArea;
    // end::ComponentWrappers[]

    public EntityComboBox getEntityComboBox() {
        return entityComboBox;
    }

    public Button getButton() {
        return button;
    }
}
// end::MyViewWrapper[]