package edu.pdx.cs410j.greencod;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {
    private final String description;
    private final String endTimeString;
    private final String beginTimestring;

    public Appointment(String description, String beginTimeString, String endTimestring) {
        this.description = description;
        this.endTimeString = endTimestring;
        this.beginTimestring = beginTimeString;
    }

    @Override
    public String getBeginTimeString() {
        return this.beginTimestring;
    }

    @Override
    public String getEndTimeString() {
        return this.endTimeString;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
