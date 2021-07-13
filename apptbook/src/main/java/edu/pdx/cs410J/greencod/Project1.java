package edu.pdx.cs410J.greencod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
  public static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
          "args are (in this order):\n" +
          "owner The person who owns the appt book\n" +
          "description A description of the appointment\n" +
          "begin When the appt begins (24-hour time)\n" +
          "end When the appt ends (24-hour time)\n" +
          "options are (options may appear in any order):\n" +
          "-print Prints a description of the new appointment\n" +
          "-README Prints a README for this project and exits\n" +
          "Date and time should be in the format: mm/dd/yyyy hh:mm";
  public static final String MISSING_DESCRIPTION = "Missing Description";
  public static final String MISSING_BEGIN_DATE = "Missing begin date";
  public static final String MISSING_BEGIN_TIME = "Missing begin time";
  public static final String MISSING_END_DATE = "Missing end date";
  public static final String MISSING_END_TIME = "Missing end time";
  public static final String TIME_NOT_CORRECT = "Incorrect Time: please use military time in format HH:MM";
  public static final String DATE_NOT_CORRECT = "Incorrect date: Please use mm/dd/yyyy";

  /**
   * Displays the README file and exits
   */
  public void readMe() throws IOException {
    try (
            InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      System.out.println(line);
      System.out.println(USAGE_MESSAGE);
    }
    catch (IOException i) {
      System.err.println("error getting readme");
      throw i;
    }
  }

  /**
   * Ensures the format of time is HH:MM
   */
  public static String validateTime(String old) throws NumberFormatException{
    String[] ar = old.split(":");
    if(ar.length != 2) {
      System.err.println(TIME_NOT_CORRECT);
      System.exit(1);
    }

    int hour = 0;
    int minute = 0;
    if(allNumsTime(ar)){
      hour = Integer.parseInt(ar[0]);
      minute = Integer.parseInt(ar[1]);
    } else {
      System.err.println(TIME_NOT_CORRECT);
      System.exit(1);
    }



    if(hour < 0 || hour > 23 || minute < 0 || minute > 59){
      System.err.println(TIME_NOT_CORRECT);
      System.exit(1);
    }
    return old;
  }
  /**
   * Checks for characters in time
   */
  public static boolean allNumsTime(String[] ar) throws NumberFormatException{
    try {
      int num = Integer.parseInt(ar[0]);
      num = Integer.parseInt(ar[1]);
    } catch (NumberFormatException e){
      return false;
    }
    return true;
  }
  /**
   * Checks for characters in date
   */
  public static boolean allNumsDate(String[] ar) throws NumberFormatException{
    try {
      int num = Integer.parseInt(ar[0]);
      num = Integer.parseInt(ar[1]);
      num = Integer.parseInt(ar[2]);
    } catch (NumberFormatException e){
      return false;
    }
    return true;
  }
  /**
   * Ensures the format of date is mm/dd/yyyy
   */
  public static String validateDate(String old) throws NumberFormatException{
    String[] ar = old.split("/");
    if(ar.length != 3){
      System.err.println(DATE_NOT_CORRECT);
      System.exit(1);
    }
    if(ar[0].length() > 2 || ar[1].length() > 2 || ar[2].length() != 4) {
      System.err.println(DATE_NOT_CORRECT);
      System.exit(1);
    }

    int month = 0;
    int day = 0;
    int year = 0;

    if(allNumsDate(ar)){
      month = Integer.parseInt(ar[0]);
      day = Integer.parseInt(ar[1]);
      year = Integer.parseInt(ar[2]);
    } else {
      System.err.println(DATE_NOT_CORRECT);
      System.exit(1);
    }






    boolean leap = false;

    if(month < 0 || month > 12) {
      System.err.println(DATE_NOT_CORRECT);
      System.exit(1);
    }
    if(month == 2) {
      if(year % 4 == 0) {
        if(year % 100 == 0) {
          if(year % 400 == 0) {
            leap = true;
          }
        } else {
          leap = true;
        }
      }
      if(!leap) {
        if(day < 0 || day > 28) {
          System.err.println(DATE_NOT_CORRECT);
          System.exit(1);
        }
      } else {
        if(day < 0 || day > 29) {
          System.err.println(DATE_NOT_CORRECT);
          System.exit(1);
        }
      }
    } else if((month < 8 && month % 2 == 1) || (month > 7 && month % 2 == 0)) {
      if(day < 0 || day > 31) {
        System.err.println(DATE_NOT_CORRECT);
        System.exit(1);
      }
    } else {
      if(day < 0 || day > 30) {
        System.err.println(DATE_NOT_CORRECT);
        System.exit(1);
      }
    }

    return old;
  }

  /**
   * Main program that parses the command line, verifies input creates a
   * <code>Project1</code>, creates an <code>Appointment</code>
   * , an <code>AppointmentBook</code>, and adds the appointment to
   * the appointment book. Also shows the README with
   * <code>readMe()</code> and the -print with the <code>Apppointment</code>
   * <code>toString</code> method.
   */
  public static void main(String[] args) throws IOException, NumberFormatException{
    String owner = null;
    String description = null;
    String beginDate = null;
    String beginTime = null;
    String endDate = null;
    String endTime = null;
    boolean flag = false;

    Project1 p = new Project1();
    for(String arg : args) {
      if(arg.equals("-README")) {
        p.readMe();
        System.exit(0);
      }
      if(arg.equals("-print")) {
        flag = true;
      } else if(owner == null) {
        owner = arg;
      } else if(description == null) {
        description = arg;
      } else if(beginDate == null) {
        beginDate = validateDate(arg);
      } else if(beginTime == null) {
        beginTime = validateTime(arg);
      } else if(endDate == null) {
        endDate = validateDate(arg);
      } else if(endTime == null) {
        endTime = validateTime(arg);
      } else {
        System.err.println("Too many command line arguments");
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
      }
    }


    if(owner == null){
      System.err.println(MISSING_COMMAND_LINE_ARGUMENTS);
      System.err.println(USAGE_MESSAGE);
      System.exit(1);
    } else if(description == null) {
      System.err.println(MISSING_DESCRIPTION);
      System.exit(1);
    } else if(beginDate == null) {
      System.err.println(MISSING_BEGIN_DATE);
      System.exit(1);
    } else if(beginTime == null) {
      System.err.println(MISSING_BEGIN_TIME);
      System.exit(1);
    } else if(endDate == null) {
      System.err.println(MISSING_END_DATE);
      System.exit(1);
    } else if(endTime == null) {
      System.err.println(MISSING_END_TIME);
      System.exit(1);
    }

    Appointment appointment = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);

    AppointmentBook appointmentBook = new AppointmentBook(owner, appointment);

    if (flag) {
      System.out.println(appointment);
    }

    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }

}