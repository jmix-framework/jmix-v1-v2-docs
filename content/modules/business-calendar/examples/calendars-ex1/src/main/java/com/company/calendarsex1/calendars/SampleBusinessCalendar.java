package com.company.calendarsex1.calendars;

import io.jmix.businesscalendar.annotation.*;

import java.time.DayOfWeek;
import java.time.Month;

// tag::business-calendar[]
@BusinessCalendar(name = "Sample Business Calendar",
        code = "sample-business-calendar" ) // <1>
public interface SampleBusinessCalendar {

    @CronHoliday(expression = "* * * 1-2 MAY ?", mark = "spring-holiday",
            description = "Early May public holidays") // <2>
    @CronHoliday(expression = "* * * ? * 6#3", mark = "monthly-maintenance",
            description = "Scheduled maintenance on the third Saturday of each month")
    void cronHoliday();

    @WeeklyHoliday(value = DayOfWeek.SATURDAY, mark = "weekend",
            description = "Standard weekend day")
    @WeeklyHoliday(value = DayOfWeek.SUNDAY, mark = "weekend",
            description = "Standard weekend day") // <3>
    void weeklyHolidays();

    @FixedDayHoliday(fixedDate = "2026-01-02", mark = "maintenance", description = "Scheduled maintenance") // <4>
    @FixedDayHoliday(fixedDate = "2026-03-04", mark = "company-holiday", description = "Company day off")
    void fixedHoliday();

    @FixedYearlyHoliday(month = Month.NOVEMBER, dayOfMonth = 4, mark = "regional", description = "Annual regional holiday") // <5>
    @FixedYearlyHoliday(month = Month.JUNE, dayOfMonth = 8, mark = "regional", description = "Annual regional holiday")
    void fixedYearlyHoliday();

    @ScheduledBusinessDay(dayOfWeek = DayOfWeek.MONDAY,
            startTime = "08:00", endTime = "17:00", mark = "regular-hours") // <6>
    @ScheduledBusinessDay(dayOfWeek = DayOfWeek.WEDNESDAY,
            startTime = "09:00", endTime = "17:00", mark = "late-start")
    @ScheduledBusinessDay(dayOfWeek = DayOfWeek.FRIDAY,
            startTime = "10:00", endTime = "15:00", mark = "short-day")
    void scheduledBD();

    @AdditionalBusinessDay(fixedDate = "2026-05-06",
            startTime = "10:00", endTime = "16:30", mark = "holiday-shift") // <7>
    @AdditionalBusinessDay(fixedDate = "2026-07-08",
            startTime = "10:00", endTime = "16:30", mark = "extra-workday")
    void additionalBD();
}
// end::business-calendar[]
