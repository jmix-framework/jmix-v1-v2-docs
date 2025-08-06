package com.company.masqueradeex1.test_support.view.user;

import io.jmix.masquerade.TestView;
import io.jmix.masquerade.sys.DialogWindow;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static io.jmix.masquerade.JConditions.ENABLED;
import static io.jmix.masquerade.JConditions.VISIBLE;
import static io.jmix.masquerade.JSelectors.byChained;
import static io.jmix.masquerade.JSelectors.byUiTestId;

// tag::UserListDialog[]
@TestView(id = "User.list")
public class UserListDialog extends DialogWindow<UserListDialog> {

    public UserListDialog selectAdmin() {
        $(By.xpath("//*[@id=\"usersDataGrid\"]/vaadin-grid-cell-content[22]"))
                .click();

        $(byChained(getBy(), byUiTestId("selectButton")))
                .shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click();
        return this;
    }
}
// end::UserListDialog[]
