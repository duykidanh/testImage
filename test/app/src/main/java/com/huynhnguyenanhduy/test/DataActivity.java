package com.huynhnguyenanhduy.test;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.huynhnguyenanhduy.Database.CarDB;
import com.huynhnguyenanhduy.Model.Car;
import com.huynhnguyenanhduy.test.databinding.ActivityDataBinding;

import java.io.ByteArrayOutputStream;

public class DataActivity extends AppCompatActivity {

    ActivityDataBinding binding;
    ActivityResultLauncher<Intent> launcher;
    CarDB db;
    Car car = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] items = new String[]{"4 cho", "7 cho", "16 cho"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.dropdownType.setAdapter(adapter);

        getData();

        db = new CarDB(DataActivity.this);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.imgThumbnail.setImageBitmap(bitmap);
                }
            }
        });

        addEvents();
    }

    public void addEvents(){
        binding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.txtName.getText().toString();
                String area = binding.txtArea.getText().toString();
                String type = binding.dropdownType.getSelectedItem().toString();
                Double price = Double.parseDouble(binding.txtPrice.getText().toString());
                byte[] photo = convertImage();
                //insert data
                if (car == null) {

                    boolean resultInsert = db.insertData(name, price, area, type, photo);
                    if(resultInsert){
                        Toast.makeText(DataActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(DataActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                //update data
                else {
                    String sql = "UPDATE " + db.TBL_NAME + " SET " + db.COL_NAME + " = '" + name + "', " + db.COL_PRICE + " = " + price + ", " + db.COL_AREA + " = '" + area + "', " + db.COL_TYPE + " = '" + type + "' WHERE " + db.COL_CODE + " = " + car.getMaDon();
                    boolean resultUpdate = db.execSql(sql);
                    if(resultUpdate){
                        resultUpdate = db.updateImage(photo, car.getMaDon());
                        if(resultUpdate){
                            Toast.makeText(DataActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(DataActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(DataActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public byte[] convertImage() {
        BitmapDrawable drawable = (BitmapDrawable) binding.imgThumbnail.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public void getData() {
        car = null;
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bundle = intent.getBundleExtra("data");
            if(bundle != null) {
                car = (Car) bundle.getSerializable("carinfo");
                if(car != null) {
                    if (car.getLoaiXe().contains("4")){
                        binding.dropdownType.setSelection(0);
                    }
                    if (car.getLoaiXe().contains("7")){
                        binding.dropdownType.setSelection(1);
                    }
                    if (car.getLoaiXe().contains("16")){
                        binding.dropdownType.setSelection(2);
                    }
//                    binding.txtType.setText(car.getLoaiXe());
                    binding.txtPrice.setText(String.valueOf(car.getGiaThue()));
                    binding.txtArea.setText(car.getDiaDiem());
                    binding.txtName.setText(car.getTenHangXe());
                    binding.imgThumbnail.setImageBitmap(car.getImageBitmap());
                }
            }

        }

    }
}