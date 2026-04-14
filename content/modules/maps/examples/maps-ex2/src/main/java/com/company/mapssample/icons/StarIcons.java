package com.company.mapssample.icons;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.IconFactory;

import java.util.Locale;


@JsModule("./icons/star-icons.js")
public enum StarIcons implements IconFactory {
    STAR,
    HEART,
    CIRCLE;

    public Icon create() {
        return new Icon("star-icons",
                this.name().toLowerCase(Locale.ENGLISH)
                        .replace('_', '-')
                        .replaceAll("^-", ""));
    }
}