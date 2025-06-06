package com.company.masqueradeex1.ui_autotest;

import com.codeborne.selenide.Selenide;
import io.jmix.core.DataManager;
import io.jmix.flowui.ViewNavigators;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static io.jmix.masquerade.JConditions.VISIBLE;
import static io.jmix.masquerade.JSelectors.byChained;


//tag::selenideLogin[]
public class SelenideTest {

    @Test
    void selenideLogin() {
        Selenide.open("/");

        $(byId("vaadinLoginUsername")).shouldHave(value("admin"));
        $(byChained(byId("vaadinLoginUsername"), byTagName("input")))
                .setValue("")
                .setValue("admin");

        $(byId("vaadinLoginPassword")).shouldHave(value("admin"));
        $(byChained(byId("vaadinLoginPassword"), byTagName("input")))
                .setValue("")
                .setValue("admin");

        $(byCssSelector("[slot='submit']")).click();
    }
}
//end::selenideLogin[]
