package com.example.apkdatamhs;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Mahasiswa extends AppCompatActivity {

    ActionBar actionBar;

    private RadioGroup etGender;
    RadioButton rbMale, rbFemale;
    private EditText etNomor, etName, etDate, etAddress;
    private Button btnSave;

    private TextView tvTitleScreen;

    private DatabaseHelper dbHelper;
    private InfoMahasiswa infoMahasiswa;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        tvTitleScreen = findViewById(R.id.tvTitleScreen);
        etNomor = findViewById(R.id.etNomor);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etGender = findViewById(R.id.etGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        etDate.setOnClickListener(v -> showDatePickerDialog());

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("view_data_id")) {
            tvTitleScreen.setText("Lihat Data");
            btnSave.setVisibility(View.GONE);
            int id = getIntent().getIntExtra("view_data_id", -1);
            infoMahasiswa = dbHelper.getData(id);
            if (infoMahasiswa != null) {
                etNomor.setText(infoMahasiswa.getNIM());
                etName.setText(infoMahasiswa.getNAMA());
                etDate.setText(infoMahasiswa.getDOB());
                setGenderSelection(infoMahasiswa.getGENDER());
                etAddress.setText(infoMahasiswa.getADDRESS());

                etNomor.setEnabled(false);
                etName.setEnabled(false);
                etDate.setEnabled(false);
                etGender.setEnabled(false);
                rbMale.setEnabled(false);
                rbFemale.setEnabled(false);
                etAddress.setEnabled(false);
            }
        }

        if (getIntent().hasExtra("data_id")) {
            tvTitleScreen.setText("Edit Data");
            btnSave.setText("Edit");
            int id = getIntent().getIntExtra("data_id", -1);
            infoMahasiswa = dbHelper.getData(id);
            if (infoMahasiswa != null) {
                etNomor.setText(infoMahasiswa.getNIM());
                etName.setText(infoMahasiswa.getNAMA());
                etDate.setText(infoMahasiswa.getDOB());
                setGenderSelection(infoMahasiswa.getGENDER());
                etAddress.setText(infoMahasiswa.getADDRESS());
                isEdit = true;
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nim = etNomor.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String gender = getSelectedGender();
                String address = etAddress.getText().toString().trim();
                if (nim.isEmpty() || nim.isEmpty()) {
                    Toast.makeText(Mahasiswa.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEdit) {
                    infoMahasiswa.setNIM(nim);
                    infoMahasiswa.setNAMA(name);
                    infoMahasiswa.setDOB(date);
                    infoMahasiswa.setGENDER(gender);
                    infoMahasiswa.setADDRESS(address);

                    int result = dbHelper.updateNote(infoMahasiswa);

                    if (result == 0) {
                        Toast.makeText(Mahasiswa.this, "Update failed. No record found with the specified ID.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mahasiswa.this, "Data successfully updated.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Mahasiswa.this, DataMahasiswa.class);
                        startActivity(intent);
                    }
                } else {
                    infoMahasiswa = new InfoMahasiswa();
                    infoMahasiswa.setNIM(nim);
                    infoMahasiswa.setNAMA(name);
                    infoMahasiswa.setDOB(date);
                    infoMahasiswa.setGENDER(gender);
                    infoMahasiswa.setADDRESS(address);

                    long result = dbHelper.addData(infoMahasiswa);

                    if (result == -1) {
                        Toast.makeText(Mahasiswa.this, "NIM already exists. Data not inserted.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Mahasiswa.this, "Data successfully inserted.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Mahasiswa.this, DataMahasiswa.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Mahasiswa.this, DataMahasiswa.class);
        startActivity(intent);
        finish();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Mahasiswa.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear);
                    etDate.setText(selectedDate);
                },
                day, month, year
        );

        datePickerDialog.show();
    }

    private String getSelectedGender() {
        int selectedId = etGender.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }

    private void setGenderSelection(String gender) {
        if (gender.equals("Laki-laki")) {
            etGender.check(R.id.rbMale);
        } else if (gender.equals("Perempuan")) {
            etGender.check(R.id.rbFemale);
        }
    }
}