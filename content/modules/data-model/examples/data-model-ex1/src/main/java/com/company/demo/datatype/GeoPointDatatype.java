package com.company.demo.datatype;

// tag::datatype[]
import com.company.demo.entity.GeoPoint;
import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.util.Locale;

@DatatypeDef(
        id = "geoPoint", // <1>
        javaClass = GeoPoint.class, // <2>
        defaultForClass = true // <3>
)
@Ddl("varchar(255)") // <4>
public class GeoPointDatatype implements Datatype<GeoPoint> {

    @Override
    public String format(@Nullable Object value) { // <5>
        if (value instanceof GeoPoint) {
            return ((GeoPoint) value).latitude() + "|" + ((GeoPoint) value).longitude();
        }
        return null;
    }

    @Override
    public String format(@Nullable Object value, Locale locale) { // <6>
        return format(value);
    }

    @Nullable
    @Override
    public GeoPoint parse(@Nullable String value) throws ParseException { // <7>
        if (value == null)
            return null;
        String[] strings = value.split("\\|");
        try {
            return new GeoPoint(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
        } catch (Exception e) {
            throw new ParseException(String.format("Cannot parse %s as GeoPoint: %s", value, e), 0);
        }
    }

    @Nullable
    @Override
    public GeoPoint parse(@Nullable String value, Locale locale) throws ParseException { // <8>
        return parse(value);
    }
}
// end::datatype[]