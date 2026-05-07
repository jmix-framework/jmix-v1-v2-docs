package com.company.onboarding.view.component.virtuallist;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.virtuallist.JmixVirtualList;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "virtual-list-view", layout = MainView.class)
@ViewController("VirtualListView")
@ViewDescriptor("virtual-list-view.xml")
public class VirtualListView extends StandardView {
    @ViewComponent
    private JmixVirtualList<User> usersVirtualList;
    // tag::uiComponents[]
    @Autowired
    private Messages messages;

    @Autowired
    protected MetadataTools metadataTools;

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private UiComponents uiComponents;

    // end::uiComponents[]
// tag::renderer[]
    @Supply(to = "virtualList", subject = "renderer")
    private Renderer<User> virtualListRenderer() {
        return new ComponentRenderer<>(this::createUserRenderer);
    }

    protected HorizontalLayout createUserRenderer(User user) {
        HorizontalLayout cardLayout = uiComponents.create(HorizontalLayout.class);
        cardLayout.setMargin(true);
        cardLayout.addClassNames(LumoUtility.Border.ALL,
                LumoUtility.BorderColor.CONTRAST_10,
                LumoUtility.BorderRadius.MEDIUM);

        Image image = uiComponents.create(Image.class);
        FileRef fileRef = user.getPicture();
        if (fileRef != null) {
            image.setWidth("50px");
            image.setHeight("50px");
            InputStreamDownloadHandler handler =
                    DownloadHandler.fromInputStream(event ->
                    new DownloadResponse(
                            fileStorage.openStream(fileRef),
                            fileRef.getFileName(),
                            fileRef.getContentType(),
                            -1
                    ));
            image.setSrc(handler);
        }

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);

        HorizontalLayout itemDetailLayout = new HorizontalLayout();
        itemDetailLayout.add(new Text(user.getUsername()));
        itemDetailLayout.add(new Html(
                messages.formatMessage(getClass(), "statusDescription",
                        user.getOnboardingStatus() == null ? ""
                                : metadataTools.format(user.getOnboardingStatus()))));

        itemDetailLayout.setPadding(false);
        itemDetailLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        H4 userName = new H4(user.getFirstName() + " " + user.getLastName());

        infoLayout.add(userName);
        infoLayout.add(itemDetailLayout);

        cardLayout.add(image,infoLayout);
        return cardLayout;
    }

    // end::renderer[]
    // tag::supply-renderer[]
    @Supply(to = "usersVirtualList", subject = "renderer")
    private Renderer<User> usersVirtualListRenderer() {
        return new ComponentRenderer<>(user -> {
            Button button = uiComponents.create(Button.class);
            button.setText(user.getFirstName() + ", " + user.getLastName());
            return button;
        });
    }
    // end::supply-renderer[]
    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        ComponentRenderer<Button, User> renderer = new ComponentRenderer<>(user -> {
            Button button = uiComponents.create(Button.class);
            button.setText(user.getFirstName() + ", " + user.getLastName());
            return button;
        });
        usersVirtualList.setRenderer(renderer);
    }
    // end::onInit[]
}