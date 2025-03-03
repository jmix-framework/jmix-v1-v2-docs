package com.company.mapssample.view.style;


import com.company.mapssample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.PointStyle;
import io.jmix.mapsflowui.kit.component.model.style.image.CircleStyle;
import io.jmix.mapsflowui.kit.component.model.style.stroke.Stroke;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

@Route(value = "modify-style-view", layout = MainView.class)
@ViewController(id = "ModifyStyleView")
@ViewDescriptor(path = "modify-style-view.xml")
public class ModifyStyleView extends StandardView {

    @ViewComponent("map.vectorLayer.vectorSource")
    private VectorSource vectorSource;

    @Subscribe
    public void onInit(final InitEvent event) {
        vectorSource.addFeature(createPolygonWithModifyStyles());
    }

    // tag::modify-style[]
    private PolygonFeature createPolygonWithModifyStyles() {
        Polygon polygon = GeometryUtils.createPolygon(new Coordinate[]{
                new Coordinate(77.2048761253423, 28.605384389707353),
                new Coordinate(75.78484389126132, 26.895539146773086),
                new Coordinate(78.00224596797739, 27.170451672755192),
                new Coordinate(77.2048761253423, 28.605384389707353)});

        return new PolygonFeature(polygon)
                .withModifyStyles(
                        new PointStyle()
                        .withImage(new CircleStyle()
                                .withRadius(6)
                                .withFill(new Fill("rgba(149, 107, 214, 0.5)"))
                                .withStroke(new Stroke()
                                        .withWidth(2d)
                                        .withColor("#2F0571")))
                        .build());
    }
    // end::modify-style[]
}