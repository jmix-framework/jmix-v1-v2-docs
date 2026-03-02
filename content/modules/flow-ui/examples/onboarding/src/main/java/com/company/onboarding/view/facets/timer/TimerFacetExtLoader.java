package com.company.onboarding.view.facets.timer;

import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.xml.facet.loader.AbstractFacetLoader;
import org.dom4j.Element;

import java.util.Objects;

public class TimerFacetExtLoader extends AbstractFacetLoader<Timer> {
    @Override
    protected Timer createFacet() {
        return facets.create(Timer.class);
    }

    @Override
    public void loadFacet() {
        loadId(element);
        loadDelay(element);

        loaderSupport.loadBoolean(element, "repeating", resultFacet::setRepeating);
        loaderSupport.loadBoolean(element, "autostart", resultFacet::setAutostart);
    }

    protected void loadId(Element element) {
        String id = loaderSupport.loadString(element, "id")
                .orElseThrow(() -> new IllegalStateException("Timer id must be defined"));
        resultFacet.setId(id);
    }

    protected void loadDelay(Element element) {
        int delay = loaderSupport.loadInteger(element, "delay")
                .orElse(-1);
        if (delay <= 0) {
            throw new GuiDevelopmentException("Timer 'delay' must be greater than 0", context, "Timer ID",
                    Objects.requireNonNull(resultFacet.getId()));
        }

        resultFacet.setDelay(delay);
    }
}
