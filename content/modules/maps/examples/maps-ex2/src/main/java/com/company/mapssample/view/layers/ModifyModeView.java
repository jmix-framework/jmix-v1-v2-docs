package com.company.mapssample.view.layers;


import com.company.mapssample.FeatureHelper;
import com.company.mapssample.entity.Region;
import com.company.mapssample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.source.HasFeatureModify;
import io.jmix.mapsflowui.component.model.source.HasGeoObjectModify;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "modify-mode-view", layout = MainView.class)
@ViewController(id = "ModifyModeView")
@ViewDescriptor(path = "modify-mode-view.xml")
public class ModifyModeView extends StandardView {

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

    // tag::modifyStartEvent[]
    @Subscribe("map.layer.source")
    public void onSourceGeoObjectModifyStart(final HasGeoObjectModify.GeoObjectModifyStartEvent<Region> event) {
        notifications.create("GeoObjectModifyStartEvent", "Items: "
                        + event.getItems().size())
                .show();
    }
    // end::modifyStartEvent[]

    // tag::modifyEndEvent[]
    @Subscribe("map.layer.source")
    public void onSourceGeoObjectModifyEnd(final HasGeoObjectModify.GeoObjectModifyEndEvent<Region> event) {
        notifications.create("GeoObjectModifyEndEvent", "Items: "
                        + event.getItems().size())
                .show();
    }
    // end::modifyEndEvent[]

    // tag::featureModifyStartEvent[]
    @Subscribe("map.featureLayer.featureSource")
    public void onSourceFeatureModifyStart(final HasFeatureModify.SourceFeatureModifyStartEvent event) {
        notifications.create("SourceFeatureModifyStartEvent", "Features: "
                        + event.getFeatures().size())
                .show();
    }
    // end::featureModifyStartEvent[]

    // tag::featureModifyEndEvent[]
    @Subscribe("map.featureLayer.featureSource")
    public void onSourceFeatureModifyEnd(final HasFeatureModify.SourceFeatureModifyEndEvent event) {
        notifications.create("SourceFeatureModifyEndEvent", "Features: "
                        + event.getFeatures().size())
                .show();
    }
    // end::featureModifyEndEvent[]

    // tag::featureDeleteEvent[]
    @Subscribe("map.featureLayer.featureSource")
    public void onSourceFeatureDelete(final HasFeatureModify.SourceFeatureDeleteEvent event) {
        notifications.create("SourceFeatureDeleteEvent", "Features: "
                        + event.getFeatures().size())
                .show();
    }
    // end::featureDeleteEvent[]
}