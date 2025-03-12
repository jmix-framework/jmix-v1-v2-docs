package com.company.demo.view.viewopeningsandbox;


import com.company.demo.entity.User;
import com.company.demo.view.action.MyCloseAction;
import com.company.demo.view.fancymessage.FancyMessageView;
import com.company.demo.view.other.OtherView;
import com.company.demo.view.user.UserLookupView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.tabbedmode.ViewBuilders;
import io.jmix.tabbedmode.view.ViewOpenMode;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "view-opening-sandbox-view", layout = DefaultMainViewParent.class)
@ViewController(id = "ViewOpeningSandboxView")
@ViewDescriptor(path = "view-opening-sandbox-view.xml")
public class ViewOpeningSandboxView extends StandardView {

    // tag::userPicker[]
    @ViewComponent
    private EntityPicker<User> userPicker;

    // end::userPicker[]

    // tag::usersDataGrid[]
    @ViewComponent
    private DataGrid<User> usersDataGrid;

    // end::usersDataGrid[]

    // tag::viewBuilders[]
    @Autowired
    private ViewBuilders viewBuilders;

    // end::viewBuilders[]

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    @Subscribe(id = "openViewBtn", subject = "clickListener")
    public void onOpenViewBtnClick(final ClickEvent<JmixButton> event) {
        openView();
    }

    // tag::openView[]
    private void openView() {
        viewBuilders.view(this, OtherView.class).open();
    }
    // end::openView[]

    // tag::openDetailViewToCreate[]
    private void openDetailViewToCreate() {
        viewBuilders.detail(this, User.class)
                .newEntity()
                .open();
    }
    // end::openDetailViewToCreate[]

    // tag::openDetailViewToEdit[]
    private void openDetailViewToEdit(User user) {
        viewBuilders.detail(this, User.class)
                .editEntity(user)
                .open();
    }
    // end::openDetailViewToEdit[]

    @Subscribe(id = "editBtn", subject = "clickListener")
    public void onEditBtnClick(final ClickEvent<JmixButton> event) {
        openDetailViewDataGridToEdit();
    }

    // tag::openDetailViewDataGridToEdit[]
    private void openDetailViewDataGridToEdit() {
        viewBuilders.detail(usersDataGrid)
                .open();
    }
    // end::openDetailViewDataGridToEdit[]

    @Subscribe(id = "createBtn", subject = "clickListener")
    public void onCreateBtnClick(final ClickEvent<JmixButton> event) {
        openDetailViewDataGridToCreate();
    }

    // tag::openDetailViewDataGridToCreate[]
    private void openDetailViewDataGridToCreate() {
        viewBuilders.detail(usersDataGrid)
                .newEntity()
                .open();
    }
    // end::openDetailViewDataGridToCreate[]

    // tag::openDetailViewFieldToEdit[]
    private void openDetailViewFieldToEdit() {
        viewBuilders.detail(userPicker)
                .open();
    }
    // end::openDetailViewFieldToEdit[]


    // tag::openDetailViewDialog[]
    private void openDetailViewDialog() {
        viewBuilders.detail(this, User.class)
                .newEntity()
                .withInitializer(user -> {
                    user.setTimeZoneId(getDefaultTimeZone());
                })
                .withOpenMode(ViewOpenMode.DIALOG)
                .open();
    }
    // end::openDetailViewDialog[]

    private String getDefaultTimeZone() {
        return "";
    }

    // tag::openLookupView[]
    private void openLookupView() {
        viewBuilders.lookup(this, User.class)
                .withSelectHandler(users -> {
                    User user = users.iterator().next();
                    // ...
                })
                .open();
    }
    // end::openLookupView[]


    @Subscribe(id = "pickUserBtn", subject = "clickListener")
    public void onPickUserBtnClick(final ClickEvent<JmixButton> event) {
        openLookupViewToSelect();
    }

    // tag::openLookupViewSelect[]
    private void openLookupViewToSelect() {
        viewBuilders.lookup(userPicker)
                .open();
    }
    // end::openLookupViewSelect[]

    @Subscribe(id = "addUserBtn", subject = "clickListener")
    public void onAddUserBtnClick(final ClickEvent<JmixButton> event) {
        openLookupViewDataGrid();
    }

    // tag::openLookupViewDataGrid[]
    private void openLookupViewDataGrid() {
        viewBuilders.lookup(usersDataGrid)
                .open();
    }
    // end::openLookupViewDataGrid[]

    @Subscribe(id = "pickUserDialogBtn", subject = "clickListener")
    public void onPickUserDialogBtnClick(final ClickEvent<JmixButton> event) {
        openLookupViewDialog();
    }

    // tag::openLookupViewDialog[]
    private void openLookupViewDialog() {
        viewBuilders.lookup(this, User.class, UserLookupView.class)
                .withOpenMode(ViewOpenMode.DIALOG)
                .withSelectHandler(users -> {
                    User user = users.iterator().next();
                    // ...
                })
                .open();
    }
    // end::openLookupViewDialog[]


    @Subscribe(id = "openViewWithParametersBtn", subject = "clickListener")
    public void onOpenViewWithParametersBtnClick(final ClickEvent<JmixButton> event) {
        openViewWithParameters("Test");
    }

    // tag::openViewWithParameters[]
    private void openViewWithParameters(String message) {
        viewBuilders.view(this, FancyMessageView.class)
                .withViewConfigurer(fancyMessageView -> {
                    fancyMessageView.setMessage(message);
                })
                .open();
    }
    // end::openViewWithParameters[]

    @Subscribe(id = "closeListenerBtn", subject = "clickListener")
    public void onCloseListenerBtnClick(final ClickEvent<JmixButton> event) {
        openViewWithCloseListener();
    }

    // tag::openViewWithCloseListener[]
    private void openViewWithCloseListener() {
        viewBuilders.view(this, OtherView.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    notifications.show("Closed: " + afterCloseEvent.getSource());
                })
                .open();
    }
    // end::openViewWithCloseListener[]

    @Subscribe(id = "closeResultBtn", subject = "clickListener")
    public void onCloseResultBtnClick(final ClickEvent<JmixButton> event) {
        openViewWithResult();
    }

    // tag::openViewWithResult[]
    private void openViewWithResult() {
        viewBuilders.view(this, OtherView.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        OtherView otherView = afterCloseEvent.getSource();
                        notifications.show("Result: " + otherView.getResult());
                    }
                })
                .open();
    }
    // end::openViewWithResult[]

    @Subscribe(id = "closeCloseActionBtn", subject = "clickListener")
    public void onCloseCloseActionBtnClick(final ClickEvent<JmixButton> event) {
        openViewWithCloseAction();
    }

    // tag::openViewWithCloseAction[]
    private void openViewWithCloseAction() {
        viewBuilders.view(this, "OtherView")
                .withAfterCloseListener(afterCloseEvent -> {
                    CloseAction closeAction = afterCloseEvent.getCloseAction();
                    if (closeAction instanceof MyCloseAction myCloseAction) {
                        notifications.show("Result: " + myCloseAction.getResult());
                    }
                })
                .open();
    }
    // end::openViewWithCloseAction[]
}