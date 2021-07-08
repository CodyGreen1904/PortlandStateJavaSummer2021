package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  private String owner = null;
  private String description = null;
  private String beginDate = null;
  private String beginTime = null;
  private String endDate = null;
  private String endTime = null;

  public  Appointment(){}

  public Appointment(String owner, String description, String beginDate, String beginTime, String endDate, String endTime) {
    this.owner = owner;
    this.description = description;
    this.beginDate = beginDate;
    this.beginTime = beginTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  @Override
  public String getBeginTimeString() {
    return beginDate + " " + beginTime;
  }

  @Override
  public String getEndTimeString() {
    return endDate + " " + endTime;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
