package edu.pdx.cs410J.greencod;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static edu.pdx.cs410J.greencod.Project4.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AppointmentBookServlet extends HttpServlet
{
    static final String OWNER_PARAMETER = "owner";
    static final String DESCRIPTION_PARAMETER = "description";
    private static final String BEGINDATE_PARAMETER = "begin";
    private static final String ENDDATE_PARAMETER = "end";

    private static final String ENDBEFOREBEGIN_PARAMETER = "APPT ENDS BEFORE BEGINNING";

    private final Map<String, AppointmentBook> books = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter(OWNER_PARAMETER, request );
        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);

        } else {
            writeAppointmentBook(owner, response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter(OWNER_PARAMETER, request );
        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);
            return;
        }

        String description = getParameter(DESCRIPTION_PARAMETER, request );
        if ( description == null) {
            missingRequiredParameter( response, DESCRIPTION_PARAMETER);
            return;
        }

        String beginDate = getParameter(BEGINDATE_PARAMETER, request );
        if ( beginDate == null) {
            missingRequiredParameter( response, BEGINDATE_PARAMETER);
            return;
        }
        String endDate = getParameter(ENDDATE_PARAMETER, request );
        if ( endDate == null) {
            missingRequiredParameter( response, ENDDATE_PARAMETER);
            return;
        }

        AppointmentBook book = this.books.get(owner);
        if (book == null) {
            book = createAppointmentBook(owner);
        }
        String[] splBegin = beginDate.split(" ");
        StringBuilder dateString = sToSb(splBegin[0], splBegin[1], splBegin[2]);
        Date beginD = sDateFormatter(dateString);

        String[] splEnd = endDate.split(" ");
        dateString = sToSb(splEnd[0], splEnd[1], splEnd[2]);
        Date endD = sDateFormatter(dateString);

        if(beginD.compareTo(endD) >= 0){
            missingRequiredParameter( response, ENDBEFOREBEGIN_PARAMETER);
            System.exit(1);
        }

        String[] deetz = new String[] {splBegin[0], splBegin[1], splBegin[2], splEnd[0], splEnd[1], splEnd[2]};
        Appointment appointment = new Appointment(owner, description, beginD, endD, deetz);
        book.addAppointment(appointment);
        PrintWriter pw = response.getWriter();
        pw.println("Added " + description + " to " + owner + "'s AppointmentBook" );
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);

    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.books.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    private void writeAppointmentBook(String owner, HttpServletResponse response) throws IOException {
        AppointmentBook book = this.books.get(owner);

        if (book == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter pw = response.getWriter();
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(book);
            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    @VisibleForTesting
    AppointmentBook getAppointmentBook(String owner) {
        return this.books.get(owner);
    }

    public AppointmentBook createAppointmentBook(String owner) {
        AppointmentBook book = new AppointmentBook(owner);
        this.books.put(owner, book);
        return book;
    }
}
