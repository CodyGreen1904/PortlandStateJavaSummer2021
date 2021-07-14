package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private Writer w;

    public TextDumper(Writer w) {
        this.w = w;
    }

    public void exampleFileCreate(int num) throws IOException {
        switch (num) {
            case 2: w.write("Cody"); break;
            case 3: w.write("Cody\nStuff"); break;
            case 4: w.write("Cody\nStuff\n07/21/1992"); break;
            case 5: w.write("Cody\nStuff\n07/21/1992\n11:11"); break;
            case 6: w.write("Cody\nStuff\n07/21/1992\n11:11\n07/21/1992"); break;
            case 7: w.write("Cody\nStuff\n07/21/1992\n11:11\n07/21/1992\n11:12"); break;
            default: break;
        }
        w.flush();
    }

    public void dump(AppointmentBook b) throws IOException {
        if(b.getOwnerName() == null){
            System.out.println(Project2.NO_OWNER_PROVIDED);
            return;
        }
        w.write(b.getOwnerName() + "\n");
        if(b.getAppointments() != null) {
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            for(Appointment appointment : appointments) {
                w.write(appointment.getDescription() + "\n");
                w.write(appointment.getBeginDate() + "\n");
                w.write(appointment.getBeginTimeS() + "\n");
                w.write(appointment.getEndDate() + "\n");
                w.write(appointment.getEndTimeS() + "\n");
            }
        }

        w.flush();
    }
}
