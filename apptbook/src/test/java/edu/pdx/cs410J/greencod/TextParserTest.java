package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {



    @Test
    @Disabled
    void cantParseEmptyFile() {
        InputStream inputStream = getClass().getResourceAsStream("emptyFile.txt");
        assertNotNull(inputStream);

        TextParser parser = new TextParser(new InputStreamReader((inputStream)));
        assertThrows(ParserException.class, parser::parse);
    }

    @Test
    void appointmentBookCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {
        File textFile = new File(dir,"appointments.txt");

        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(new Appointment(owner, "Head Transplant Consultation","12/31/3000", "11:00", "07/21/1992", "11:11"));


        book.addAppointment(new Appointment(owner, "Head Transplant Consultation","12/31/3000", "11:00", "07/21/1992", "11:11"));

        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(book);

        TextParser parser = new TextParser(new FileReader(textFile));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }

    @Test
    void noOwnerWriteToFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir, "appointments.txt");

        AppointmentBook book = new AppointmentBook(null);

        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.dump(book);
    }

    @Test
    @Disabled
    void noOwnerParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir, "appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(1);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }

    @Test
    @Disabled
    void noDescriptionParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir, "appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(2);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    @Test
    @Disabled
    void noBeginDateParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(3);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    @Test
    @Disabled
    void noBeginTimeParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(4);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    @Test
    @Disabled
    void noEndDateParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(5);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    @Test
    @Disabled
    void noEndTimeParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(6);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();;



    }
}
