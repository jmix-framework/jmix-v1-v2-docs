package com.company.demo.view.user;

import com.company.demo.entity.User;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.tabbedmode.event.UiRefreshEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

// tag::userListView[]
@Route(value = "users", layout = MainView.class)
@ViewController(id = "User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {
    // tag::debug[]
    @Autowired
    private Notifications notifications;

    @Subscribe(id = "usersDc", target = Target.DATA_CONTAINER)
    public void onUsersDcCollectionChange(final CollectionContainer.CollectionChangeEvent<User> event) {
        notifications.create("CollectionChangeEvent", event.getChangeType().name())
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
    // end::debug[]

    @EventListener
    public void onPageRefresh(UiRefreshEvent event) {
        getViewData().loadAll();
    }
}
// end::userListView[]