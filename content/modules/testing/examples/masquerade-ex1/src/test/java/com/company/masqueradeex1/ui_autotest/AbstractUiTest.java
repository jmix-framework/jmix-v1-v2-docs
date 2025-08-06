package com.company.masqueradeex1.ui_autotest;

import com.codeborne.selenide.Selenide;
import com.company.masqueradeex1.test_support.view.sys.LoginView;
import com.company.masqueradeex1.test_support.view.sys.MainView;
import org.junit.jupiter.api.AfterEach;

import static com.codeborne.selenide.Condition.text;
import static io.jmix.masquerade.JConditions.value;
import static io.jmix.masquerade.Masquerade.$j;

public abstract class AbstractUiTest {

    @AfterEach
    public void afterEach() {
        Selenide.closeWebDriver();
    }

    protected MainView loginAsAdmin() {
        Selenide.open("/");

        LoginView loginView = $j(LoginView.class);

        loginView.getUsernameField().shouldHave(value("admin"));
        loginView.getUsernameField().setValue("");
        loginView.getUsernameField().setValue("admin");

        loginView.getPasswordField().shouldHave(value("admin"));
        loginView.getPasswordField().setValue("");
        loginView.getPasswordField().setValue("admin");

        loginView.getButton().shouldHave(text("Log in"));
        loginView.getButton().click();

        MainView mainView = $j(MainView.class);
        return mainView.exists();
    }
}