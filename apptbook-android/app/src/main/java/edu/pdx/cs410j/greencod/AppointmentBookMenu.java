package edu.pdx.cs410j.greencod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppointmentBookMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_book_menu);

        Button makeAppointmentButton = findViewById(R.id.makeAppointmentButton);
        makeAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentBookMenu.this, MakeAppointment.class);
                startActivity(intent);
            }
        });
        Button viewAppointmentButton = findViewById(R.id.viewAppointments);
        viewAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentBookMenu.this, ViewAppointments.class);
                startActivity(intent);
            }
        });
        Button searchAppointmentButton = findViewById(R.id.searchAppointments);
        searchAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentBookMenu.this, SearchAppointments.class);
                startActivity(intent);
            }
        });
    }
}