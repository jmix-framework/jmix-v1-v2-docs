package com.company.onboarding.view.myonboarding;

import com.company.onboarding.entity.User;
import com.company.onboarding.entity.UserStep;
import com.company.onboarding.view.DataGridHelper;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

// tag::annotations[]
@ViewController("MyOnboardingView")
@ViewDescriptor("my-onboarding-view.xml")
@Route(value = "my-onboarding", layout = MainView.class)
@DialogMode(width = "50em", height = "40em")
public class MyOnboardingView extends StandardView {
// end::annotations[]

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private UiComponents uiComponents;

    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionContainer<UserStep> userStepsDc;
    @ViewComponent
    private CollectionLoader<UserStep> userStepsDl;
    @ViewComponent
    private DataGrid<UserStep> stepsDataGrid;
    @ViewComponent
    private Div totalStepsLabel;
    @ViewComponent
    private Div overdueStepsLabel;
    @ViewComponent
    private Div completedStepsLabel;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        Grid.Column<UserStep> checkboxColumn = stepsDataGrid.addColumn(new ComponentRenderer<>(userStep -> {
                    Checkbox checkbox = uiComponents.create(Checkbox.class);
                    checkbox.setValue(userStep.getCompletedDate() != null);
                    checkbox.addValueChangeListener(e -> {
                        if (userStep.getCompletedDate() == null) {
                            userStep.setCompletedDate(LocalDate.now());
                        } else {
                            userStep.setCompletedDate(null);
                        }
                    });
                    return checkbox;
                }));

        DataGridHelper.setDataGridColumnPosition(stepsDataGrid, checkboxColumn, 0);

        stepsDataGrid.getColumnByKey("dueDate")
                .setClassNameGenerator(userStep ->
                        isOverdue(userStep) ? "overdue-step" : null);

    }

    // tag::onBeforeShow[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        // end::onBeforeShow[]
        User user = (User) currentAuthentication.getUser();
        userStepsDl.setParameter("user", user);
        userStepsDl.load();

        updateLabels();
// tag::set-size[]
        Dialog dialog = UiComponentUtils.findDialog(this);
        if (dialog != null) {
            dialog.setWidth("80em");
            dialog.setHeight("60em");
        }
        // end::set-size[]
        // tag::onBeforeShow[]
    }
    // end::onBeforeShow[]
    @Subscribe(id = "userStepsDc", target = Target.DATA_CONTAINER)
    public void onUserStepsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<UserStep> event) {
        updateLabels();
    }

    private void updateLabels() {
        totalStepsLabel.setText("Total steps: " + userStepsDc.getItems().size());

        long completedCount = userStepsDc.getItems().stream()
                .filter(us -> us.getCompletedDate() != null)
                .count();
        completedStepsLabel.setText("Completed steps: " + completedCount);

        long overdueCount = userStepsDc.getItems().stream()
                .filter(us -> isOverdue(us))
                .count();
        overdueStepsLabel.setText("Overdue steps: " + overdueCount);
    }

    private boolean isOverdue(UserStep us) {
        return us.getCompletedDate() == null
                && us.getDueDate() != null
                && us.getDueDate().isBefore(LocalDate.now());
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(ClickEvent<Button> event) {
        dataContext.save();
        close(StandardOutcome.SAVE);
    }

    // tag::close-with-discard[]
    @Subscribe("discardButton")
    public void onDiscardButtonClick(ClickEvent<Button> event) {
        close(StandardOutcome.DISCARD);
    }
    // end::close-with-discard[]

    @Install(to = "stepsDataGrid", subject = "classNameGenerator")
    private String stepsDataGridClassNameGenerator(UserStep entity) {
        return null;
    }
}