package edu.pdx.cs410J.greencod;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
   * Shows beginTimeString functions correctly
   */
  @Test
  void doesGetBeginTimeStringWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.getBeginTimeString(), containsString("07/21/1992 11:11"));
  }

  /**
   * Shows endTimeString functions correctly
   */
  @Test
  void doesGetEndTimeStringWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.getEndTimeString(), containsString("07/21/1992 11:15"));
  }

  /**
   * Shows getDescription functions correctly
   */
  @Test
  void doesGetDescriptionWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.getDescription(), containsString("Haircut"));
  }

  /**
   * Shows toString functions correctly
   */
  @Test
  void doesToStringWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.toString(), containsString("Haircut from 07/21/1992 11:11 until 07/21/1992 11:15"));
  }

  @Test
  void doesOwnerNameWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    AppointmentBook appointmentBook = new AppointmentBook("Cody", appointment);
    assertThat(appointmentBook.getOwnerName(), containsString("Cody"));
  }
  @Test
  void doesAddAppointmentAndGetAppointmentWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    AppointmentBook appointmentBook = new AppointmentBook("Cody", appointment);
    appointmentBook.addAppointment(appointment);
    for(Appointment appnt : appointmentBook.getAppointments()) {
      assertThat(appnt.toString(), containsString("Haircut from 07/21/1992 11:11 until 07/21/1992 11:15"));
    }
    assertThat(appointmentBook.getOwnerName(), containsString("Cody"));
  }
}
