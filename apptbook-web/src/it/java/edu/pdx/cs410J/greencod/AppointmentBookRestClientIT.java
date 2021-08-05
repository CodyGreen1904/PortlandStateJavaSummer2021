package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.Map;

import static edu.pdx.cs410J.greencod.Project4.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllAppointmentBooks() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllAppointmentBooks();
  }

  @Test
  void test2CreateAppointmentBookWithOneAppointment() throws IOException, ParserException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String owner = "Dave";
    String description = "Teach Java";
    String beginDate = "07/21/1992";
    String beginTime = "11:11";
    String beginPeriod = "am";
    String endDate = "07/21/1992";
    String endTime = "11:11";
    String endPeriod = "pm";

    StringBuilder dateString = sToSb(beginDate, beginTime, beginPeriod);

    Date beginD = sDateFormatter(dateString);
    dateString = sToSb(endDate, endTime, endPeriod);
    Date endD = sDateFormatter(dateString);
    if(beginD.compareTo(endD) >= 0){
      System.err.println(BEGIN_AFTER_END);
      System.exit(1);
    }

    String[] deetz = new String[] {beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod};

    Appointment appointmentToAdd = new Appointment(owner, description, beginD, endD, deetz);
    client.createAppointment(owner, appointmentToAdd);

    AppointmentBook book = client.getAppointments(owner);
    assertThat(book.getOwnerName(), equalTo(owner));

    Appointment appointment = book.getAppointments().iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));
  }

  @Test
  void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }

}
