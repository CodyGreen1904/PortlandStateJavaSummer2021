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

public class ViewAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        Button submit = findViewById(R.id.submitViewAppointment);
        submit.setOnClickListener(v -> verifyOwnerExists());
    }

    private void verifyOwnerExists() {
        EditText ownerEdit = findViewById(R.id.ownerSearchInput);
        String owner = ownerEdit.getText().toString();

        AppointmentBook appointmentBook = null;
        String fileName = owner.replace(" ", "_");
        File textFile = new File(this.getFilesDir(), fileName);
        if(textFile.exists()) {
            try{
                TextParser parser = new TextParser((new FileReader(textFile)));
                appointmentBook = parser.parse();
                if(appointmentBook == null){
                    Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }else if (!appointmentBook.getOwnerName().equals(owner)) {
                    Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
            } catch(FileNotFoundException f){
                Toast toast = Toast.makeText(this, "Error Parsing File, new AppointmentBook created", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "No FileOwner of that name", Toast.LENGTH_LONG);
            toast.show();
        }
        Intent intent = new Intent(ViewAppointments.this, DisplayAppointments.class);
        Bundle b = new Bundle();
        b.putString("owner", owner);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}