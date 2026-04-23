package com.company.onboarding.component;

import io.jmix.flowui.component.datepicker.TypedDatePicker;

// tag::extended-date-picker[]
public class ExtendedDatePicker<V extends Comparable> extends TypedDatePicker<V> {

    public static final String FAST_INPUT_DATE_FORMAT = "ddMMyyyy";

    @Override
    protected void initComponent() {
        super.initComponent();
        getThemeNames().set("align-left", true);
        getI18n().setDateFormats(
                messages.getMessage("dateFormat"),
                FAST_INPUT_DATE_FORMAT
        );
    }
}
// end::extended-date-picker[]