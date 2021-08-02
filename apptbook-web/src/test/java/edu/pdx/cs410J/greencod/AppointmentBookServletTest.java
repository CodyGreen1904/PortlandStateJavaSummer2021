package edu.pdx.cs410J.greencod;

import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static edu.pdx.cs410J.greencod.Project4.sDateFormatter;
import static edu.pdx.cs410J.greencod.Project4.sToSb;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {

  @Test
  void gettingAppointmentBookReturnsTextFormat() throws ServletException, IOException {
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

    AppointmentBookServlet servlet = new AppointmentBookServlet();
    AppointmentBook book = servlet.createAppointmentBook(owner);
    book.addAppointment(new Appointment(owner, description, beginD, endD, deetz));

    Map<String, String> queryParams = Map.of("owner", owner);
    StringWriter sw = invokeServletMethod(queryParams, servlet::doGet);

    String text = sw.toString();
    assertThat(text, containsString(owner));
    assertThat(text, containsString(description));
  }

  private StringWriter invokeServletMethod(Map<String, String> params, ServletMethodInvoker invoker) throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    params.forEach((key, value) -> when(request.getParameter(key)).thenReturn(value));

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(sw));

    invoker.invoke(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);
    return sw;
  }

  @Test
  void addAppointment() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

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

    invokeServletMethod(Map.of("owner", owner, "description", description), servlet::doPost);

    AppointmentBook book = servlet.getAppointmentBook(owner);
    assertThat(book, notNullValue());
    assertThat(book.getOwnerName(), equalTo(owner));

    Collection<Appointment> appointments = book.getAppointments();
    assertThat(appointments, hasSize(1));

    Appointment appointment = appointments.iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));

  }

  private interface ServletMethodInvoker {
    void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
  }
}
