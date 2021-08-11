package edu.pdx.cs410j.greencod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import edu.pdx.cs410J.AbstractAppointmentBook;

public class AppointmentBook extends AbstractAppointmentBook<Appointment> {

    private final String owner;

    private final ArrayList<Appointment> appointments;

    /**
     * Creates a new <code>AppointmentBook</code>
     *
     * @param owner       The owner of the <code>AppointmentBook</code>
     * @param appointment The appointment being added
     */
    public AppointmentBook(String owner, Appointment appointment) {
        this.owner = owner;
        this.appointments = new ArrayList<>();
        this.addAppointment(appointment);
    }

    /**
     * Creates a new <code>AppointmentBook</code>
     *
     * @param owner The owner of the <code>AppointmentBook</code>
     */
    public AppointmentBook(String owner) {
        this.owner = owner;
        this.appointments = new ArrayList<>();
    }

    /**
     * Returns the <code>String</code> with the name of
     * the <code>owner</code>
     */
    public String getOwnerName() {
        return owner;
    }

    /**
     * Returns the <code>Collection</code> with the <code>ArrayList</code>
     * of appointments
     */
    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Adds an <code>Appointment</code> to the list of
     * <code>appointments</code>
     */
    public void addAppointment(Appointment var1) {
        appointments.add(var1);
        Collections.sort(appointments);
    }
}

