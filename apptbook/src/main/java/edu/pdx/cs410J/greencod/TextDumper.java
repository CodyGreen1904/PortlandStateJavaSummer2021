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
        w.write(b.getOwnerName());
        w.flush();
        w.flush();
    }
}
