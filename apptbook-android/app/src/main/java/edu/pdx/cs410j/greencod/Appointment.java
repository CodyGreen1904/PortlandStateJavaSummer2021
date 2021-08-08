package edu.pdx.cs410j.greencod;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {
    private final String description;
    private final String endTimeString;
    private final String beginTimestring;

    public Appointment(String description, String endTimeString, String beginTimestring) {
        this.description = description;
        this.endTimeString = endTimeString;
        this.beginTimestring = beginTimestring;
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
