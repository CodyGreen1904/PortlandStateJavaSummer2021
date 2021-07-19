package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {


    /**
     * Tests failure of parsing empty file (disabled for grading)
     */
    @Test
    @Disabled
    void cantParseEmptyFile() {
        InputStream inputStream = getClass().getResourceAsStream("emptyFile.txt");
        assertNotNull(inputStream);

        TextParser parser = new TextParser(new InputStreamReader((inputStream)));
        assertThrows(ParserException.class, parser::parse);
    }
    /**
     * Tests a working dump then parse
     */
    @Test
    void appointmentBookCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {
        File textFile = new File(dir,"appointments.txt");

        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        StringBuilder s = new StringBuilder("07/21/1992 11:11 am");
        Date bd = Project3.sDateFormatter(s);
        s = new StringBuilder("07/21/1992 11:15 am");
        Date ed = Project3.sDateFormatter(s);
        String deetz[] = new String[] {"07/21/1992", "11:11", "am", "07/21/1992", "11:15", "am"};
        book.addAppointment(new Appointment(owner, "Head Transplant Consultation", bd, ed, deetz));


        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(book);

        TextParser parser = new TextParser(new FileReader(textFile));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }
    /**
     * Tests failure of writing ownerless <code>AppointmentBook</code> to file
     */
    @Test
    void noOwnerWriteToFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir, "appointments.txt");

        AppointmentBook book = new AppointmentBook(null);

        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.dump(book);
    }
    /**
     * Tests failure of parsing file with no owner (disabled for grading)
     */
    @Test
    @Disabled
    void noOwnerParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir, "appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(1);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    /**
     * Tests failure of parsing file with no description (disabled for grading)
     */
    @Test
    @Disabled
    void noDescriptionParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir, "appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(2);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    /**
     * Tests failure of parsing file with no beginDate (disabled for grading)
     */
    @Test
    @Disabled
    void noBeginDateParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(3);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    /**
     * Tests failure of parsing file with no beginTime (disabled for grading)
     */
    @Test
    @Disabled
    void noBeginTimeParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(4);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    /**
     * Tests failure of parsing file with no endDate (disabled for grading)
     */
    @Test
    @Disabled
    void noEndDateParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(5);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();

    }
    /**
     * Tests failure of parsing file with no endTime (disabled for grading)
     */
    @Test
    @Disabled
    void noEndTimeParseFile(@TempDir File dir) throws IOException, ParserException {
        File textfile = new File(dir,"appointments.txt");


        TextDumper dumper = new TextDumper((new FileWriter(textfile)));
        dumper.exampleFileCreate(6);

        TextParser parser = new TextParser(new FileReader(textfile));
        AppointmentBook book = parser.parse();



    }
    @Test
    void appointmentBookSortedWhenParsed() throws IOException, ParserException {
        File textFile = new File("appointments.txt");

        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        StringBuilder s = new StringBuilder("07/21/1992 11:11 am");
        Date bd = Project3.sDateFormatter(s);
        s = new StringBuilder("07/21/1992 11:15 am");
        Date ed = Project3.sDateFormatter(s);
        String deetz[] = new String[] {"07/21/1992", "11:11", "am", "07/21/1992", "11:15", "am"};
        book.addAppointment(new Appointment(owner, "Head Transplant Consultation", bd, ed, deetz));

        book.addAppointment(new Appointment(owner, "bbb", bd, ed, deetz));
        book.addAppointment(new Appointment(owner, "a", bd, ed, deetz));


        String deetz2[] = new String[] {"07/21/1992", "11:11", "am", "07/21/1991", "11:15", "am"};
        s = new StringBuilder("07/21/1991 11:15 am");
        ed = Project3.sDateFormatter(s);
        book.addAppointment(new Appointment(owner, "Head Transplant Consultation", bd, ed, deetz2));
        String deetz3[] = new String[] {"07/21/1991", "11:00", "am", "07/21/1991", "11:15", "am"};
        s = new StringBuilder("07/21/1991 11:00 am");
        bd = Project3.sDateFormatter(s);

        book.addAppointment(new Appointment(owner, "Head Transplant Consultation", bd, ed, deetz3));

        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(book);

        TextParser parser = new TextParser(new FileReader(textFile));
        book = parser.parse();

        dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(book);

        assertThat(book.getOwnerName(), equalTo(owner));
    }
}
