package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AppointmentBookTest {

    @Test
    void appointmentBookCanBeDumpedAndParsed() throws IOException, ParserException {
        AppointmentBook book1 = new AppointmentBook("Owner");
        String description = "Description";
        book1.addAppointment(new Appointment(description));

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book1);

        StringReader sr = new StringReader(sw.toString());
        TextParser parser = new TextParser(sr);

        AppointmentBook book2 = parser.parse();
        assertThat(book2.getOwnerName(), equalTo(book1.getOwnerName()));

        Appointment appointment = book2.getAppointments().iterator().next();
        assertThat(appointment.getDescription(), equalTo(description));
    }
}