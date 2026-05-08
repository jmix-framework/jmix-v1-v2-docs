package com.company.onboarding.listener;

import com.company.onboarding.entity.OnboardingStatus;
// tag::import-user[]
import com.company.onboarding.entity.User;
// end::import-user[]
// tag::import-user-step[]
import com.company.onboarding.entity.UserStep;
// end::import-user-step[]
import com.company.onboarding.event.OnboardingStatusChangedEvent;
// tag::import-data-manager[]
import io.jmix.core.DataManager;
// end::import-data-manager[]
// tag::import-id[]
import io.jmix.core.Id;
// end::import-id[]
// tag::import-entity-changed-event[]
import io.jmix.core.event.EntityChangedEvent;
// end::import-entity-changed-event[]
// tag::import-ui-event-publisher[]
import io.jmix.flowui.UiEventPublisher;
// end::import-ui-event-publisher[]
// tag::import-event-listener[]
import org.springframework.context.event.EventListener;
// end::import-event-listener[]
// tag::import-component[]
import org.springframework.stereotype.Component;
// end::import-component[]
import java.util.Collections;

// tag::ui-event-publisher-1[]

@Component
public class UserStepEventListener {

    private final DataManager dataManager;
    private final UiEventPublisher uiEventPublisher;

    public UserStepEventListener(DataManager dataManager,
                                 UiEventPublisher uiEventPublisher) {
        this.dataManager = dataManager;
        this.uiEventPublisher = uiEventPublisher;
    }

    @EventListener
    public void onUserStepChangedBeforeCommit(EntityChangedEvent<UserStep> event) {
        User user;
        if (event.getType() != EntityChangedEvent.Type.DELETED) {
            Id<UserStep> userStepId = event.getEntityId();
            UserStep userStep = dataManager.load(userStepId).one();
            user = userStep.getUser();
        } else {
            Id<User> userId = event.getChanges().getOldReferenceId("user");
            if (userId == null) {
                throw new IllegalStateException("Cannot get User from deleted UserStep");
            }
            user = dataManager.load(userId).one();
        }

        // end::ui-event-publisher-1[]

        long completedCount = user.getSteps().stream()
                .filter(us -> us.getCompletedDate() != null)
                .count();
        if (completedCount == 0) {
            user.setOnboardingStatus(OnboardingStatus.NOT_STARTED);
        } else if (completedCount == user.getSteps().size()) {
            user.setOnboardingStatus(OnboardingStatus.COMPLETED);
        } else {
            user.setOnboardingStatus(OnboardingStatus.IN_PROGRESS);
        }

        dataManager.save(user);
        // tag::ui-event-publisher-2[]
        uiEventPublisher.publishEventForUsers( // <1>
                new OnboardingStatusChangedEvent(this),
                Collections.singleton(user.getUsername())
        );
    }
}
// end::ui-event-publisher-2[]