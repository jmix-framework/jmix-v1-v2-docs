package com.company.masqueradeex1.ui_autotest;

import com.codeborne.selenide.Selenide;
import com.company.masqueradeex1.test_support.view.sys.LoginView;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static io.jmix.masquerade.Masquerade.$j;

// tag::LoginUiTest[]
public class LoginUiTest {

    @Test
    public void loginAsAdmin() {

        Selenide.open("/"); // <1>

        LoginView loginView = $j(LoginView.class); // <2>

        loginView.getUsernameField()
                .shouldHave(value("admin"))
                .setValue("")
                .setValue("admin");

        loginView.getPasswordField()
                .shouldHave(value("admin"))
                .setValue("")
                .setValue("admin");

        loginView.getButton()
                .shouldHave(text("Log in"))
                .click();
    }

}
// end::LoginUiTest[]
