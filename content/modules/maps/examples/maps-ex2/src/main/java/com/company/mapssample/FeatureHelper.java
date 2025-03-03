package com.company.mapssample;

import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

public final class FeatureHelper {

    private static GeometryFactory factory = GeometryUtils.getGeometryFactory();

    private FeatureHelper() {
    }

    public static PolygonFeature createWestVirginia() {
        return new PolygonFeature(factory.createPolygon(new Coordinate[]{
                new Coordinate(-80.518598, 40.636951),
                new Coordinate(-80.518598, 39.722302),
                new Coordinate(-79.477979, 39.722302),
                new Coordinate(-79.488933, 39.20747),
                new Coordinate(-79.291763, 39.300578),
                new Coordinate(-79.094593, 39.470363),
                new Coordinate(-78.963147, 39.437501),
                new Coordinate(-78.765977, 39.585379),
                new Coordinate(-78.470222, 39.514178),
                new Coordinate(-78.431884, 39.623717),
                new Coordinate(-78.267575, 39.61824),
                new Coordinate(-78.174467, 39.694917),
                new Coordinate(-78.004682, 39.601809),
                new Coordinate(-77.834897, 39.601809),
                new Coordinate(-77.719881, 39.322485),
                new Coordinate(-77.82942, 39.130793),
                new Coordinate(-78.349729, 39.464886),
                new Coordinate(-78.404499, 39.169131),
                new Coordinate(-78.870039, 38.763838),
                new Coordinate(-78.996008, 38.851469),
                new Coordinate(-79.209609, 38.495467),
                new Coordinate(-79.313671, 38.413313),
                new Coordinate(-79.477979, 38.457129),
                new Coordinate(-79.647764, 38.594052),
                new Coordinate(-79.724442, 38.364021),
                new Coordinate(-79.921611, 38.177805),
                new Coordinate(-79.998289, 37.997066),
                new Coordinate(-80.184505, 37.849189),
                new Coordinate(-80.294043, 37.690357),
                new Coordinate(-80.29952, 37.509618),
                new Coordinate(-80.474782, 37.421987),
                new Coordinate(-80.513121, 37.482234),
                new Coordinate(-80.967707, 37.290541),
                new Coordinate(-81.225123, 37.235771),
                new Coordinate(-81.362047, 37.339833),
                new Coordinate(-81.55374, 37.208387),
                new Coordinate(-81.679709, 37.20291),
                new Coordinate(-81.849494, 37.285064),
                new Coordinate(-81.986418, 37.454849),
                new Coordinate(-81.969987, 37.537003),
                new Coordinate(-82.101434, 37.553434),
                new Coordinate(-82.293127, 37.668449),
                new Coordinate(-82.342419, 37.783465),
                new Coordinate(-82.50125, 37.931343),
                new Coordinate(-82.621743, 38.123036),
                new Coordinate(-82.594358, 38.424267),
                new Coordinate(-82.331465, 38.446175),
                new Coordinate(-82.293127, 38.577622),
                new Coordinate(-82.172634, 38.632391),
                new Coordinate(-82.221926, 38.785745),
                new Coordinate(-82.03571, 39.026731),
                new Coordinate(-81.887833, 38.873376),
                new Coordinate(-81.783771, 38.966484),
                new Coordinate(-81.811156, 39.0815),
                new Coordinate(-81.685186, 39.273193),
                new Coordinate(-81.57017, 39.267716),
                new Coordinate(-81.455155, 39.410117),
                new Coordinate(-81.345616, 39.344393),
                new Coordinate(-81.219646, 39.388209),
                new Coordinate(-80.830783, 39.711348),
                new Coordinate(-80.737675, 40.078303),
                new Coordinate(-80.600752, 40.319289),
                new Coordinate(-80.595275, 40.472643),
                new Coordinate(-80.666475, 40.582182),
                new Coordinate(-80.518598, 40.636951)}))
                .withProperty("name", "West Virginia");
    }
}
