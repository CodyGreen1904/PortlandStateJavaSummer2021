package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private Writer w;

    public TextDumper(Writer w) {
        this.w = w;
    }

    public void dump(AppointmentBook b) throws IOException {
        if(b.getAppointments() != null) {
            w.write(b.getOwnerName() + "\n");
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            for(Appointment appointment : appointments) {
                w.write(appointment.getDescription() + "\n");
                w.write(appointment.getBeginDate() + "\n");
                w.write(appointment.getBeginTimeS() + "\n");
                w.write(appointment.getEndDate() + "\n");
                w.write(appointment.getBeginTimeS() + "\n");
            }
        }

        w.flush();
    }
}
