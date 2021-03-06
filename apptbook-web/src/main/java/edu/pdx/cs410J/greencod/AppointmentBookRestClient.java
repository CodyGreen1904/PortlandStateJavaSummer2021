package edu.pdx.cs410J.greencod;
import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";

  private final String url;


  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }

  /**
   * Returns the definition for the given owner
   * @param owner
   * owner of appointment book
   * @return
   * returns definition
   * @throws IOException
   * throws IOException
   */
  public AppointmentBook getAppointments(String owner) throws IOException, ParserException {
    Response response = get(this.url, Map.of("owner", owner));
    if(response.getCode() != HTTP_OK){
      System.err.println("Owner Doesnt Exist");
      System.exit(1);
    }
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  /**
   * Creates an appointment and adds it to the servlet
   * @param owner
   * Owner of apptbook
   * @param appointment
   * Appt to add
   * @throws IOException
   * Throws IOException
   */
  public void createAppointment(String owner, Appointment appointment) throws IOException {

    String beginAsString = formatDateForHttpRequest(appointment.getBeginTime());
    String endAsString = formatDateForHttpRequest(appointment.getEndTime());

    Response response = postToMyURL(Map.of("owner", owner, "description", appointment.getDescription(), "begin", beginAsString, "end", endAsString));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * Takes in a date and returns it in a format for an HTTP request
   * @param time
   * Date to convert
   * @return
   * Returns string for HTTP request
   */
  private String formatDateForHttpRequest(Date time) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    return simpleDateFormat.format(time);
  }

  @VisibleForTesting
  Response postToMyURL(Map<String, String> appointmentInfo) throws IOException {
    return post(this.url, appointmentInfo);
  }

  /**
   * Deletes all apptbooks
   * @throws IOException
   * Throws IOExcpetion
   */
  public void removeAllAppointmentBooks() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
    return response;
  }

}