package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents an <code>Appointment</code>.
 */

public class Appointment extends AbstractAppointment implements Comparable<Appointment>{

    private String owner = null;
    private String description = null;
    private Date begin = null;
    private Date end = null;
    private String deetz[] = null;

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

    /**
     * Overrides the compare to method for Collections
     * @param compare
     *  The appointment being compared to the current appointment
     * @return
     */
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
    /**
     * Returns the <code>String</code> <code>description</code>
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the begin Date member
     * @return
     */
    public Date getBeginTime() {
        return begin;
    }

    /**
     * Returns the end Date member
     * @return
     */
    public Date getEndTime() {
        return end;
    }

    /**
     * Returns the deetz String Array member
     * @return
     */
    public String[] getDeetz() {return deetz;}

    /**
     * Returns the formatted Date String for begin
     * @return
     */
    public String getBeginTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(begin);
    }
    /**
     * Returns the formatted Date String for end
     * @return
     */
    public String getEndTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(end);
    }

}