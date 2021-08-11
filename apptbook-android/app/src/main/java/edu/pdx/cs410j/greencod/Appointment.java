package edu.pdx.cs410j.greencod;

import java.text.DateFormat;
import java.util.Date;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment implements Comparable<Appointment>{

private String owner = null;
private String description = null;
private Date begin = null;
private Date end = null;
private String deetz[] = null;

/**
 * Creates a new <code>Appointment</code>
 */
public Appointment() {}

/**
 * Creates a new <code>Appointment</code>
 *
 * @param owner
 *        The owner of the appointment
 * @param description
 *        a brief description of the appointment
 * @param begin
 *        The begin date
 * @param end
 *        The end date
 */
public Appointment(String owner, String description, Date begin, Date end, String deetz[]){
        this.owner = owner;
        this.description = description;
        this.begin = begin;
        this.end = end;
        this.deetz = deetz;
        }


@Override
public int compareTo(Appointment compare) {
        if(this.begin == compare.getBeginTime()){
        if(this.end == compare.getEndTime()){
        return this.description.compareTo(compare.getDescription());
        }
        return this.end.compareTo(compare.getEndTime());
        }
        return this.begin.compareTo(compare.getBeginTime());
        }

@Override
public String getDescription() {
        return description;
        }


public Date getBeginTime() {
        return begin;
        }


public Date getEndTime() {
        return end;
        }


public String[] getDeetz() {return deetz;}


public String getBeginTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(begin);
        }

public String getEndTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(end);
        }

        }
