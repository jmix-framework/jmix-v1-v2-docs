package com.company.onboarding.view.layout.gridlayout;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import io.jmix.core.*;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.gridlayout.GridLayout;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.Random;

@Route(value = "grid-layout-view", layout = MainView.class)
@ViewController(id = "GridLayoutView")
@ViewDescriptor(path = "grid-layout-view.xml")
public class GridLayoutView extends StandardView {
    private static final Random random = new Random();
    // tag::gridLayout[]
    @ViewComponent
    private GridLayout<Object> gridLayout;

    // end::gridLayout[]
    // tag::uiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::uiComponents[]
    @Autowired
    private FileStorageLocator fileStorageLocator;
    @Autowired
    private Metadata metadata;
    @Autowired
    private MessageTools messageTools;

    // tag::add-example[]
    @Subscribe
    public void onInit(final InitEvent event) {
        Checkbox checkbox = uiComponents.create(Checkbox.class);
        checkbox.setLabel("I verify that all information is accurate");
        checkbox.setValue(false);
        gridLayout.add(checkbox);
    }
    // end::add-example[]

    @Install(to = "gridLayoutUsers", subject = "itemLabelGenerator")
    private String gridLayoutUsersItemLabelGenerator(final User item) {
        return item.getFirstName() + " " + item.getLastName();
    }

    // tag::renderer[]
    @Supply(to = "gridLtUsers", subject = "renderer")
    private ComponentRenderer<Card, User> gridLtUsersRenderer() { // <1>
        return new ComponentRenderer<>(this::createCard, this::initCard);
    }

    // end::renderer[]

    // tag::renderer[]
    private Card createCard() { // <2>
        Card card = uiComponents.create(Card.class);
        card.setWidthFull();
        card.addThemeVariants(CardVariant.LUMO_OUTLINED, CardVariant.LUMO_ELEVATED);
        return card;
    }

    // end::renderer[]
    // tag::renderer[]
    private void initCard(Card card, User user) { // <3>
        card.setHeaderPrefix(createAvatar(user));
        card.setTitle(user.getFirstName() + " " + user.getLastName());
        card.setSubtitle(createSubtitle(user));
        card.setHeaderSuffix(createHeaderSuffix(user));
    }

    // end::renderer[]
// tag::renderer[]
    private Image createAvatar(User user) { // <4>
        Image image = uiComponents.create(Image.class);
        FileRef fileRef = user.getPicture();
        if (fileRef != null) {
            image.setWidth("50px");
            image.setHeight("50px");
            InputStreamDownloadHandler handler = DownloadHandler.fromInputStream(event -> {
                InputStream inputStream = fileStorageLocator.getByName(fileRef.getStorageName()).openStream(fileRef);
                return new DownloadResponse(inputStream, fileRef.getFileName(), fileRef.getContentType(), -1);
            });
            image.setSrc(handler);
        }
        return image;
    }

    // end::renderer[]
    // tag::renderer[]
    private Span createSubtitle(User user) {
        Span span = uiComponents.create(Span.class);
        span.setText("%s: %s".formatted(
                getPropertyCaption(user, "department"),
                (user.getDepartment() != null ?
                        user.getDepartment().getName() :
                        "Not assigned")));
        return span;
    }

    // end::renderer[]
    // tag::renderer[]
    private String getPropertyCaption(User user, String property) {
        MetaClass metaClass = metadata.getClass(user);
        return messageTools.getPropertyCaption(metaClass, property);
    }

    // end::renderer[]

    // tag::renderer[]
    private Span createHeaderSuffix(User user) {
        Span span = uiComponents.create(Span.class);
        if (user.getActive()) {
            span.setText("Active");
            span.getElement().getThemeList().add("badge success");
        }
        else {
            span.setText("Inactive");
            span.getElement().getThemeList().add("badge error");
        }
        return span;
    }
    // end::renderer[]
}