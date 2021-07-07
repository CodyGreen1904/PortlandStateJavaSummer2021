package edu.pdx.cs410J.greencod;

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

  public static void main(String[] args) {
    String owner = null;
    String description = null;
    String beginDate = null;
    String beginTime = null;
    String endDate = null;
    String endTime = null;

    for(String arg : args) {
      if(owner == null) {
        owner = arg;
      } else if(description == null) {
        description = arg;
      } else if(beginDate == null) {
        beginDate = arg;
      }
    }

    Appointment appointment = new Appointment();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

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
    }

    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }

}