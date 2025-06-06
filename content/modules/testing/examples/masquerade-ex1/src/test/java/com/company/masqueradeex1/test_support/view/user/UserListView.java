package com.company.masqueradeex1.test_support.view.user;

import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.TestView;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.DataGrid;
import io.jmix.masquerade.sys.View;

import static io.jmix.masquerade.Masquerade.$j;

@TestView(id = "User.list")
public class UserListView extends View<UserListView> {

    @TestComponent
    private Button createButton;

    @TestComponent
    private DataGrid usersDataGrid;

    @TestComponent
    private Button showUsername;

    public UserDetailView createNewUser() {
        createButton.click();
        return $j(UserDetailView.class);
    }

    public DataGrid getUsersDataGrid() {
        return usersDataGrid;
    }

    public void showUsername() {
        usersDataGrid.getRowByIndex(1)
                .click();

        showUsername.click();
    }
}
