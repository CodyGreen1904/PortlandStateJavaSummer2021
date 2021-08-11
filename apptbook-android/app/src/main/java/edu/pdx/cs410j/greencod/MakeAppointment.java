package edu.pdx.cs410j.greencod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MakeAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        Button submit = findViewById(R.id.makeAppointmentButton);
        submit.setOnClickListener(v -> verifyValuesAndMakeAppointment());
    }

    private void verifyValuesAndMakeAppointment() {
        
    }
}