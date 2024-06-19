package com.huynhnguyenanhduy.multithreadex3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huynhnguyenanhduy.multithreadex3.databinding.ActivityMain2Binding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    boolean flag = true;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor();

        addEvents();
    }

    private void addEvents() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.containerLayout.removeAllViews();
                executeLongRunningTask();
            }
        });
    }

    private void executeLongRunningTask() {
        //Background Thread
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                binding.containerLayout.removeAllViews();
                int numOfViews = Integer.parseInt(binding.editText.getText().toString());
                flag = true;
                int change = 1;
                Random random = new Random();

                for (int i = 1; i <= numOfViews; ++i) {
                    int percent, randNumb;
                    percent = i*100/numOfViews;
                    randNumb = random.nextInt(100);

                    //Update UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressBar.setProgress(percent);
                            binding.txtPercent.setText(percent + "%");

                            int numOfChild = binding.containerLayout.getChildCount();
                            if (numOfChild == 0) {
                                createNewView(randNumb, flag);
                                Log.i("Create new view", "" + flag);
                                flag = !flag;
                            }
                            else {
                                View child = binding.containerLayout.getChildAt(binding.containerLayout.getChildCount() - 1);
                                if(child instanceof LinearLayout) {
                                    LinearLayout linearLayout = (LinearLayout) child;
                                    if(linearLayout.getChildCount() < 2) {
                                        TextView textView = createNewTextView(randNumb, flag);
                                        linearLayout.addView(textView);
                                        Log.i("Create new text view", "" + flag);
                                    }
                                    else {
                                        createNewView(randNumb, flag);
                                        Log.i("Create new view", "" + flag);
                                        flag = !flag;
                                    }
                                }
                            }
                            if (percent == 100) {
                                binding.txtPercent.setText("FINISH");
                            }
                        }
                    });
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e) {
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

    private void createNewView(int randNumb, boolean flag) {
        LinearLayout linearLayout = new LinearLayout(MainActivity2.this);
        LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(paramsLinear);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(1.5f);
        TextView textView = createNewTextView(randNumb, flag);
        linearLayout.addView(textView);
        binding.containerLayout.addView(linearLayout);
    }

    private TextView createNewTextView(int randNumb, boolean flag) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
        params.setMargins(15,20, 15, 20);
        TextView textView = new TextView(MainActivity2.this);
        if (flag == true) {
            params.weight = 0.5f;
            textView.setBackgroundColor(Color.MAGENTA);
        }else {
            params.weight = 1f;
            textView.setBackgroundColor(Color.GREEN);
        }
        textView.setText(String.valueOf(randNumb));
        textView.setTextSize(26);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);

        return textView;
    }
}