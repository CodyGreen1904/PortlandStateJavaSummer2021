package edu.pdx.cs410j.greencod;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import static edu.pdx.cs410j.greencod.MakeAppointment.sDateFormatter;
import static edu.pdx.cs410j.greencod.MakeAppointment.validateDate;
import static edu.pdx.cs410j.greencod.MakeAppointment.validatePeriod;
import static edu.pdx.cs410j.greencod.MakeAppointment.validateTime;

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
    public AppointmentBook parse(){
        try {
            if(!r.ready()) {
                return null;
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
                while((line = r.readLine()) != null) {
                    description = line;
                    beginDate = r.readLine();
                    if(beginDate == null || !validateDate(beginDate)){
                        return null;
                    }
                    beginTime = r.readLine();
                    if(beginTime == null || !validateTime(beginTime)){
                        return null;
                    }
                    beginPeriod = r.readLine();
                    if(beginPeriod == null || !validatePeriod(beginPeriod)){
                        return null;
                    }
                    endDate = r.readLine();
                    if(endDate == null || !validateDate(endDate)) {
                        return null;
                    }
                    endTime = r.readLine();
                    if(endTime == null || !validateTime(endTime)){
                        return null;
                    }
                    endPeriod = r.readLine();
                    if(endPeriod == null || !validatePeriod(endPeriod)){
                        return null;
                    }
                    Date bd = sDateFormatter(beginDate + " " + beginTime + " " + beginPeriod);
                    Date ed = sDateFormatter(endDate + " " + endTime + " " + endPeriod);
                    String[] deetz = new String[] {beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod};
                    Appointment newAppointment = new Appointment(owner, description, bd, ed, deetz);
                    newAppointmentBook.addAppointment(newAppointment);
                }
                if(description == null){
                    return null;
                }
            }
            return newAppointmentBook;
        } catch (IOException e) {
            return null;
        }
    }
}
