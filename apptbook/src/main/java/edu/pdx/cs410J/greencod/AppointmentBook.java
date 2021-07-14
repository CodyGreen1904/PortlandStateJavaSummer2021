package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class represents an <code>AppointmentBook</code>.
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment>{

    private String owner;

    private ArrayList<Appointment> appointments;

    /**
     * Creates a new <code>AppointmentBook</code>
     *
     * @param owner
     *        The owner of the appointmentbook
     * @param appointment
     *        The appointment being added
     */
    public AppointmentBook(String owner, Appointment appointment) {
        this.owner = owner;
        this.appointments = new ArrayList<>();
        this.addAppointment(appointment);
    }

    public AppointmentBook(String owner){
        this.owner = owner;
        this.appointments = new ArrayList<>();
    }

    /**
     * Returns the <code>String</code> with the name of
     *  the <code>owner</code>
     */

    public  String getOwnerName(){
        return owner;
    }

    /**
     * Returns the <code>Collection</code> with the <code>ArrayList</code>
     * of appointments
     */
    public  Collection<Appointment> getAppointments(){
        return appointments;
    }

    /**
     * Adds an <code>Appointment</code> to the list of
     * <code>appointments</code>
     */
    public void addAppointment(Appointment var1){
        appointments.add(var1);
    }
}
