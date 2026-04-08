package com.company.samlex1.view.main;

import com.company.samlex1.entity.User;
import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;

@Route("")
@ViewController(id = "MainView")
@ViewDescriptor(path = "main-view.xml")
public class MainView extends StandardMainView {

    @Autowired
    private Messages messages;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @Install(to = "userMenu", subject = "buttonRenderer")
    private Component userMenuButtonRenderer(UserDetails userDetails) {
        UserMenuInfo userMenuInfo = buildUserMenuInfo(userDetails);
        if (userMenuInfo == null) {
            return null;
        }

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-button-content");

        Avatar avatar = createAvatar(userMenuInfo.getDisplayName());

        Span name = uiComponents.create(Span.class);
        name.setText(userMenuInfo.getDisplayName());
        name.setClassName("user-menu-text");

        content.add(avatar, name);

        if (isSubstituted(userDetails)) {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(messages.getMessage("userMenu.substituted"));
            subtext.setClassName("user-menu-subtext");
            content.add(subtext);
        }

        return content;
    }

    @Install(to = "userMenu", subject = "headerRenderer")
    private Component userMenuHeaderRenderer(UserDetails userDetails) {
        UserMenuInfo userMenuInfo = buildUserMenuInfo(userDetails);
        if (userMenuInfo == null) {
            return null;
        }

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-header-content");

        Avatar avatar = createAvatar(userMenuInfo.getDisplayName());
        avatar.addThemeVariants(AvatarVariant.LUMO_LARGE);

        Span text = uiComponents.create(Span.class);
        text.setText(userMenuInfo.getDisplayName());
        text.setClassName("user-menu-text");

        content.add(avatar, text);

        if (userMenuInfo.getDisplayName().equals(userMenuInfo.getUsername())) {
            text.addClassNames("user-menu-text-subtext");
        } else {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(userMenuInfo.getUsername());
            subtext.setClassName("user-menu-subtext");
            content.add(subtext);
        }

        return content;
    }

    private UserMenuInfo buildUserMenuInfo(UserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }

        if (userDetails instanceof User) {
            User user = (User) userDetails;
            String displayName = generatePersistentUserName(user);
            return new UserMenuInfo(displayName, user.getUsername());
        }

        if (userDetails instanceof Saml2AuthenticatedPrincipal) {
            Saml2AuthenticatedPrincipal samlPrincipal = (Saml2AuthenticatedPrincipal) userDetails;
            String username = userDetails.getUsername();
            String displayName = generateInMemorySamlUserName(samlPrincipal, username);
            return new UserMenuInfo(displayName, username);
        }

        String username = userDetails.getUsername();
        return new UserMenuInfo(username, username);
    }

    private Avatar createAvatar(String fullName) {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setName(fullName);
        avatar.getElement().setAttribute("tabindex", "-1");
        avatar.setClassName("user-menu-avatar");
        return avatar;
    }

    private String generatePersistentUserName(User user) {
        String userName = String.format("%s %s",
                        Strings.nullToEmpty(user.getFirstName()),
                        Strings.nullToEmpty(user.getLastName()))
                .trim();

        if (userName.isEmpty()) {
            return user.getUsername();
        }
        return userName;
    }

    private String generateInMemorySamlUserName(Saml2AuthenticatedPrincipal samlPrincipal, String username) {
        String firstName = getSamlAttribute(samlPrincipal,
                "FirstName", "firstName", "givenName");
        String lastName = getSamlAttribute(samlPrincipal,
                "LastName", "lastName", "surname", "sn");

        String userName = String.format("%s %s",
                        Strings.nullToEmpty(firstName),
                        Strings.nullToEmpty(lastName))
                .trim();

        if (userName.isEmpty()) {
            return username;
        }
        return userName;
    }

    private String getSamlAttribute(Saml2AuthenticatedPrincipal samlPrincipal, String... attributeNames) {
        int i;
        for (i = 0; i < attributeNames.length; i++) {
            Object value = samlPrincipal.getFirstAttribute(attributeNames[i]);
            if (value != null) {
                String text = value.toString().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }
        return null;
    }

    private boolean isSubstituted(UserDetails userDetails) {
        if (userDetails == null) {
            return false;
        }

        UserDetails authenticatedUser = currentUserSubstitution.getAuthenticatedUser();
        return !authenticatedUser.getUsername().equals(userDetails.getUsername());
    }

    private static class UserMenuInfo {
        private final String displayName;
        private final String username;

        public UserMenuInfo(String displayName, String username) {
            this.displayName = displayName;
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getUsername() {
            return username;
        }
    }
}