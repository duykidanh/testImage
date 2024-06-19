package com.example.activity_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.Model.Product;
import com.example.activity_intent.databinding.ActivitySub1Binding;

public class SubActivity1 extends AppCompatActivity {

    ActivitySub1Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySub1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
    }

    public void getData() {
        Intent intent = getIntent();

//        int number = intent.getIntExtra("number", 0);
//        float grades = intent.getFloatExtra("grades", 0.0f);
//        boolean checked = intent.getBooleanExtra("checked", false);
//        String say = intent.getStringExtra("say");

        Bundle bundle = intent.getBundleExtra("data");
        assert bundle != null;
        int number = bundle.getInt("number", 0);
        float grades = bundle.getFloat("grades", 0.0f);
        boolean checked = bundle.getBoolean("checked", false);

        Product product = (Product) bundle.getSerializable("productInfo");
        String say = bundle.getString("say");

        binding.txtData.setText("");
        binding.txtData.append("Number: " + number + "\n");
        binding.txtData.append("Grades: " + grades + "\n");
        binding.txtData.append("Checked: " + checked + "\n");
        binding.txtData.append("Say: " + say + "\n");

        assert product != null;
        binding.txtData.append("Code: " + product.getProductCode() + "\n");
        binding.txtData.append("Name: " + product.getProductName() + "\n");
        binding.txtData.append("Price: " + product.getProductPrice() + "$");
    }
}