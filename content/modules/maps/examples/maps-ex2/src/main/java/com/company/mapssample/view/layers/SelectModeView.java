package com.company.mapssample.view.layers;


import com.company.mapssample.FeatureHelper;
import com.company.mapssample.entity.Region;
import com.company.mapssample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.source.HasFeatureSelect;
import io.jmix.mapsflowui.component.model.source.HasGeoObjectSelect;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "select-mode-view", layout = MainView.class)
@ViewController(id = "SelectModeView")
@ViewDescriptor(path = "select-mode-view.xml")
public class SelectModeView extends StandardView {

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    @ViewComponent("map.featureLayer.featureSource")
    private VectorSource featureSource;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        featureSource.addFeature(FeatureHelper.createWestVirginia());
    }

    // tag::selectEvent[]
    @Subscribe("map.layer.source")
    public void onMapLayerSourceGeoObjectSelect(final HasGeoObjectSelect.GeoObjectSelectEvent<Region> event) {
        notifications.create("GeoObjectSelectEvent",
                        String.format("Selected: %s\nDeselected: %s",
                                event.getSelected().size(), event.getDeselected()))
                .show();
    }
    // end::selectEvent[]

    // tag::featureSelectEvent[]
    @Subscribe("map.featureLayer.featureSource")
    public void onMapFeatureLayerFeatureSourceSourceFeatureSelect(final HasFeatureSelect.SourceFeatureSelectEvent event) {
        notifications.create("SourceFeatureSelectEvent",
                        String.format("Selected: %s\nDeselected: %s",
                                event.getSelected().size(), event.getDeselected()))
                .show();
    }
    // end::featureSelectEvent[]
}