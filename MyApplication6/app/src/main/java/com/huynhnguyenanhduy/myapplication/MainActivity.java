package com.huynhnguyenanhduy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huynhnguyenanhduy.Database.ProductDB;
import com.huynhnguyenanhduy.Model.Product;
import com.huynhnguyenanhduy.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProductDB db;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    public void loadData(){
        db = new ProductDB(MainActivity.this);
        productList = new ArrayList<>();
        ArrayList<String> products = new ArrayList<>();
        Cursor cursor = db.GetData("SELECT * FROM " + db.TB_NAME);
        while (cursor.moveToNext()){
            Product product = new Product(cursor.getString(0), cursor.getString(1), cursor.getDouble(2));
            productList.add(product);
            products.add(product.toString());
        }

        ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, products);
        binding.listView.setAdapter(adapter);
    }

    public void addEvents(){
        binding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_item);

                Button cf_button = (Button) dialog.findViewById(R.id.button_confirm);
                Button ex_button = (Button) dialog.findViewById(R.id.button_exit);
                Button bk_button = (Button) dialog.findViewById(R.id.button_back);

                EditText txtMaSP = (EditText) dialog.findViewById(R.id.txtID);
                EditText txtTenSP = (EditText) dialog.findViewById(R.id.txtName);
                EditText txtGiaSP = (EditText) dialog.findViewById(R.id.txtPrice);

                cf_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maSP = txtMaSP.getText().toString();
                        String tenSP = txtTenSP.getText().toString();
                        double giaSP = Double.parseDouble(txtGiaSP.getText().toString());

                        try {
                            db.insertData(maSP, tenSP, giaSP);
                            Toast.makeText(MainActivity.this, "Insert Complete", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            onResume();
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                        CreateData();
                    }
                });

                bk_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ex_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
                return true;
            }
        });

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_item);

                Button cf_button = (Button) dialog.findViewById(R.id.button_confirm);
                Button ex_button = (Button) dialog.findViewById(R.id.button_exit);
                Button bk_button = (Button) dialog.findViewById(R.id.button_back);

                EditText txtMaSP = (EditText) dialog.findViewById(R.id.txtID);
                EditText txtTenSP = (EditText) dialog.findViewById(R.id.txtName);
                EditText txtGiaSP = (EditText) dialog.findViewById(R.id.txtPrice);

                cf_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maSP = txtMaSP.getText().toString();
                        String tenSP = txtTenSP.getText().toString();
                        double giaSP = Double.parseDouble(txtGiaSP.getText().toString());

                        try {
                            db.insertData(maSP, tenSP, giaSP);
                            Toast.makeText(MainActivity.this, "Insert Complete", Toast.LENGTH_SHORT).show();
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                        CreateData();
                    }
                });

                bk_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ex_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
            }
        });
    }

    public void CreateData() {

    }
    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }
}