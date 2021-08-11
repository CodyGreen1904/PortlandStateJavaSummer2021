package edu.pdx.cs410j.greencod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MakeAppointment extends AppCompatActivity {

    public static final String MISSING_BEGIN_DATE = "Missing begin date";
    public static final String MISSING_BEGIN_TIME = "Missing begin time";
    public static final String MISSING_END_DATE = "Missing end date";
    public static final String MISSING_END_TIME = "Missing end time";
    public static final String TIME_NOT_CORRECT = "Incorrect Time: please use 12 hour time in format HH:MM with either am or pm";
    public static final String DATE_NOT_CORRECT = "Incorrect date: Please use mm/dd/yyyy";
    public static final String TOO_MANY_ARGUMENTS = "Too many command line arguments";
    public static final String UNKNOWN_COMMAND_LINE_ARGUMENT = "Unknown command line argument";
    public static final String NO_OWNER_PROVIDED = "Somehow the program attempted to write an AppointmentBook with no owner to a file, file not created";
    public static final String MISSING_BEGIN_PERIOD = "Missing begin time period";
    public static final String MISSING_END_PERIOD = "Missing end time period";
    public static final String BEGIN_AFTER_END = "The appointment's start time must be before the appointment's end time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);



        Button submit = findViewById(R.id.submitMakeAppointment);
        submit.setOnClickListener(v -> verifyValuesAndMakeAppointment());
    }

    private void verifyValuesAndMakeAppointment() {

        EditText ownerEdit = findViewById(R.id.ownerInput);
        String owner = ownerEdit.getText().toString();

        EditText descriptionEdit = findViewById(R.id.descriptionInput);
        String description = descriptionEdit.getText().toString();

        EditText startEdit = findViewById(R.id.editStartDate);
        EditText endEdit = findViewById(R.id.editEndDate);

        String startS = startEdit.getText().toString();
        String endS = endEdit.getText().toString();
        String[] startString = startS.split(" ");
        if(!validateDate(startString[0])) {
            Toast toast = Toast.makeText(this, DATE_NOT_CORRECT + " " + startS, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!validateTime(startString[1])) {
            Toast toast = Toast.makeText(this, TIME_NOT_CORRECT + "for Start (check time)", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!validatePeriod(startString[2])) {
            Toast toast = Toast.makeText(this, TIME_NOT_CORRECT + "for Start (check period)", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        String[] endString = endS.toString().split(" ");
        if(!validateDate(endString[0])) {
            Toast toast = Toast.makeText(this, DATE_NOT_CORRECT + "for End", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!validateTime(endString[1])) {
            Toast toast = Toast.makeText(this, TIME_NOT_CORRECT + "for End (check time)", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!validatePeriod(endString[2])) {
            Toast toast = Toast.makeText(this, TIME_NOT_CORRECT + "for End (check period)", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Date start = sDateFormatter(startS.toString());
        Date end = sDateFormatter(endS);

        String deetz[] = new String[] {startString[0], startString[1], startString[2], endString[0], endString[1], endString[2]};

        Appointment appointmentToAdd = new Appointment(owner, description, start, end, deetz);
        AppointmentBook appointmentBook = null;
        String fileName = owner.replace(" ", "_");
        File textFile = new File(this.getFilesDir(), fileName);
        boolean badFileFlag = false;
        if(textFile.exists()) {
            try{
                TextParser parser = new TextParser((new FileReader(textFile)));
                appointmentBook = parser.parse();
                if(appointmentBook == null){
                    Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                    toast.show();
                    badFileFlag = true;
                }else if (!appointmentBook.getOwnerName().equals(owner)) {
                    Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                    toast.show();
                    badFileFlag = true;
                }
            } catch(FileNotFoundException f){
                Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                toast.show();
                badFileFlag = true;
            }
        } else {
            appointmentBook = new AppointmentBook(owner);
        }
        if(badFileFlag && textFile.exists()){
            appointmentBook = new AppointmentBook(owner);
        }
        appointmentBook.addAppointment(appointmentToAdd);
        try {
            TextDumper dumper = new TextDumper(new FileWriter(textFile));
            dumper.dump(appointmentBook);
        } catch (IOException e){
            Toast toast = Toast.makeText(this, "Error Dumping File, no appointment created", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        String fin = "Made Appointment: "
                + appointmentToAdd.getDescription() + " from "
                + appointmentToAdd.getBeginTimeString() + " to " + appointmentToAdd.getEndTimeString();
        Toast toast = Toast.makeText(this, fin, Toast.LENGTH_LONG);
        toast.show();
        finish();
    }
    public static Date sDateFormatter(String old){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            return simpleDateFormat.parse(old);
        } catch (ParseException e) {
            return null;
        }
    }
    public static boolean validatePeriod(String old) {
        if(old.equalsIgnoreCase("am")  || old.equalsIgnoreCase("pm")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean validateDate(String old){
        String[] ar = old.split("/");
        if(ar.length != 3){
            return false;
        }
        if(ar[0].length() > 2 || ar[1].length() > 2 || ar[2].length() != 4) {
            return false;
        }

        int month = 0;
        int day = 0;
        int year = 0;

        if(allNumsDate(ar)){
            month = Integer.parseInt(ar[0]);
            day = Integer.parseInt(ar[1]);
            year = Integer.parseInt(ar[2]);
        } else {
            return false;
        }

        boolean leap = false;

        if(month < 0 || month > 12) {
            return false;
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
                return day >= 0 && day <= 28;
            } else {
                if(day < 0 || day > 29) {
                    System.err.println(DATE_NOT_CORRECT);
                    System.exit(1);
                }
            }
        } else if((month < 8 && month % 2 == 1) || (month > 7 && month % 2 == 0)) {
            return day >= 0 && day <= 31;
        } else {
            return day >= 0 && day <= 30;
        }

        return true;
    }
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
    public static boolean allNumsTime(String[] ar) throws NumberFormatException{
        try {
            int num = Integer.parseInt(ar[0]);
            num = Integer.parseInt(ar[1]);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    public static boolean validateTime(String old) throws NumberFormatException{
        String[] ar = old.split(":");
        if(ar.length != 2) {
            return false;
        }

        int hour = 0;
        int minute = 0;
        if(allNumsTime(ar)){
            hour = Integer.parseInt(ar[0]);
            minute = Integer.parseInt(ar[1]);
        } else {
            return false;
        }
        if(hour < 0 || hour > 12 || minute < 0 || minute > 59){
            return false;
        }
        return true;
    }
    public static long getMinutes(Date d1, Date d2){
        long difference = d2.getTime() - d1.getTime();
        difference = TimeUnit.MILLISECONDS.toMinutes(difference);
        return difference;
    }
}