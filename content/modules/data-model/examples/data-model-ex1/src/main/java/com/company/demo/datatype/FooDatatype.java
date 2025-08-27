package com.company.demo.datatype;

import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;

import java.text.ParseException;
import java.util.Locale;

// tag::ddl[]
@DatatypeDef(id = "foo", javaClass = Foo.class, defaultForClass = true)
@Ddl("varchar(255)")
public class FooDatatype implements Datatype<Foo> {
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
    public Foo parse(String value) throws ParseException {
        return null;
    }

    @Override
    public Foo parse(String value, Locale locale) throws ParseException {
        return null;
    }
}
