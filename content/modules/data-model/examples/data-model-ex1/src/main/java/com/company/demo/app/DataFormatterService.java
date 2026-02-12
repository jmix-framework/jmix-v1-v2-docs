package com.company.demo.app;

// tag::datatypeformatter[]
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.time.LocalDate;

@Component
public class DataFormatterService {

    private final DatatypeFormatter datatypeFormatter;

    public DataFormatterService(DatatypeFormatter datatypeFormatter) {
        this.datatypeFormatter = datatypeFormatter;
    }

    public String formatDate (LocalDate date) {
        return datatypeFormatter.formatLocalDate(date);
    }

    public LocalDate parseDate(String value) throws ParseException {
        return datatypeFormatter.parseLocalDate(value);
    }

}
// end::datatypeformatter[]