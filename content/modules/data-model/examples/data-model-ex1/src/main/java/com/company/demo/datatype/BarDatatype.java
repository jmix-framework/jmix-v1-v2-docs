package com.company.demo.datatype;

import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;

import java.text.ParseException;
import java.util.Locale;

// tag::ddl[]
@DatatypeDef(id = "bar", javaClass = Bar.class, defaultForClass = true)
@Ddl("varchar(255)")
@Ddl(value = "text", dbms = "postgres")
@Ddl(value = "varchar2(255)", dbms = "oracle")
public class BarDatatype implements Datatype<Bar> {
// end::ddl[]
    @Override
    public String format(Object value) {
        return "";
    }

    @Override
    public String format(Object value, Locale locale) {
        return "";
    }

    @Override
    public Bar parse(String value) throws ParseException {
        return null;
    }

    @Override
    public Bar parse(String value, Locale locale) throws ParseException {
        return null;
    }
}
