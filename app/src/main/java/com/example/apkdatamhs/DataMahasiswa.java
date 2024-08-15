package com.example.apkdatamhs;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DataMahasiswa extends AppCompatActivity {

    ActionBar actionBar;
    private ListView listView;
    private TextView tvListView;
    private FloatingActionButton fab;
    private EditText etSearch;
    private DatabaseHelper dbHelper;
    private List<InfoMahasiswa> dataList;
    private ArrayAdapter<String> adapter;
    private List<String> nimList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa);

        fab = findViewById(R.id.fab);
        listView = findViewById(R.id.listView);
        etSearch = findViewById(R.id.etSearch);
        tvListView = findViewById(R.id.tvListView);
        dbHelper = new DatabaseHelper(this);
        dataList = new ArrayList<>();
        nimList = new ArrayList<>();

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataMahasiswa.this, Mahasiswa.class);
                startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int
                    i1, int i2) {
                searchData(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DataMahasiswa.this);
                builder.setTitle("Pilihan");

                builder.setItems(new CharSequence[]{"Lihat Data","Update Data", "Hapus Data"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(DataMahasiswa.this, Mahasiswa.class);
                                intent.putExtra("view_data_id", dataList.get(position).getId());
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent2 = new Intent(DataMahasiswa.this, Mahasiswa.class);
                                intent2.putExtra("data_id", dataList.get(position).getId());
                                startActivity(intent2);
                                break;
                            case 2:
                                new AlertDialog.Builder(DataMahasiswa.this)
                                        .setTitle("Konfirmasi")
                                        .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dbHelper.deleteData(dataList.get(position));
                                                loadData();
                                            }
                                        })
                                        .setNegativeButton("Tidak", null)
                                        .show();
                                break;
                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DataMahasiswa.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        dataList = dbHelper.getAllData();
        nimList.clear();
        for (InfoMahasiswa infoMahasiswa : dataList) {
            nimList.add(infoMahasiswa.getNIM()+ "\n" + infoMahasiswa.getNAMA());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, nimList);
        listView.setAdapter(adapter);
        if (adapter.getCount() > 0) {
            tvListView.setText("List Data Mahasiswa");
        } else {
            tvListView.setText("No Data Mahasiswa");
        }
    }

    private void searchData(String keyword) {
        dataList = dbHelper.searchData(keyword);
        nimList.clear();
        for (InfoMahasiswa infoMahasiswa : dataList) {
            nimList.add(infoMahasiswa.getNIM()+ "\n" + infoMahasiswa.getNAMA());
        }
        adapter.notifyDataSetChanged();
    }
}