package edu.pdx.cs410J.greencod;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest {

  /**
   * Fails, meaning BeginTimeString has been implemented
   */
  @Test
  @Disabled
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    assertThrows(UnsupportedOperationException.class, appointment::getBeginTimeString);
  }
  /**
   * Shows all appointment descriptions are null on creation
   */
  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), equalTo(null));
  }

  /**
   * Shows beginTime is null
   */
  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

  /**
   * Shows getDescription functions correctly
   */
  @Test
  void doesGetDescriptionWork() {
    StringBuilder s = new StringBuilder("07/21/1992 11:11 am");
    Date bd = Project3.sDateFormatter(s);
    s = new StringBuilder("07/21/1992 11:15 am");
    Date ed = Project3.sDateFormatter(s);
    String[] deetz = new String[] {"07/21/1992", "11:11", "am", "07/21/1992", "11:15", "am"};
    Appointment appointment = new Appointment("Cody", "Haircut", bd, ed, deetz);
    assertThat(appointment.getDescription(), containsString("Haircut"));
  }

  /**
   * Shows toString functions correctly
   */
  @Test
  void doesToStringWork() {
    StringBuilder s = new StringBuilder("07/21/1992 11:11 am");
    Date bd = Project3.sDateFormatter(s);
    s = new StringBuilder("07/21/1992 11:15 am");
    Date ed = Project3.sDateFormatter(s);
    String deetz[] = new String[] {"07/21/1992", "11:11", "am", "07/21/1992", "11:15", "am"};
    Appointment appointment = new Appointment("Cody", "Haircut", bd, ed, deetz);
    assertThat(appointment.toString(), containsString("Haircut from 7/21/92, 11:11 AM until 7/21/92, 11:15 AM"));
  }

  @Test
  void doesOwnerNameWork() {
    StringBuilder s = new StringBuilder("07/21/1992 11:11 am");
    Date bd = Project3.sDateFormatter(s);
    s = new StringBuilder("07/21/1992 11:15 am");
    Date ed = Project3.sDateFormatter(s);
    String deetz[] = new String[] {"07/21/1992", "11:11", "am", "07/21/1992", "11:15", "am"};
    Appointment appointment = new Appointment("Cody", "Haircut", bd, ed, deetz);
    AppointmentBook appointmentBook = new AppointmentBook("Cody", appointment);
    assertThat(appointmentBook.getOwnerName(), containsString("Cody"));
  }
  @Test
  void doesAddAppointmentAndGetAppointmentWork() {
    StringBuilder s = new StringBuilder("07/21/1992 11:11 am");
    Date bd = Project3.sDateFormatter(s);
    s = new StringBuilder("07/21/1992 11:15 am");
    Date ed = Project3.sDateFormatter(s);
    String deetz[] = new String[] {"07/21/1992", "11:11", "am", "07/21/1992", "11:15", "am"};
    Appointment appointment = new Appointment("Cody", "Haircut", bd, ed, deetz);
    AppointmentBook appointmentBook = new AppointmentBook("Cody", appointment);
    appointmentBook.addAppointment(appointment);
    for(Appointment appnt : appointmentBook.getAppointments()) {
      assertThat(appnt.toString(), containsString("Haircut from 7/21/92, 11:11 AM until 7/21/92, 11:15 AM"));
    }
    assertThat(appointmentBook.getOwnerName(), containsString("Cody"));
  }
}
