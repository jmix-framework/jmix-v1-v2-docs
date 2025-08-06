package com.company.masqueradeex1.test_support.view.user;

import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.TestView;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.Checkbox;
import io.jmix.masquerade.component.PasswordField;
import io.jmix.masquerade.component.TextField;
import io.jmix.masquerade.sys.View;

@TestView(id = "User.detail")
public class UserDetailView extends View<UserDetailView> {

    @TestComponent
    private TextField usernameField;
    @TestComponent
    private PasswordField passwordField;
    @TestComponent
    private PasswordField confirmPasswordField;
    @TestComponent
    private Checkbox activeField;

    @TestComponent
    private Button saveAndCloseButton;

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public Checkbox getActiveField() {
        return activeField;
    }

    public void save() {
        saveAndCloseButton.click();
    }
}