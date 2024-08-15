package com.example.apkdatamhs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
    Button btnViewData, btnInputData, btnInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnViewData = findViewById(R.id.btnViewData);
        btnInputData = findViewById(R.id.btnInputData);
        btnInfo = findViewById(R.id.btnInfo);

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, DataMahasiswa.class);
                startActivity(intent);
            }
        });

        btnInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Mahasiswa.class);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, aboutMe.class);
                startActivity(intent);
            }
        });
    }
}
