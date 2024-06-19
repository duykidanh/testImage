package com.huynhnguyenanhduy.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huynhnguyenanhduy.Database.CarDB;
import com.huynhnguyenanhduy.Model.Car;
import com.huynhnguyenanhduy.test.DataActivity;
import com.huynhnguyenanhduy.test.MainActivity;
import com.huynhnguyenanhduy.test.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Car> carList;

    CarDB db;
    public CarAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
        db = new CarDB(this.context);
        notifyDataSetChanged();
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new LayoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Car car = carList.get(position);

        LayoutViewHolder viewHolder = (LayoutViewHolder) holder;

        viewHolder.imgThumbnail.setImageBitmap(car.getImageBitmap());

        viewHolder.txtName.setText(car.getTenHangXe());
        viewHolder.txtArea.setText(car.getDiaDiem());
        viewHolder.txtType.setText(car.getLoaiXe());
        viewHolder.txtPrice.setText(String.valueOf(car.getGiaThue()));

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("carinfo", (Serializable) car);
                intent.putExtra("data", bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Car");
                builder.setMessage("Are you sure to delete this Car?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM " + db.TBL_NAME + " WHERE " + db.COL_CODE + " = " + car.getMaDon();
                        db.execSql(sql);
                        resetData();
                    }
                });
                builder.setNegativeButton("No", ((dialog, which) -> {
                    dialog.dismiss();
                }));

                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class LayoutViewHolder extends RecyclerView.ViewHolder{

        ImageView imgThumbnail;
        TextView txtName;
        TextView txtPrice;
        TextView txtArea;
        TextView txtType;
        ImageView btnEdit;
        ImageView btnDelete;
        public LayoutViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.imgPhoto);
            txtName = itemView.findViewById(R.id.txtName);
            txtArea = itemView.findViewById(R.id.txtArea);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtType = itemView.findViewById(R.id.txtType);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void resetData(){
        carList = new ArrayList<>();
        String sql = "SELECT * FROM " + db.TBL_NAME;
        Cursor cursor = db.getData(sql);
        while (cursor.moveToNext()){
            Car car = new Car(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(5));
            carList.add(car);
        }
        cursor.close();

        notifyDataSetChanged();
    }
}
