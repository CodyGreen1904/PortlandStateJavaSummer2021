package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;

/**
 * This class represents an <code>PrettyPrinter</code>.
 */
public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {
    private Writer w;
    /**
     * Creates a new <code>PrettyPrinter</code>
     * @param w
     *      The Writer for PrettyPrinter
     */
    public PrettyPrinter(Writer w) {
        this.w = w;
    }
    /**
     * Prints a pretty print format to given file
     * @param b
     * AppointmentBook being written
     * @return
     */
    public void dump(AppointmentBook b) throws IOException {
        if(b.getOwnerName() == null){
            System.out.println(Project4.NO_OWNER_PROVIDED);
            return;
        }
        PrintWriter pw = new PrintWriter(this.w);
        pw.println("****This Appointment Book belongs to " + b.getOwnerName() + ", The Coolest Cat in the Cave****\n\n");
        if(b.getAppointments() != null) {
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            int count = 1;
            for (Appointment appointment : appointments) {
                pw.println("Appointment number: " + count);
                pw.println("The appointment, which is: " + appointment.getDescription());
                pw.println("will begin at precisely " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(appointment.getBeginTime()));
                pw.println(", and will go until exactly " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(appointment.getEndTime()));
                pw.println("lasting exactly " + Project4.getMinutes(appointment.getBeginTime(), appointment.getEndTime()) + " minutes\n");
                ++count;
            };
        } else {
            pw.println("Nothing to report for today, you get out there and make a difference!!!\n");
        }
        pw.println("\"\"\"You miss 100% of the shots you don't take\"\n\t-Wayne Gretzky\"\n\t\t-Michael Scott\"\n\t\t\t-Cody Green");
        pw.flush();
    }
}
