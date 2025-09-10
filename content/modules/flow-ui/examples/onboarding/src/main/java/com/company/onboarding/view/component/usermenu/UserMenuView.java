package com.company.onboarding.view.component.usermenu;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.usermenu.ComponentUserMenuItem;
import io.jmix.flowui.kit.component.usermenu.TextUserMenuItem;
import io.jmix.flowui.kit.component.usermenu.UserMenuItem;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

    // tag::user-menu-renderer-injects[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;

    // end::user-menu-renderer-injects[]

    // tag::user-menu-button-renderer[]
    @Install(to = "userMenu", subject = "buttonRenderer")
    private Component userMenuButtonRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        String userName = generateUserName(user);
        Avatar avatar = createAvatar(userName, user.getPicture());
        Span name = uiComponents.create(Span.class);
        name.setText(userName);
        name.addClassName(LumoUtility.TextColor.BODY);

        HorizontalLayout content = uiComponents.create(HorizontalLayout.class);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.add(avatar, name);
        content.addClassNames( // <1>
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Vertical.SMALL);

        return content;
    }
    // end::user-menu-button-renderer[]

    // tag::user-menu-header-renderer[]
    @Install(to = "userMenu", subject = "headerRenderer")
    private Component userMenuHeaderRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        String name = generateUserName(user);

        Avatar avatar = createAvatar(name, user.getPicture());
        avatar.addThemeVariants(AvatarVariant.LUMO_LARGE);
        avatar.addClassName("user-menu-avatar");

        Span text = uiComponents.create(Span.class);
        text.setText(name);
        text.setClassName("user-menu-text");

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-header-content"); // <1>
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
    // end::user-menu-header-renderer[]

    // tag::user-menu-renderer-helpers[]

    private String generateUserName(User user) {
        String userName = String.format("%s %s",
                        Strings.nullToEmpty(user.getFirstName()),
                        Strings.nullToEmpty(user.getLastName()))
                .trim();

        return userName.isEmpty() ? user.getUsername() : userName;
    }

    private Avatar createAvatar(String fullName, @Nullable FileRef fileRef) {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setName(fullName);
        avatar.getElement().setAttribute("tabindex", "-1"); // <2>

        if (fileRef != null) {
            StreamResource streamResource = new StreamResource(
                    fileRef.getFileName(),
                    () -> fileStorage.openStream(fileRef));
            avatar.setImageResource(streamResource); // <3>
        }

        return avatar;
    }
    // end::user-menu-renderer-helpers[]
}