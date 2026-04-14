package com.company.onboarding.view.layout.gridlayout;

import com.company.onboarding.entity.User;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import io.jmix.core.*;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.fragmentrenderer.FragmentRenderer;
import io.jmix.flowui.fragmentrenderer.RendererItemContainer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

// tag::cardFragment[]
@FragmentDescriptor("card-fragment.xml")
@RendererItemContainer("userDc")
public class CardFragment extends FragmentRenderer<VerticalLayout, User> {

    @Autowired
    private FileStorageLocator fileStorageLocator;
    @Autowired
    private Metadata metadata;
    @Autowired
    private MessageTools messageTools;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initLayout();
    }

    private void initLayout() {
        Card card = uiComponents.create(Card.class);
        card.setWidthFull();
        card.addThemeVariants(CardVariant.LUMO_OUTLINED, CardVariant.LUMO_ELEVATED);

        card.setHeaderPrefix(createAvatar(getItem()));
        card.setTitle(getItem().getFirstName() + " " + getItem().getLastName());
        card.setSubtitle(createSubtitle(getItem()));
        card.setHeaderSuffix(createHeaderSuffix(getItem()));
        getContent().add(card);
    }

    private Image createAvatar(User user) {
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

    private Span createSubtitle(User user) {
        Span span = uiComponents.create(Span.class);
        span.setText("%s: %s".formatted(
                getPropertyCaption(user, "department"),
                (user.getDepartment() != null ?
                        user.getDepartment().getName() :
                        "Not assigned")));
        return span;
    }

    private String getPropertyCaption(User user, String property) {
        MetaClass metaClass = metadata.getClass(user);
        return messageTools.getPropertyCaption(metaClass, property);
    }

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
}
// end::cardFragment[]