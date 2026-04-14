package com.company.demo.entity;

// tag::datatype[]
import java.io.Serializable;

public record GeoPoint(double latitude, double longitude) implements Serializable {
}
// end::datatype[]
