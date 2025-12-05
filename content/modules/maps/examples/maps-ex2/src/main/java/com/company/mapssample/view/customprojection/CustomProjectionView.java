package com.company.mapssample.view.customprojection;


import com.company.mapssample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;

@Route(value = "custom-projection-view", layout = MainView.class)
@ViewController(id = "CustomProjectionView")
@ViewDescriptor(path = "custom-projection-view.xml")
public class CustomProjectionView extends StandardView {
    // tag::set-projection[]
    @ViewComponent
    private GeoMap geoMap;

    @Subscribe
    public void onInit(final InitEvent event) {
        geoMap.setProjection(() -> "EPSG:21781");
    }
    // end::set-projection[]
}