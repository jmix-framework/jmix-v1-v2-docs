package com.company.onboarding.view.component.multiselectlistbox;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "MultiSelectListBoxView", layout = MainView.class)
@ViewController("MultiSelectListBoxView")
@ViewDescriptor("multi-select-list-box-view.xml")
public class MultiSelectListBoxView extends StandardView {
    @Autowired
    private MetadataTools metadataTools;

    // tag::itemEnabledProvider[]
    @Install(to = "multiSelectListBox", subject = "itemEnabledProvider")
    private boolean multiSelectListBoxItemEnabledProvider(final User user) {
        if (user != null) {
            return user.getActive();
        }
        return true;
    }
    // end::itemEnabledProvider[]
    // tag::itemLabelGenerator[]
    @Install(to = "multiSelectListBox", subject = "itemLabelGenerator")
    private String multiSelectListBoxItemLabelGenerator(final User item) {
        return metadataTools.format(item.getDisplayName()).toUpperCase();
    }
    // end::itemLabelGenerator[]

    @Autowired
    private UiComponents uiComponents;

    // tag::renderer[]
    @Supply(to = "mSelectListBox", subject = "renderer")
    private ComponentRenderer<Span, User> mSelectListBoxRenderer() {
        return new ComponentRenderer<>(user -> {
            Span span = uiComponents.create(Span.class);
            span.setText(user.getDisplayName());
            span.setClassName("font-bold");
            return span;
        });
    }
    // end::renderer[]
}