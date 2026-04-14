package com.company.onboarding.icons;

import com.vaadin.flow.component.Component;
import io.jmix.flowui.icon.impl.IconsImpl;
import org.springframework.context.annotation.Primary;

//tag::app-icons[]
@Primary
@org.springframework.stereotype.Component("AppIcons")
public class AppIcons extends IconsImpl {

    @Override
    protected Component createIconByName(String iconName) {
        return switch (iconName) {
            case "STAR" -> MyIcons.STAR.create(); //<1>
            case "HEART" -> MyIcons.HEART.create();
            case "CIRCLE" -> MyIcons.CIRCLE.create();
            default -> super.createIconByName(iconName); //<2>
        };
    }
}
//end::app-icons[]