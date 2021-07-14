package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private BufferedReader r;

    public TextParser(Reader r) {
        this.r = new BufferedReader(r);
    }

    public AppointmentBook parse() throws ParserException {
        try {
            if(!r.ready()) {
                throw new ParserException("No Owner Provided");
            }
            String owner = r.readLine();
            AppointmentBook newAppointmentBook = new AppointmentBook(owner);
            String line;
            String description = null;
            String beginDate;
            String beginTime;
            String endDate;
            String endTime;
            if(owner != null){
                while((line = r.readLine()) != null) {
                    description = line;
                    beginDate = r.readLine();
                    if(beginDate == null){
                        throw new ParserException("Missing Begin Date");
                    }
                    beginTime = r.readLine();
                    if(beginTime == null){
                        throw new ParserException("Missing Begin Time");
                    }
                    endDate = r.readLine();
                    if(endDate == null){
                        throw new ParserException("Missing End Date");
                    }
                    endTime = r.readLine();
                    if(endTime == null){
                        throw new ParserException("Missing End Time");
                    }
                    Appointment newAppointment = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
                    newAppointmentBook.addAppointment(newAppointment);
                }
                if(description == null){
                    throw new ParserException("Missing Description");
                }
            }

            return newAppointmentBook;
        } catch (IOException e) {
            throw new ParserException("Mistake in parser: ", e);
        }
    }
}
