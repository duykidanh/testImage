package com.huynhnguyenanhduy.multithreadex3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huynhnguyenanhduy.multithreadex3.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int percent, randNumb;
    Random random = new Random();

    Handler handler = new Handler();
    //Main UI
    Runnable foregroundThread = new Runnable() {
        @Override
        public void run() {
            binding.txtPercent.setText("" + percent + "%");
            binding.progressBar.setProgress(percent);

            LinearLayout linearLayout = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(paramsLinear);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(1.0F);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 200, 0.5F);
            params.setMargins(15, 20, 15, 20);
            TextView textView = new TextView(MainActivity.this);
            TextView clone = new TextView(MainActivity.this);
            textView.setLayoutParams(params);
            clone.setLayoutParams(params);
            textView.setText(String.valueOf(randNumb));
            textView.setTextSize(26);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);

            if (randNumb % 2 == 0){
                textView.setBackgroundColor(Color.MAGENTA);
                linearLayout.addView(textView);
                linearLayout.addView(clone);
            }else {
                textView.setBackgroundColor(Color.GREEN);
                linearLayout.addView(clone);
                linearLayout.addView(textView);
            }

            binding.containerLayout.addView(linearLayout);

            if (percent == 100) {
                binding.txtPercent.setText("FINISH");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();

    }

    private void addEvents() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execLongRunningTask();
            }
        });
    }

    private void execLongRunningTask() {
        binding.containerLayout.removeAllViews();
        int numOfViews = Integer.parseInt(binding.editText.getText().toString());
        // Worker/Background Thread
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= numOfViews; ++i) {
                    percent = i*100/numOfViews;
                    randNumb = random.nextInt(100);
                    handler.post(foregroundThread);
                    SystemClock.sleep(100);
                }
            }
        });
        backgroundThread.start();
    }
}