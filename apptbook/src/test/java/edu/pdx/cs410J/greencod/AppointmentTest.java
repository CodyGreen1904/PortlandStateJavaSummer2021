package edu.pdx.cs410J.greencod;

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

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    assertThrows(UnsupportedOperationException.class, appointment::getBeginTimeString);
  }

  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), containsString("not implemented"));
  }

  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

  @Test
  void doesGetBeginTimeStringWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.getBeginTimeString(), containsString("07/21/1992 11:11"));
  }

  @Test
  void doesGetEndTimeStringWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.getEndTimeString(), containsString("07/21/1992 11:15"));
  }

  @Test
  void doesGetDescriptionWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.getDescription(), containsString("Haircut"));
  }

  @Test
  void doesToStringWork() {
    Appointment appointment = new Appointment("Cody", "Haircut", "07/21/1992", "11:11", "07/21/1992", "11:15");
    assertThat(appointment.toString(), containsString("Haircut from 07/21/1992 11:11 until 07/21/1992 11:15"));
  }



}
