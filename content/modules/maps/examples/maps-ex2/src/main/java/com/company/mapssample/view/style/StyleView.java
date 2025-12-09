package com.company.mapssample.view.style;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.style.*;
import io.jmix.mapsflowui.kit.component.model.style.image.CircleStyle;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import io.jmix.mapsflowui.kit.component.model.style.stroke.Stroke;
import io.jmix.mapsflowui.kit.component.model.style.text.TextStyle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;

import java.util.List;

@Route(value = "style-view", layout = MainView.class)
@ViewController("StyleView")
@ViewDescriptor("style-view.xml")
public class StyleView extends StandardView {

    private final GeometryFactory geometries = GeometryUtils.getGeometryFactory();
    @ViewComponent("map.vectorLayer.vectorSource")
    private VectorSource vectorSource;

    @Subscribe
    public void onInit(final InitEvent event) {
        vectorSource.addAllFeatures(List.of(createPointWithFontIcons(),
                createStyledMarker(),
                createStyledPoint(),
                createTextPoint(),
                createStyledPolygon(),
                createStyledLineString()));
    }
    // tag::marker[]
    private MarkerFeature createStyledMarker() {
        MarkerFeature feature = new MarkerFeature(GeometryUtils.createPoint(20, 20));
        feature.removeAllStyles();
        return feature.withStyles(new Style()
                        .withImage(new IconStyle()
                                .withSrc("icons/icon.png")
                                .withScale(0.05)));
    }
    // end::marker[]
    // tag::point[]
    private PointFeature createStyledPoint() {
        return new PointFeature(GeometryUtils.createPoint(22, 25))
                .withStyles(
                        new PointStyle()
                                .withImage(new CircleStyle()
                                        .withRadius(7)
                                        .withFill(new Fill("#E7003E"))
                                        .withStroke(new Stroke()
                                                .withWidth(2.0)
                                                .withColor("#710067")))
                                .build());
    }
    // end::point[]

    // tag::point-text[]
    private PointFeature createTextPoint() {
        return new PointFeature(GeometryUtils.createPoint(15, 15))
                .withStyles(
                        new PointStyle()
                                .withText(new TextStyle()
                                        .withText("Africa")
                                        .withFont("20px sans-serif")
                                        .withFill(new Fill("#5E4BD8"))
                                        .withStroke(new Stroke()
                                                .withWidth(2.)
                                                .withColor("#A368D5")))
                                .build());
    }
    // end::point-text[]

    // tag::polygon[]
    private PolygonFeature createStyledPolygon() {
        LinearRing shell = geometries.createLinearRing(new Coordinate[]{
                new Coordinate(1.2457020544488762, 42.476628901048684),
                new Coordinate(-0.054875980233204155, 52.77260344863316),
                new Coordinate(29.858418817454655, 46.105591288830624),
                new Coordinate(1.2457020544488762, 42.476628901048684),
        });
        return new PolygonFeature(geometries.createPolygon(shell))
                .withStyles(
                        new PolygonStyle()
                                .withFill(new Fill("rgba(1, 147, 154, 0.2)"))
                                .withStroke(new Stroke()
                                        .withWidth(3.)
                                        .withColor("#123EAB"))
                                .build());
    }
    // end::polygon[]
    // tag::lineString[]
    private LineStringFeature createStyledLineString() {
        LineString lineString = geometries.createLineString(new Coordinate[]{
                new Coordinate(13, 20),
                new Coordinate(13, 32),
                new Coordinate(25, 17)});
        return new LineStringFeature(lineString)
                .withStyles(
                        new LineStringStyle()
                                .withStroke(new Stroke()
                                        .withWidth(3.)
                                        .withColor("#F60018"))
                                .build());
    }
    // end::lineString[]
    // tag::point-font-icon[]
    private PointFeature createPointWithFontIcons() {
        return new PointFeature(GeometryUtils.createPoint(0, 43))
                .withStyles(new Style()
                        .withText(new TextStyle()
                                .withText("\uf015")
                                .withFont("24px 'Font Awesome 7 Free'")));
    }
    // end::point-font-icon[]
/*
    // tag::point-vaadin-icon[]
    private PointFeature createPointWithVaadinIcons() {
        return new PointFeature(GeometryUtils.createPoint(11, 45))
                .withStyles(new Style()
                        .withImage(new IconStyle()
                                .withVaadinIcon(VaadinIcon.USER)
                                .withColor("#1B1BB3")
                        ));
    }
    // end::point-vaadin-icon[]

    // tag::point-lumo-icon[]
    private PointFeature createPointWithLumoIcons() {
        return new PointFeature(GeometryUtils.createPoint(12, 47))
                .withStyles(new Style()
                        .withImage(new IconStyle()
                                .withLumoIcon(LumoIcon.SEARCH)
                                .withIconHeight("32px")
                                .withIconWidth("32px")
                                .withColor("#621448"))
                );
    }
    // end::point-lumo-icon[]


    // tag::point-custom-icon[]
    private PointFeature createCustomPoint() {
        return new PointFeature(GeometryUtils.createPoint(4, 33))
                .withStyles(new Style()
                        .withImage(new IconStyle()
                                .withIconFromSet(StarIcons.CIRCLE))
                );
    }
    // end::point-custom-icon[]
    private PointFeature createCustomGreenPoint() {
        return new PointFeature(GeometryUtils.createPoint(5, 32))
                // tag::point-custom-green-icon[]
                .withStyles(new Style()
                        .withImage(new IconStyle()
                                .withIconFromSet(StarIcons.CIRCLE)
                                .withColor("yellow"))
                );
    }
    // end::point-custom-green-icon[]
    // tag::point-icon-name[]
    private PointFeature createPointWithIconName() {
        return new PointFeature(GeometryUtils.createPoint(0, 30))
                .withStyles(new Style()
                        .withImage(new IconStyle()
                                .withIconName("vaadin:user")));
    }
    // end::point-icon-name[]

 */
}