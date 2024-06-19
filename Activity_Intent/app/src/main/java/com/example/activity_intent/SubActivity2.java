package com.example.activity_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.activity_intent.databinding.ActivitySub2Binding;

public class SubActivity2 extends AppCompatActivity {

    Intent intent;
    ActivitySub2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySub2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        addEvents();
    }

    public void getData() {
        intent = getIntent();
        Log.i("SubActivity2", "getData: " + intent.getStringExtra("numb"));
        binding.txtData2.setText(intent.getStringExtra("numb"));
    }

    public void addEvents() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(binding.txtData2.getText().toString());
                double pow = Math.pow(number, 2);
                intent.putExtra("result", pow);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}