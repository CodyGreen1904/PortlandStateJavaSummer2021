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
    }
}