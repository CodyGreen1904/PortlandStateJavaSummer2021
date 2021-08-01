package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;

/**
 * This class represents a <code>TextDumper</code>.
 */

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private Writer w;

    /**
     * Creates a new <code>TextDumper</code>
     * @param w
     *      The Writer for TextDumper
     */
    public TextDumper(Writer w) {
        this.w = w;
    }

    /**
     * Used to write <code>AppointmentBook</code> to files
     * @param b
     *      <code>AppointmentBook</code> being added
     * @throws IOException
     */
    public void dump(AppointmentBook b) throws IOException {
        if(b.getOwnerName() == null){
            System.out.println(Project4.NO_OWNER_PROVIDED);
            return;
        }
        w.write(b.getOwnerName() + "\n");
        if(b.getAppointments() != null) {
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            for(Appointment appointment : appointments) {
                w.write(appointment.getDescription() + "\n");
                for(String d : appointment.getDeetz()){
                    w.write(d + "\n");
                }
            }
        }

        w.flush();
    }
}
