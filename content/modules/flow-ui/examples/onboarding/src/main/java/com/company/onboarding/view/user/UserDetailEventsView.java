package com.company.onboarding.view.user;

import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.EntityStates;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Route(value = "users-detail-events-view/:id", layout = MainView.class)
@ViewController("UserDetailEventsView")
@ViewDescriptor("user-detail-events-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailEventsView extends StandardDetailView<User> {

    // tag::notifications-bean[]
    @Autowired
    private Notifications notifications;

    // end::notifications-bean[]

    // tag::timeZoneField[]
    @ViewComponent
    private JmixComboBox<String> timeZoneField;

    // end::timeZoneField[]

    // tag::init-event[]
    @Subscribe
    public void onInit(final InitEvent event) {
        timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
    }
    // end::init-event[]

    // tag::init-entity-event[]
    @Subscribe
    public void onInitEntity(final InitEntityEvent<User> event) {
        User user = event.getEntity();
        user.setOnboardingStatus(OnboardingStatus.NOT_STARTED);
    }
    // end::init-entity-event[]

    // tag::validation-event[]
    @Subscribe
    public void onValidation(final ValidationEvent event) {
        if (entityStates.isNew(getEditedEntity())
                && !Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
            event.getErrors().add("Passwords do not match");
        }
    }
    // end::validation-event[]

    // tag::before-save-event[]
    @ViewComponent
    private JmixPasswordField passwordField;
    @ViewComponent
    private JmixPasswordField confirmPasswordField;

    @Autowired
    private EntityStates entityStates;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create("Passwords do not match")
                        .withType(Notifications.Type.WARNING)
                        .show();
                event.preventSave(); // <1>
            }

            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue())); // <2>
        }
    }
    // end::before-save-event[]

    // tag::after-save-event[]
    @Subscribe
    public void onAfterSave(final AfterSaveEvent event) {
        notifications.create("Entity saved successfully")
                .withType(Notifications.Type.SUCCESS)
                .withPosition(Notification.Position.TOP_END)
                .show();
    }
    // end::after-save-event[]
    // tag::read-only-change-event[]
    @Subscribe
    public void onReadOnlyChange(final ReadOnlyChangeEvent event) {
        timeZoneField.setReadOnly(event.isReadOnly());
    }
    // end::read-only-change-event[]
}