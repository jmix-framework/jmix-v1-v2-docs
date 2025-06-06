package com.company.masqueradeex1.ui_autotest;

import com.company.masqueradeex1.test_support.view.sys.MainView;
import com.company.masqueradeex1.test_support.view.user.UserListView;
import io.jmix.masquerade.component.Notification;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static io.jmix.masquerade.JConditions.*;
import static io.jmix.masquerade.JConditions.EXIST;
import static io.jmix.masquerade.Masquerade.$j;

public class NotificationUiTest extends AbstractUiTest  {

    // tag::NotificationTest[]
    @Test
    public void notificationTest() {
        MainView mainView = loginAsAdmin();

        UserListView userListView = mainView.openItem(UserListView.class,
                "applicationListItem", "user.listListItem");

        userListView.showUsername();

        Notification notification = $j(Notification.class);
        notification
                .shouldBe(VISIBLE)
                .shouldHave(notificationPosition(Notification.Position.BOTTOM_END))
                .shouldHave(notificationTheme(Notification.Theme.SUCCESS))
                .shouldHave(notificationTitle("Username:"))
                .should(notificationTitleContains("name:"))
                .shouldHave(notificationMessage("test"))
                .should(notificationMessageContains("te"));

        sleep(3000);

        notification.shouldNotBe(EXIST);
    }
    // end::NotificationTest[]
}
