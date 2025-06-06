package com.company.masqueradeex1.test_support.view.sys;



import io.jmix.masquerade.TestView;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.PasswordField;
import io.jmix.masquerade.component.TextField;
import io.jmix.masquerade.sys.View;
import org.openqa.selenium.support.FindBy;

// tag::LoginViewWrapper[]
@TestView
public class LoginView extends View<LoginView> {

    @FindBy(css = "[slot='submit']")
    private Button button;

    @FindBy(id = "vaadinLoginUsername")
    private TextField username;

    @FindBy(id = "vaadinLoginPassword")
    private PasswordField password;

    public Button getButton() {
        return button;
    }

    public TextField getUsernameField() {
        return username;
    }

    public PasswordField getPasswordField() {
        return password;
    }
}

// end::LoginViewWrapper[]
