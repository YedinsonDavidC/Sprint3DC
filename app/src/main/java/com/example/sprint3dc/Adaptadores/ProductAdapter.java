package com.example.sprint3dc.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sprint3dc.BD.DBFirebase;
import com.example.sprint3dc.Entidades.Product;
import com.example.sprint3dc.MainActivity;
import com.example.sprint3dc.MainActivity2;
import com.example.sprint3dc.ProductForm;
import com.example.sprint3dc.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product> arrayProducts;
    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.arrayProducts = arrayProducts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template, null);

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imgProduct);

        Button btnDeleteTemplate = (Button) view.findViewById(R.id.btnDeleteTemplate);
        Button btnEditemplate = (Button) view.findViewById(R.id.btnEditTemplate);

        TextView tvNameProduct = (TextView) view.findViewById(R.id.tvNameProduct);
        TextView tvDescriptionProduct = (TextView) view.findViewById(R.id.tvDescriptionProduct);
        TextView tvPriceProduct = (TextView) view.findViewById(R.id.tvPriceProduct);

        Product product = arrayProducts.get(i);

        imgProduct.setImageResource(R.drawable.noimg);
        tvNameProduct.setText(product.getName());
        tvDescriptionProduct.setText(product.getDescription());
        tvPriceProduct.setText(String.valueOf(product.getPrice()));

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context.getApplicationContext(), MainActivity2.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image",product.getImage());
                context.startActivity(intent);
            }
        });

        btnDeleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebase dbFirebase;
                dbFirebase = new DBFirebase();
                dbFirebase.deleteData(product.getId());
                Toast.makeText(context.getApplicationContext(), "Eliminado con exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });

        btnEditemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductForm.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", product.getId());
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", String.valueOf(product.getPrice()));
                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
