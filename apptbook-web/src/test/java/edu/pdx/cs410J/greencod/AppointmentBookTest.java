package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import static edu.pdx.cs410J.greencod.Project4.sDateFormatter;
import static edu.pdx.cs410J.greencod.Project4.sToSb;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AppointmentBookTest {

    @Test
    void appointmentBookCanBeDumpedAndParsed() throws IOException, ParserException {
        AppointmentBook book1 = new AppointmentBook("Owner");
        String owner = "Dave";
        String description = "Teach Java";
        String beginDate = "07/21/1992";
        String beginTime = "11:11";
        String beginPeriod = "am";
        String endDate = "07/21/1992";
        String endTime = "11:11";
        String endPeriod = "am";

        StringBuilder dateString = sToSb(beginDate, beginTime, beginPeriod);

        Date beginD = sDateFormatter(dateString);
        dateString = sToSb(endDate, endTime, endPeriod);
        Date endD = sDateFormatter(dateString);
        String deetz[] = new String[] {beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod};
        book1.addAppointment(new Appointment(owner, description, beginD, endD, deetz));

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