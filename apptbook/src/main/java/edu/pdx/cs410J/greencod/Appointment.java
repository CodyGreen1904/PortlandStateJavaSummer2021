package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * This class represents an <code>Appointment</code>.
 */

public class Appointment extends AbstractAppointment {

  private String owner = null;
  private String description = null;
  private String beginDate = null;
  private String beginTime = null;
  private String endDate = null;
  private String endTime = null;

  /**
   * Creates a new <code>Appointment</code>
   */
  public Appointment() {}

  /**
   * Creates a new <code>Appointment</code>
   *
   * @param owner
   *        The owner of the appointment
   * @param description
   *        a brief description of the appointment
   * @param beginDate
   *        The start date of the appointment
   * @param beginTime
   *        The start time of the appointment
   * @param endDate
   *        The end date of the appointment
   * @param endTime
   *        The end time of the appointment
   */
  public Appointment(String owner, String description, String beginDate, String beginTime, String endDate, String endTime) {
    this.owner = owner;
    this.description = description;
    this.beginDate = beginDate;
    this.beginTime = beginTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  /**
   * Returns the <code>String</code> combination of
   * <code>beginDate</code> and <code>beginTime</code>
   */
  @Override
  public String getBeginTimeString() {
    return beginDate + " " + beginTime;
  }
  /**
   * Returns the <code>String</code> combination of
   * <code>endDate</code> and <code>endTime</code>
   */
  @Override
  public String getEndTimeString() {
    return endDate + " " + endTime;
  }
  /**
   * Returns the <code>String</code> <code>description</code>
   */
  @Override
  public String getDescription() {
    return description;
  }
}
