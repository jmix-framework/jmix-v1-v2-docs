package com.company.demo.view.user;

import com.company.demo.entity.User;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "users-lookup", layout = DefaultMainViewParent.class)
@ViewController(id = "User.lookup")
@ViewDescriptor(path = "user-lookup-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserLookupView extends StandardListView<User> {
}