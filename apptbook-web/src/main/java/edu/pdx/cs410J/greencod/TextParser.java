package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

/**
 * This class represents a <code>TextParser</code>
 */
public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private BufferedReader r;

    /**
     * Creates a new <code>TextParser</code>
     * @param r
     *      The Reader for TextParser
     */
    public TextParser(Reader r) {
        this.r = new BufferedReader(r);
    }

    /**
     * Parses a file and builds an <code>AppointmentBook</code>
     * with the information
     * @return
     *      null if nothing, built <code>AppointmentBook</code> if success
     * @throws ParserException
     *          With explanation
     */
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
            String beginPeriod;
            String endPeriod;
            if(owner != null){
                System.out.println("Checking contents of file....");

                while((line = r.readLine()) != null) {
                    description = line;
                    beginDate = r.readLine();
                    if(beginDate == null){
                        throw new ParserException("Missing Begin Date");
                    }
                    Project4.validateDate(beginDate);
                    beginTime = r.readLine();
                    if(beginTime == null){
                        throw new ParserException("Missing Begin Time");
                    }
                    Project4.validateTime(beginTime);
                    beginPeriod = r.readLine();
                    if(beginPeriod == null){
                        throw new ParserException("Missing Begin Period");
                    }
                    Project4.validatePeriod(beginPeriod);
                    endDate = r.readLine();
                    if(endDate == null){
                        throw new ParserException("Missing End Date");
                    }
                    endDate = Project4.validateDate(endDate);
                    endTime = r.readLine();
                    if(endTime == null){
                        throw new ParserException("Missing End Time");
                    }
                    Project4.validateTime(endTime);
                    endPeriod = r.readLine();
                    if(endPeriod == null){
                        throw new ParserException("Missing End Period");
                    }
                    Project4.validatePeriod(endPeriod);
                    StringBuilder dateString = Project4.sToSb(beginDate, beginTime, beginPeriod);
                    Date bd = Project4.sDateFormatter(dateString);
                    dateString = Project4.sToSb(endDate, endTime, endPeriod);
                    Date ed = Project4.sDateFormatter(dateString);
                    String deetz[] = new String[] {beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod};
                    Appointment newAppointment = new Appointment(owner, description, bd, ed, deetz);
                    newAppointmentBook.addAppointment(newAppointment);
                }
                if(description == null){
                    throw new ParserException("Missing Description");
                }
            }
            System.out.println("file contents good");
            return newAppointmentBook;
        } catch (ParserException e) {
            System.err.println("Mistake in parser: " + e);
            System.exit(1);
        } catch (IOException p) {
            System.err.println("Mistake in parser: " + p);
            System.exit(1);
        }
        return null;
    }
}