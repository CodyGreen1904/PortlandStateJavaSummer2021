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
            return new AppointmentBook(owner);
        } catch (IOException e) {
            throw new ParserException("Mistake in parser: ", e);
        }
    }
}
