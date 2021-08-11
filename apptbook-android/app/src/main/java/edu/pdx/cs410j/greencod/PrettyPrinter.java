package edu.pdx.cs410j.greencod;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;

import edu.pdx.cs410J.AppointmentBookDumper;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    /**
     * Creates a new <code>PrettyPrinter</code>
     * @param w
     *      The Writer for PrettyPrinter
     */
    public PrettyPrinter(Writer w) {
        ;
    }

    /**
     * Prints a pretty print format to standard out
     * @param b
     * AppointmentBook being written
     * @return
     */
    public static StringBuilder prettyPrint(AppointmentBook b) {
        if(b.getOwnerName() == null){
            System.out.println(MakeAppointment.NO_OWNER_PROVIDED);
            return new StringBuilder(MakeAppointment.NO_OWNER_PROVIDED);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("****This Appointment Book belongs to " + b.getOwnerName() + ", The Coolest Cat in the Cave****\n\n\n");
        if(b.getAppointments() != null) {
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            int count = 1;
            for (Appointment appointment : appointments) {
                stringBuilder.append("Appointment number: " + count + "\n");
                stringBuilder.append("The appointment, which is: " + appointment.getDescription() + "\n");
                stringBuilder.append("will begin at precisely " + DateFormat.getDateInstance(DateFormat.LONG).format(appointment.getBeginTime()));
                stringBuilder.append(", and will go until exactly " + DateFormat.getDateInstance(DateFormat.LONG).format(appointment.getEndTime()) + "\n\n");
                ++count;
            };
        } else {
            stringBuilder.append("Nothing to report for today, you get out there and make a difference!!!\n\n");
        }
        stringBuilder.append("\"\"\"You miss 100% of the shots you don't take\"\n\t-Wayne Gretzky\"\n\t\t-Michael Scott\"\n\t\t\t-Cody Green");
        return stringBuilder;
    }
    public void dump(AppointmentBook b) throws IOException {
    }
}
