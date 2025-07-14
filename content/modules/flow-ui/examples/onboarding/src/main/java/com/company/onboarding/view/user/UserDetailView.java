package com.company.onboarding.view.user;

import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.Step;
import com.company.onboarding.entity.User;
import com.company.onboarding.entity.UserStep;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.KeyCombination;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.util.OperationResult;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Route(value = "users/:id", layout = MainView.class)
@ViewController("User.detail")
@ViewDescriptor("user-detail-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailView extends StandardDetailView<User> {

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private PasswordField passwordField;
    @ViewComponent
    private PasswordField confirmPasswordField;
    @ViewComponent
    private ComboBox<String> timeZoneField;

    @Autowired
    private EntityStates entityStates;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private DataGrid<UserStep> stepsDataGrid;
    @Autowired
    private UiComponents uiComponents;

    // tag::filter[]
    @ViewComponent
    private CollectionPropertyContainer<UserStep> stepsDc;
    @Autowired
    private MetadataTools metadataTools;

    private void filterByDueDate(LocalDate dueDate) {
        List<UserStep> filtered = getEditedEntity().getSteps().stream()
                .filter(userStep -> userStep.getDueDate().isAfter(dueDate))
                .collect(Collectors.toList());
        stepsDc.setDisconnectedItems(filtered);
    }

    private void resetFilter() {
        stepsDc.setDisconnectedItems(getEditedEntity().getSteps());
    }
    // end::filter[]

    // tag::keyboard-shortcuts-injects[]
    @ViewComponent
    private JmixButton saveAndCloseBtn;
    @ViewComponent
    private JmixButton closeBtn;

    // end::keyboard-shortcuts-injects[]

    // tag::on-init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
    // end::on-init-start[]
        timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));

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
                }))
                .setWidth("50px"); // width doesn't work

        stepsDataGrid.setColumnPosition(checkboxColumn, 0);
        // tag::keyboard-shortcuts[]
        saveAndCloseBtn.addClickShortcut(Key.ENTER, KeyModifier.META)
                .resetFocusOnActiveElement();

        KeyCombination keyCombination = KeyCombination.create(Key.ESCAPE, KeyModifier.SHIFT);
        keyCombination.setResetFocusOnActiveElement(true);
        closeBtn.setShortcutCombination(keyCombination);
        // end::keyboard-shortcuts[]
    // tag::on-init-end[]
    }
    // end::on-init-end[]

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        usernameField.setReadOnly(false);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);

        event.getEntity().setOnboardingStatus(OnboardingStatus.NOT_STARTED);
    }

    @Subscribe
    public void onReady(ReadyEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    protected void onBeforeCommit(BeforeSaveEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(messageBundle.getMessage("passwordsDoNotMatch"))
                        .withType(Notifications.Type.WARNING)
                        .show();
                event.preventSave();
            }
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }

    @Subscribe("generateStepsButton")
    public void onGenerateStepsButtonClick(ClickEvent<Button> event) {
        User user = getEditedEntity();

        if (user.getJoiningDate() == null) {
            notifications.show("Cannot generate steps for user without 'Joining date'");
            return;
        }

        List<Step> steps = dataManager.load(Step.class)
                .query("select s from Step s order by s.sortValue asc")
                .list();

        for (Step step : steps) {
            if (stepsDc.getItems().stream().noneMatch(userStep ->
                    userStep.getStep().equals(step))) {
                UserStep userStep = dataContext.create(UserStep.class);
                userStep.setUser(user);
                userStep.setStep(step);
                userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
                userStep.setSortValue(step.getSortValue());
                stepsDc.getMutableItems().add(userStep);
            }
        }
    }

    @Subscribe(id = "stepsDc", target = Target.DATA_CONTAINER)
    public void onStepsDcCollectionChange(CollectionContainer.CollectionChangeEvent<UserStep> event) {
        updateOnboardingStatus();
    }

    @Subscribe(id = "stepsDc", target = Target.DATA_CONTAINER)
    public void onStepsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<UserStep> event) {
        updateOnboardingStatus();
    }

    private void updateOnboardingStatus() {
        User user = getEditedEntity();

        long completedCount = user.getSteps() == null ? 0 :
                user.getSteps().stream()
                        .filter(us -> us.getCompletedDate() != null)
                        .count();
        if (completedCount == 0) {
            user.setOnboardingStatus(OnboardingStatus.NOT_STARTED);
        } else if (completedCount == user.getSteps().size()) {
            user.setOnboardingStatus(OnboardingStatus.COMPLETED);
        } else {
            user.setOnboardingStatus(OnboardingStatus.IN_PROGRESS);
        }
    }

    // tag::page-title[]
    @Override
    public String getPageTitle() {
        User user = getEditedEntity();
        return entityStates.isNew(user)
                ? messageBundle.getMessage("newUserTitle")
                : messageBundle.formatMessage("editUserTitle", metadataTools.getInstanceName(user));

    }
    // end::page-title[]

    // tag::overridden-save-method[]
    @Override
    public OperationResult save() {
        return super.save()     // <1>
                .then(() -> {
                    // ...      // <2>
                });
    }
    // end::overridden-save-method[]
}