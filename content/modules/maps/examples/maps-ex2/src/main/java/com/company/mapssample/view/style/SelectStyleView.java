package com.company.mapssample.view.style;


import com.company.mapssample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.PolygonStyle;
import io.jmix.mapsflowui.kit.component.model.style.stroke.Stroke;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

@Route(value = "select-style-view", layout = MainView.class)
@ViewController(id = "SelectStyleView")
@ViewDescriptor(path = "select-style-view.xml")
public class SelectStyleView extends StandardView {

    @ViewComponent("map.vectorLayer.vectorSource")
    private VectorSource vectorSource;

    @Subscribe
    public void onInit(final InitEvent event) {
        vectorSource.addFeature(createPolygonWithSelectStyles());
    }

    // tag::select-style[]
    private PolygonFeature createPolygonWithSelectStyles() {
        Polygon polygon = GeometryUtils.createPolygon(new Coordinate[]{
                new Coordinate(-64.75370117729211, 32.30679527567045),
                new Coordinate(-80.18546220891433, 25.760830653727623),
                new Coordinate(-80.18546220891433, 25.760830653727623),
                new Coordinate(-66.11846996297967, 18.4077981563645),
                new Coordinate(-64.75370117729211, 32.30679527567045),
        });
        return new PolygonFeature(polygon)
                .withSelectStyles(
                        new PolygonStyle()
                                .withFill(new Fill("rgba(255, 61, 0, 0.2)"))
                                .withStroke(new Stroke()
                                        .withWidth(4d)
                                        .withColor("#FF3D00"))
                                .build());
    }
    // end::select-style[]
}