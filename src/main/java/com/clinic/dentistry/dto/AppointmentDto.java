package com.clinic.dentistry.dto;

import com.clinic.dentistry.models.User;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDto {
    public final Long id;
    public final String fullName;
    public final String jobTitle;
    public final List<DayTimes> timetable;

    public AppointmentDto(User user) {
        id = user.getEmployeeId();
        fullName = user.getFullName();
        jobTitle = user.getEmployee().getJobTitle();
        timetable = new ArrayList<>();
    }

    public static class DayTimes{
        public final String day;
        public final List<String> times;

        public DayTimes(String day, List<String> times) {
            this.day = day;
            this.times = times;
        }
    }
}
