package com.huynhnguyenanhduy.multithreadex3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huynhnguyenanhduy.multithreadex3.databinding.ActivityMain3Binding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity3 extends AppCompatActivity {

    ActivityMain3Binding binding;

    boolean flag = true;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor();
        addEvents();
    }

    public void addEvents(){
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                binding.linearLayout.removeAllViews();
                executeLongRunningTask();
            }
        });
    }

    public void executeLongRunningTask() {
        //background
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                int numOfViews = Integer.parseInt(binding.editText.getText().toString());
                Random random = new Random();

                for (int i=1; i<=numOfViews; ++i){
                    int percent, randNum;
                    randNum = random.nextInt(100);
                    percent = i*100/numOfViews;

                    binding.scrollBar.setProgress(percent);

                    //foregroundUI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int numOfChilds = binding.linearLayout.getChildCount();
                            if (numOfChilds == 0) {
                                LinearLayout newLayout = createNewView(randNum, flag);
                                binding.linearLayout.addView(newLayout);
                            }
                            else {
                                View view = binding.linearLayout.getChildAt(numOfChilds - 1);
                                if (view instanceof LinearLayout){
                                    LinearLayout childView = (LinearLayout) view;
                                    int max = flag?3:2;
                                    if(childView.getChildCount() < max) {
                                        TextView child = createNewTextView(randNum, flag, childView.getChildCount());
                                        childView.addView(child);
                                    }
                                    else {
                                        flag = !flag;
                                        LinearLayout newLayout = createNewView(randNum, flag);
                                        binding.linearLayout.addView(newLayout);
                                    }
                                }

                            }


                        }
                    });

                    try {
                        Thread.sleep(100);
                    }
                    catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (executorService != null){
            executorService.shutdown();
        }
    }

    public LinearLayout createNewView(int randNum, boolean flag) {
        LinearLayout layout = new LinearLayout(MainActivity3.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setWeightSum(4.0f);
        TextView textView = createNewTextView(randNum, flag, 0);
        layout.addView(textView);
        return layout;
    };

    public TextView createNewTextView(int randNum, boolean flag, int childCount) {
        TextView textView = new TextView(MainActivity3.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 200);
        if(flag == false) {
            params.weight = 2.0f;
            textView.setBackgroundColor(Color.GREEN);
        }
        else {

            if(childCount == 0 || childCount == 2) {
                textView.setBackgroundColor(Color.YELLOW);
                params.weight = 1.0f;
            }
            else{
                textView.setBackgroundColor(Color.RED);
                params.weight = 2.0f;
            }
        }
        textView.setLayoutParams(params);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        textView.setText("" + randNum);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}