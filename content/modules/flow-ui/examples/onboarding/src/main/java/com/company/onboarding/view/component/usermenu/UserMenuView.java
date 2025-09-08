package com.company.onboarding.view.component.usermenu;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.usermenu.ComponentUserMenuItem;
import io.jmix.flowui.kit.component.usermenu.TextUserMenuItem;
import io.jmix.flowui.kit.component.usermenu.UserMenuItem;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "user-menu", layout = MainView.class)
@ViewController(id = "UserMenuView")
@ViewDescriptor(path = "user-menu-view.xml")
@AnonymousAllowed
public class UserMenuView extends StandardView {

    // tag::notifications-bean[]
    @Autowired
    private Notifications notifications;

    // end::notifications-bean[]

    // tag::user-menu-action[]
    @Subscribe("userMenuActions.aboutMenuItem.aboutAction")
    public void onUserMenuActionsAboutMenuItemAboutAction(final ActionPerformedEvent event) {
        notifications.show("About");
    }
    // end::user-menu-action[]

    // tag::user-menu-text[]
    @Subscribe("userMenuText.contactUsMenuItem")
    public void onUserMenuTextContactUsMenuItemClick(final UserMenuItem.HasClickListener.ClickEvent<TextUserMenuItem> event) {
        notifications.show("Phone number: +6(876)5463");
    }
    // end::user-menu-text[]

    // tag::user-menu-component[]
    @Subscribe("userMenuComponent.emailItMenuItem")
    public void onUserMenuComponentEmailItMenuItemClick(final UserMenuItem.HasClickListener.ClickEvent<ComponentUserMenuItem> event) {
        notifications.show("Email: test@river.net");
    }
    // end::user-menu-component[]

    // tag::user-menu-renderers[]
    @Autowired
    private Messages messages;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @Install(to = "userMenu", subject = "buttonRenderer")
    private Component userMenuButtonRenderer(final UserDetails userDetails) {
        User user = (User) userDetails;

        if (user == null) {
            return null;
        }

        String userName = generateUserName(user);

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-button-content");

        Avatar avatar = createAvatar(userName);

        Span name = uiComponents.create(Span.class);
        name.setText(userName);
        name.setClassName("user-menu-text");

        content.add(avatar, name);

        if (isSubstituted(user)) {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(messages.getMessage("userMenu.substituted"));
            subtext.setClassName("user-menu-subtext");

            content.add(subtext);
        }

        return content;
    }

    @Install(to = "userMenu", subject = "headerRenderer")
    private Component userMenuHeaderRenderer(final UserDetails userDetails) {
        User user = (User) userDetails;

        if (user == null) {
            return null;
        }

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-header-content");

        String name = generateUserName(user);

        Avatar avatar = createAvatar(name);
        avatar.addThemeVariants(AvatarVariant.LUMO_LARGE);

        Span text = uiComponents.create(Span.class);
        text.setText(name);
        text.setClassName("user-menu-text");

        content.add(avatar, text);

        if (name.equals(user.getUsername())) {
            text.addClassNames("user-menu-text-subtext");
        } else {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(user.getUsername());
            subtext.setClassName("user-menu-subtext");

            content.add(subtext);
        }

        return content;
    }

    private Avatar createAvatar(String fullName) {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setName(fullName);
        avatar.getElement().setAttribute("tabindex", "-1");
        avatar.setClassName("user-menu-avatar");

        return avatar;
    }

    private String generateUserName(User user) {
        String userName = String.format("%s %s",
                        Strings.nullToEmpty(user.getFirstName()),
                        Strings.nullToEmpty(user.getLastName()))
                .trim();

        return userName.isEmpty() ? user.getUsername() : userName;
    }

    private boolean isSubstituted(User user) {
        UserDetails authenticatedUser = currentUserSubstitution.getAuthenticatedUser();
        return user != null && !authenticatedUser.getUsername().equals(user.getUsername());
    }
    // end::user-menu-renderers[]
}