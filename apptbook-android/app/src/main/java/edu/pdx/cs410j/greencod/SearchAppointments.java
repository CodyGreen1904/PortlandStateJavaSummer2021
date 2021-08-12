package edu.pdx.cs410j.greencod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import static edu.pdx.cs410j.greencod.MakeAppointment.DATE_NOT_CORRECT;
import static edu.pdx.cs410j.greencod.MakeAppointment.TIME_NOT_CORRECT;
import static edu.pdx.cs410j.greencod.MakeAppointment.sDateFormatter;
import static edu.pdx.cs410j.greencod.MakeAppointment.validateDate;
import static edu.pdx.cs410j.greencod.MakeAppointment.validatePeriod;
import static edu.pdx.cs410j.greencod.MakeAppointment.validateTime;

public class SearchAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointments);

        Button submit = findViewById(R.id.submitSearchAppointment);
        submit.setOnClickListener(v -> verifyValuesAndSearchAppointment());
    }
    private void verifyValuesAndSearchAppointment() {

        EditText ownerEdit = findViewById(R.id.ownerInputSearch);
        String owner = ownerEdit.getText().toString();


        EditText startEdit = findViewById(R.id.editStartDateSearch);
        EditText endEdit = findViewById(R.id.editEndDateSearch);

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
        Date start = sDateFormatter(startS);
        Date end = sDateFormatter(endS);

        if(start.compareTo(end) >= 0) {
            Toast toast = Toast.makeText(this, "Start time after end time", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        AppointmentBook appointmentBookReference = null;
        String fileName = owner.replace(" ", "_");
        File textFile = new File(this.getFilesDir(), fileName);
        if(textFile.exists()) {
            try{
                TextParser parser = new TextParser((new FileReader(textFile)));
                appointmentBookReference = parser.parse();
                if(appointmentBookReference == null){
                    Toast toast = Toast.makeText(this, "Error Parsing File, try again", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }else if (!appointmentBookReference.getOwnerName().equals(owner)) {
                    Toast toast = Toast.makeText(this, "Error Parsing File, try again", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
            } catch(FileNotFoundException f){
                Toast toast = Toast.makeText(this, "Error Parsing File, try again", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        } else {
            Toast toast = Toast.makeText(this, "Owner has no AppointmentBook", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Appointment[] appointmentsReference = appointmentBookReference.getAppointments().toArray(new Appointment[0]);
        AppointmentBook newBook = new AppointmentBook(owner);
        for (Appointment i : appointmentsReference) {
            if ((i.getBeginTime().after(start) && (i.getBeginTime().before(end)) || start.equals(i.getBeginTime()))) {
                newBook.addAppointment(i);
            }
        }
        textFile = new File(this.getFilesDir(), "testfile");
        try {
            TextDumper dumper = new TextDumper(new FileWriter(textFile));
            dumper.dump(newBook);
        } catch (IOException e){
            Toast toast = Toast.makeText(this, "Error Dumping File, no appointment created", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Intent intent = new Intent(SearchAppointments.this, DisplayAppointments.class);
        Bundle b = new Bundle();
        b.putString("owner", "testfile");
        intent.putExtras(b);
        startActivity(intent);

        finish();
    }
}