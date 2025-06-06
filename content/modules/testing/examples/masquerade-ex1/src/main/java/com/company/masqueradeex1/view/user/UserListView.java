package com.company.masqueradeex1.view.user;

import com.company.masqueradeex1.entity.User;
import com.company.masqueradeex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @Autowired
    private Notifications notifications;
    @ViewComponent
    private DataGrid<User> usersDataGrid;

    @Subscribe(id = "showUsername", subject = "clickListener")
    public void onShowUsernameClick(final ClickEvent<JmixButton> event) {
        User singleSelectedItem = usersDataGrid.getSingleSelectedItem();
        if (singleSelectedItem == null) {
            return;
        }

        notifications.create("Username:", singleSelectedItem.getUsername())
                .withThemeVariant(NotificationVariant.LUMO_SUCCESS)
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
}