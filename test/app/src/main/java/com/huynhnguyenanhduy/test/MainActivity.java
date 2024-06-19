package com.huynhnguyenanhduy.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.huynhnguyenanhduy.Adapter.CarAdapter;
import com.huynhnguyenanhduy.Database.CarDB;
import com.huynhnguyenanhduy.Model.Car;
import com.huynhnguyenanhduy.test.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    CarDB db;
    CarAdapter adapter;
    List<Car> carList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        loadData();

        String[] items = new String[]{"Name", "Price", "Area", "Type"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.dropdownMenu.setAdapter(adapter);
        addEvents();
    }

    public void loadData(){
        db = new CarDB(MainActivity.this);
        carList = new ArrayList<>();
        String sql = "SELECT * FROM " + db.TBL_NAME;
        Cursor cursor = db.getData(sql);
        while (cursor.moveToNext()){
            Car car = new Car(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(5));
            carList.add(car);
        }
        cursor.close();

        adapter = new CarAdapter(MainActivity.this, carList);
        binding.listviewDB.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.listviewDB.setAdapter(adapter);
    }

    public void addEvents(){
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = binding.dropdownMenu.getSelectedItem().toString();
                List<Car> searchList = new ArrayList<>();
                String key = binding.txtSearch.getText().toString();
                for (Car i:carList) {
                    switch (choice){
                        case "Name":
                            if(i.getTenHangXe().contains(key)){
                                searchList.add(i);
                            }
                            break;
                        case "Price":
                            if(i.getGiaThue() == Double.parseDouble(key)){
                                searchList.add(i);
                            }
                            break;
                        case "Area":
                            if(i.getDiaDiem().contains(key)){
                                searchList.add(i);
                            }
                            break;
                        case "Type":
                            if(i.getLoaiXe().contains(key)){
                                searchList.add(i);
                            }
                            break;
                    }

                }
                adapter.setCarList(searchList);
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCarList(carList);
                binding.txtSearch.setText("");
            }
        });
    }

//    @Override
//    protected void onStart() {
//        loadData();
//        super.onStart();
//    }

//    @Override
//    protected void onRestart() {
//        loadData();
//        super.onRestart();
//    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }
}