package com.company.onboarding.view.component.checkboxgroup;


import com.company.onboarding.entity.Hobby;
import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
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
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(value = "CheckboxgroupView", layout = MainView.class)
@ViewController("CheckboxgroupView")
@ViewDescriptor("checkboxgroup-view.xml")
public class CheckboxgroupView extends StandardView {
    @ViewComponent
    private JmixCheckboxGroup<Hobby> checkboxGroup;
    @Autowired
    private Notifications notifications;
    // tag::checkboxGroupInt[]
    @ViewComponent
    private JmixCheckboxGroup<Integer> checkboxGroupInt;

    // end::checkboxGroupInt[]
    // tag::ratingCheckboxGroup[]
    @ViewComponent
    private JmixCheckboxGroup<Integer> ratingCheckboxGroup;

    // end::ratingCheckboxGroup[]
    // tag::checkboxGroupEnum[]
    @ViewComponent
    private JmixCheckboxGroup<OnboardingStatus> checkboxGroupEnum;

    // end::checkboxGroupEnum[]
    // tag::renderer[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;

    // end::renderer[]
    @Subscribe("getBtn")
    public void onGetBtnClick(final ClickEvent<JmixButton> event) {
        notifications.create("Values: " + checkboxGroup.getTypedValue()).show();
    }

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        checkboxGroupInt.setItems(1, 2, 3, 4, 5);
        // end::setItems[]
        // tag::setItemsMap[]
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(2, "Poor");
        map.put(3, "Average");
        map.put(4, "Good");
        map.put(5, "Excellent");
        ComponentUtils.setItemsMap(ratingCheckboxGroup, map);
        // end::setItemsMap[]
        // tag::setItemsEnum[]
        checkboxGroupEnum.setItems(OnboardingStatus.class);
        // end::setItemsEnum[]
        // tag::onInit[]
    }

    // end::onInit[]
    // tag::renderer[]
    @Supply(to = "userCheckboxGroup", subject = "renderer")
    private ComponentRenderer<HorizontalLayout, User> userCheckboxGroupRenderer() {
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