package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookDumper;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {
    private Writer w;

    public PrettyPrinter(Writer w) {
        this.w = w;
    }

    public static StringBuilder prettyPrint(AppointmentBook b) {
        if(b.getOwnerName() == null){
            System.out.println(Project3.NO_OWNER_PROVIDED);
            return null;
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
        if(b.getOwnerName() == null){
            System.out.println(Project3.NO_OWNER_PROVIDED);
            return;
        }

        w.write("****This Appointment Book belongs to " + b.getOwnerName() + ", The Coolest Cat in the Cave****\n\n\n");
        if(b.getAppointments() != null) {
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            int count = 1;
            for (Appointment appointment : appointments) {
                w.write("Appointment number: " + count + "\n");
                w.write("The appointment, which is: " + appointment.getDescription() + "\n");
                w.write("will begin at precisely " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(appointment.getBeginTime()));
                w.write(", and will go until exactly " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(appointment.getEndTime()) + "\n");
                w.write("lasting exactly " + Project3.getMinutes(appointment.getBeginTime(), appointment.getEndTime()) + " minutes\n\n");
                ++count;
            };
        } else {
            w.write("Nothing to report for today, you get out there and make a difference!!!\n\n");
        }
        w.write("\"\"\"You miss 100% of the shots you don't take\"\n\t-Wayne Gretzky\"\n\t\t-Michael Scott\"\n\t\t\t-Cody Green");
        w.flush();
    }
}
