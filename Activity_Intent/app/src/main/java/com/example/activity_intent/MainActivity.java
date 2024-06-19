package com.example.activity_intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.Model.Product;
import com.example.activity_intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> launcher;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.i("MainActivity", "onCreate");

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                double res = result.getData().getDoubleExtra("result", 0.0f);
                binding.txtValue.setText(String.valueOf(res));
            }
        });
        addEvents();
    }

    public void addEvents() {
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        binding.btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });

        binding.btnOpenSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity1.class);
                //attach data
                //using putExtra(key, data)
//                intent.putExtra("number", 89);
//                intent.putExtra("grades", 8.9f);
//                intent.putExtra("checked", true);
//                intent.putExtra("say", "Never gonna give you up");

                //using bundle
                Bundle bundle = new Bundle();
                bundle.putInt("number", 21);
                bundle.putFloat("grades", 6.6f);
                bundle.putBoolean("checked", false);
                bundle.putString("say", "Never gonna let you down");

                Product product = new Product(1, "Coca Cola", 15000.0f);
                bundle.putSerializable("productInfo", product);

                intent.putExtra("data", bundle);

                startActivity(intent);
            }
        });

        binding.btnOpenSub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity2.class);

                intent.putExtra("numb", binding.editNumber.getText().toString());

                //startActivity(intent);
                launcher.launch(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume");
    }
}