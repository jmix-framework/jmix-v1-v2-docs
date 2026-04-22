package com.company.onboarding.util;
// tag::DateUtils[]
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private DateUtils(){
    }
}
// end::DateUtils[]