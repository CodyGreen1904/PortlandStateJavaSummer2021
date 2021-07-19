package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project3 {

  public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
  public static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
          "args are (in this order):\n" +
          "owner The person whose owns the appt book\n" +
          "description A description of the appointment\n" +
          "begin When the appt begins\n" +
          "end When the appt ends\n" +
          "options are (options may appear in any order):\n" +
          "-pretty file Pretty print the appointment book to\n" +
          "a text file or standard out (file -)\n" +
          "-textFile file Where to read/write the appt book info\n" +
          "-print Prints a description of the new appointment\n" +
          "-README Prints a README for this project and exits";
  public static final String MISSING_DESCRIPTION = "Missing Description";
  public static final String MISSING_BEGIN_DATE = "Missing begin date";
  public static final String MISSING_BEGIN_TIME = "Missing begin time";
  public static final String MISSING_END_DATE = "Missing end date";
  public static final String MISSING_END_TIME = "Missing end time";
  public static final String TIME_NOT_CORRECT = "Incorrect Time: please use 12 hour time in format HH:MM with either am or pm";
  public static final String DATE_NOT_CORRECT = "Incorrect date: Please use mm/dd/yyyy";
  public static final String TOO_MANY_ARGUMENTS = "Too many command line arguments";
  public static final String UNKNOWN_COMMAND_LINE_ARGUMENT = "Unknown command line argument";
  public static final String OWNERS_DONT_MATCH = "Owner name in file is different than owner provided";
  public static final String NO_OWNER_PROVIDED = "Somehow the program attempted to write an AppointmentBook with no owner to a file, file not created";
  public static final String ERROR_PARSING_DATE = "Incorrect date format, use MM/dd/yyyy hh:mm a";
  public static final String MISSING_BEGIN_PERIOD = "Missing begin time period";
  public static final String MISSING_END_PERIOD = "Missing end time period";
  public static final String BEGIN_AFTER_END = "The appointment's start time must be before the appointment's end time";

  public static StringBuilder sToSb(String date, String time, String period) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(date + " " + time + " " + period);

    return stringBuilder;
  }
  public static long getMinutes(Date d1, Date d2){
    long difference = d2.getTime() - d1.getTime();
    difference = TimeUnit.MILLISECONDS.toMinutes(difference);
    return difference;
  }


  /**
   * Displays the README file and exits
   */
  public void readMe() throws IOException {
    try (
            InputStream readme = Project3.class.getResourceAsStream("README.txt")
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



    if(hour < 0 || hour > 12 || minute < 0 || minute > 59){
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
   * Ensures the format of period is am or pm
   */
  public static String validatePeriod(String old) {
    if(old.equalsIgnoreCase("am")  || old.equalsIgnoreCase("pm")) {
      return old;
    } else {
      System.err.println(TIME_NOT_CORRECT);
      System.exit(1);
    }
    return old;
  }

  public static Date sDateFormatter(StringBuilder old){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    try {
      return simpleDateFormat.parse(old.toString());
    } catch (ParseException e) {
      System.err.println("Problem parsing string into date");
      System.exit(1);
    }
    return null;
  }

  /**
   * Main program that parses the command line, verifies input creates a
   * <code>Project3</code>, creates an <code>Appointment</code>
   * , an <code>AppointmentBook</code>, and adds the appointment to
   * the appointment book. Also shows the README with
   * <code>readMe()</code> and the -print with the <code>Apppointment</code>
   * <code>toString</code> method.
   */
  public static void main(String[] args) throws IOException, NumberFormatException, ParserException {
    String owner = null;
    String description = null;
    String beginDate = null;
    String beginTime = null;
    String beginPeriod = null;
    String endDate = null;
    String endTime = null;
    String endPeriod = null;
    boolean fileFlag = false;
    String fileLocation = null;
    boolean printFlag = false;
    boolean prettyPrintFlag = false;
    boolean prettyFileFlag = false;
    String prettyFileLocation = null;
    Date beginD = null;
    Date endD = null;


    Project3 p = new Project3();
    for(String arg : args) {
      if(fileFlag) {
        fileLocation = arg;
        fileFlag = false;
        continue;
      }
      if (prettyFileFlag) {
        if(arg.equals("-")){
          prettyFileFlag = false;
          prettyPrintFlag = true;
          continue;
        }
        prettyFileLocation = arg;
        prettyFileFlag = false;
        continue;
      }
      if(arg.startsWith("-")){
        if(arg.equals("-README")) {
          p.readMe();
          System.exit(0);
        }
        if(arg.equals("-print")) {
          printFlag = true;
          continue;
        } else if(arg.equals("-textFile")) {
          fileFlag = true;
          continue;
        } else if(arg.equals("-pretty")){
          prettyFileFlag = true;
          continue;
        }
        else{
          System.err.println(UNKNOWN_COMMAND_LINE_ARGUMENT);
          System.err.println(USAGE_MESSAGE);
          System.exit(1);
        }
      } else if(owner == null) {
        owner = arg;
      } else if(description == null) {
        description = arg;
      } else if(beginDate == null) {
        beginDate = validateDate(arg);
      } else if(beginTime == null) {
        beginTime = validateTime(arg);
      } else if(beginPeriod == null) {
        beginPeriod = validatePeriod(arg);
      } else if(endDate == null) {
        endDate = validateDate(arg);
      } else if(endTime == null) {
        endTime = validateTime(arg);
      } else if(endPeriod == null) {
        endPeriod = validatePeriod(arg);
      } else {
        System.err.println(TOO_MANY_ARGUMENTS);
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
    } else if(beginPeriod == null){
      System.err.println(MISSING_BEGIN_PERIOD);
      System.exit(1);
    } else if(endDate == null) {
      System.err.println(MISSING_END_DATE);
      System.exit(1);
    } else if(endTime == null) {
      System.err.println(MISSING_END_TIME);
      System.exit(1);
    } else if(endPeriod == null) {
      System.err.println(MISSING_END_PERIOD);
      System.exit(1);
    }

    StringBuilder dateString = sToSb(beginDate, beginTime, beginPeriod);

    beginD = sDateFormatter(dateString);
    dateString = sToSb(endDate, endTime, endPeriod);
    endD = sDateFormatter(dateString);

    if(beginD.compareTo(endD) >= 0){
      System.err.println(BEGIN_AFTER_END);
      System.exit(1);
    }

    String deetz[] = new String[] {beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod};

    Appointment appointmentToAdd = new Appointment(owner, description, beginD, endD, deetz);
    AppointmentBook appointmentBook = null;
    if(fileLocation != null || prettyFileLocation != null) {
      if (fileLocation != null) {
        TextParser parser;
        TextDumper dumper;
        File textFile = new File(fileLocation);
        if(textFile.exists()) {
          parser = new TextParser((new FileReader(textFile)));
          appointmentBook = parser.parse();
          if (!appointmentBook.getOwnerName().equals(owner)) {
            System.err.println(OWNERS_DONT_MATCH);
            System.exit(1);
          }
        } else {
          appointmentBook = new AppointmentBook(owner);
        }
        appointmentBook.addAppointment(appointmentToAdd);


        dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(appointmentBook);
      }
      if (prettyFileLocation != null) {
        PrettyPrinter prettyDumper;
        File prettytextFile = new File(prettyFileLocation);

        appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointmentToAdd);

        prettyDumper = new PrettyPrinter(new FileWriter(prettytextFile));
        prettyDumper.dump(appointmentBook);
      }
    }else {
      appointmentBook = new AppointmentBook(owner);
      appointmentBook.addAppointment(appointmentToAdd);
    }

    if (printFlag) {
      System.out.println(appointmentToAdd);
    }
    if (prettyPrintFlag) {
      System.out.println(PrettyPrinter.prettyPrint(appointmentBook));
    }

    System.out.println();

    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }
}