package edu.pdx.cs410j.greencod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;

public class DisplayAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_appointments);
        Bundle b = getIntent().getExtras();
        String owner = null;
        if(b != null) {
            owner = b.getString("owner");
        }
        AppointmentBook appointmentBook = null;
        String fileName = owner.replace(" ", "_");
        File textFile = new File(this.getFilesDir(), fileName);
        try{
            TextParser parser = new TextParser((new FileReader(textFile)));
            appointmentBook = parser.parse();
            if(appointmentBook == null){
                Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        } catch(FileNotFoundException f){
            Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        TextView textView = (TextView) findViewById(R.id.displayAppointmentText);
        textView.setMovementMethod(new ScrollingMovementMethod());


        textView.setText(prettyPrint(appointmentBook));

        Button submit = findViewById(R.id.backToHomeDisplay);
        submit.setOnClickListener(v -> finish());

    }
    public static StringBuilder prettyPrint(AppointmentBook b) {
        if(b.getOwnerName() == null){
            System.out.println(MakeAppointment.NO_OWNER_PROVIDED);
            return new StringBuilder(MakeAppointment.NO_OWNER_PROVIDED);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("****This Appointment Book belongs to " + b.getOwnerName() + ", The Coolest Cat in the Cave****\n\n\n");
        if(b.getAppointments() != null) {
            Appointment[] appointments = b.getAppointments().toArray(new Appointment[0]);
            int count = 1;
            for (Appointment appointment : appointments) {
                stringBuilder.append("Appointment number: " + count + "\n");
                stringBuilder.append("The appointment, which is: " + appointment.getDescription() + "\n");
                stringBuilder.append("will begin at precisely " + DateFormat.getDateInstance(DateFormat.LONG).format(appointment.getBeginTime()));
                stringBuilder.append(", and will go until exactly " + DateFormat.getDateInstance(DateFormat.LONG).format(appointment.getEndTime()) + "\n\n");
                ++count;
            };
        } else {
            stringBuilder.append("Nothing to report for today, you get out there and make a difference!!!\n\n");
        }
        stringBuilder.append("\"\"\"You miss 100% of the shots you don't take\"\n\t-Wayne Gretzky\"\n\t\t-Michael Scott\"\n\t\t\t-Cody Green");
        return stringBuilder;
    }
}