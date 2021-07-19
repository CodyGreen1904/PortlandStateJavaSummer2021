package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;

/**
 * This class represents an <code>TextDumper</code>.
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
     * Creates a dummy file with text based on what num is equal to, used to test for missing args with TextParser
     * (not in use when grading)
     *
     * @param num
     *      Key used for switch statement
     * @throws IOException
     */
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

    /**
     * Used to write <code>AppointmentBook</code> to files
     * @param b
     *      <code>AppointmentBook</code> being added
     * @throws IOException
     */
    public void dump(AppointmentBook b) throws IOException {
        if(b.getOwnerName() == null){
            System.out.println(Project3.NO_OWNER_PROVIDED);
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
