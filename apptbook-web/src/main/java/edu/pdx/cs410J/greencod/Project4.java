package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
    public static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>\n" +
            "args are (in this order):\n" +
            "owner The person who owns the appt book\n" +
            "description A description of the appointment\n" +
            "begin When the appt begins\n" +
            "end When the appt ends\n" +
            "options are (options may appear in any order):\n" +
            "-host hostname Host computer on which the server runs\n" +
            "-port port Port on which the server is listening\n" +
            "-search Appointments should be searched for\n" +
            "-print Prints a description of the new appointment\n" +
            "-README Prints a README for this project and exits\n";
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

    /**
     * Pulls in three strings and returns a stringbuilder in the proper date format
     * @param date
     *  holds date string
     * @param time
     *  holds time string
     * @param period
     *  holds period string
     * @return
     */
    public static StringBuilder sToSb(String date, String time, String period) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date + " " + time + " " + period);

        return stringBuilder;
    }
    /**
     * Pulls in two dates and returns the minute difference in time
     * @param d1
     *  The starting date
     * @param d2
     * The ending date
     * @return
     */
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
                InputStream readme = Project4.class.getResourceAsStream("README.txt")
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
    /**
     * Pulls in a Stringbuildder in a MM/dd/yyyy hh:mm a format and returns a date parsed form it
     * @param old
     *  Stringbuilder that contains a string in a date format
     * @return
     */
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

    public static void main(String... args) throws IOException{
        String hostName = null;
        String portString = null;
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String beginPeriod = null;
        String endDate = null;
        String endTime = null;
        String endPeriod = null;
        boolean printFlag = false;
        boolean hostFlag = false;
        boolean portFlag = false;

        Project4 p = new Project4();
        for (String arg : args) {
            if (hostFlag) {
                hostName = arg;
                hostFlag = false;
                continue;
            }
            if (portFlag) {
                portString = arg;
                portFlag = false;
                continue;
            }
            if (arg.startsWith("-")) {
                if (arg.equals("-README")) {
                    p.readMe();
                    System.exit(0);
                }
                if (arg.equals("-print")) {
                    printFlag = true;
                    continue;
                } else if (arg.equals("-host")) {
                    hostFlag = true;
                    continue;
                } else if (arg.equals("-port")) {
                    portFlag = true;
                    continue;
                }
                if (owner == null) {
                    owner = arg;
                } else if (description == null) {
                    description = arg;
                } else if (beginDate == null) {
                    beginDate = validateDate(arg);
                } else if (beginTime == null) {
                    beginTime = validateTime(arg);
                } else if (beginPeriod == null) {
                    beginPeriod = validatePeriod(arg);
                } else if (endDate == null) {
                    endDate = validateDate(arg);
                } else if (endTime == null) {
                    endTime = validateTime(arg);
                } else if (endPeriod == null) {
                    endPeriod = validatePeriod(arg);
                } else {
                    System.err.println(TOO_MANY_ARGUMENTS);
                    System.err.println(USAGE_MESSAGE);
                    System.exit(1);
                }
            }
        }

        if (hostName == null) {
            usage( MISSING_ARGS );
            return;

        } else if ( portString == null) {
            usage( "Missing port" );
            return;
        } else if(owner == null){
            System.err.println(MISSING_COMMAND_LINE_ARGUMENTS);
            System.err.println(USAGE_MESSAGE);
            System.exit(1);
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        try {
            if (owner == null) {
                usage("Missing owner");

            } else if (description == null) {
                // Get the text of the appointment book
                AppointmentBook book = client.getAppointments(owner);
                PrettyPrinter pretty = new PrettyPrinter(new OutputStreamWriter(System.out));
                pretty.dump(book);

            } else {
                // Create a new appointment
                client.createAppointment(owner, description);
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex);
            System.exit(1);
            return;
        }

        System.exit(0);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }
}