package com.company.onboarding.view.component.radiobuttongroup;


import com.company.onboarding.entity.Hobby;
import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(value = "RadioButtonGroupView", layout = MainView.class)
@ViewController("RadioButtonGroupView")
@ViewDescriptor("radio-button-group-view.xml")
public class RadioButtonGroupView extends StandardView {

    @ViewComponent
    private JmixRadioButtonGroup<Hobby> hobbiesRadioButtonGroup;
    // tag::items-list[]
    @ViewComponent
    private JmixRadioButtonGroup<Integer> radioButtonGroupInt;

    // end::items-list[]
    // tag::items-map[]
    @ViewComponent
    private JmixRadioButtonGroup<Integer> ratingRadioButtonGroup;

    // end::items-map[]
    // tag::items-enum[]
    @ViewComponent
    private JmixRadioButtonGroup<OnboardingStatus> enumRadioButtonGroup;

    // end::items-enum[]
    @Autowired
    private MetadataTools metadataTools;
    // tag::autowired[]
    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private FileStorage fileStorage;

    // end::autowired[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::items-list[]
        radioButtonGroupInt.setItems(1, 2, 3, 4, 5);
        // end::items-list[]
        // tag::items-map[]
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(2, "Poor");
        map.put(3, "Average");
        map.put(4, "Good");
        map.put(5, "Excellent");
        ComponentUtils.setItemsMap(ratingRadioButtonGroup, map);
        // end::items-map[]
        // tag::items-enum[]
        enumRadioButtonGroup.setItems(OnboardingStatus.class);
        // end::items-enum[]
        // tag::onInit[]
    }
    // end::onInit[]

    // tag::itemEnabledProvider[]
    @Install(to = "radioButtonGroup", subject = "itemEnabledProvider")
    private boolean radioButtonGroupItemEnabledProvider(OnboardingStatus onboardingStatus) {
        if (onboardingStatus != null) {
            return onboardingStatus.getId() != 30;
        }
        return true;
    }

    // end::itemEnabledProvider[]
    // tag::itemLabelGenerator[]
    @Install(to = "statusRadioButtonGroup", subject = "itemLabelGenerator")
    private String statusRadioButtonGroupItemLabelGenerator(
            final OnboardingStatus t) {
        return metadataTools.format(t).toUpperCase();
    }

    // end::itemLabelGenerator[]
    // tag::renderer[]
    @Supply(to = "rbgRenderer", subject = "renderer")
    private ComponentRenderer<HorizontalLayout, User> rbgRendererRenderer() {
        return new ComponentRenderer<>(user -> {
            FileRef fileRef = user.getPicture();
            HorizontalLayout row = uiComponents.create(HorizontalLayout.class);
            row.setAlignItems(FlexComponent.Alignment.END);
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                image.setClassName("user-picture");
                InputStreamDownloadHandler handler =
                        DownloadHandler.fromInputStream(event ->
                                new DownloadResponse(
                                        fileStorage.openStream(fileRef),
                                        fileRef.getFileName(),
                                        fileRef.getContentType(),
                                        -1
                                ));
                image.setSrc(handler);
                row.add(image);
            }
            row.add(new Span(user.getFirstName() + ", " + user.getLastName()));
            return row;
        });
    }
    // end::renderer[]
}