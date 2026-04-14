// tag::my-icons-enum[]
package com.company.onboarding.icons;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.IconFactory;
import java.util.Locale;

@JsModule("./icons/my-icons.js")
public enum MyIcons implements IconFactory {
    STAR,
    HEART,
    CIRCLE;

    public Icon create() {
        return new Icon("my-icons",
                name().toLowerCase(Locale.ENGLISH).replace('_', '-'));
    }
}
// end::my-icons-enum[]