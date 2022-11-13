package com.example.amuletmarketplace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amuletmarketplace.Database.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private Context context;
    private List<Product> productList;
    public ProductListAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.recycler, parent, false);

       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.MyViewHolder holder, int position) {
        Product product = productList.get(position);

        Bitmap bitmap = BitmapFactory.decodeByteArray(this.productList.get(position).image , 0, this.productList.get(position).image.length);
        holder.image.setImageBitmap(bitmap);
        holder.productName.setText(this.productList.get(position).productName);
        holder.price.setText(String.valueOf(this.productList.get(position).price));
        holder.amount.setText(String.valueOf(this.productList.get(position).amount));
        //holder.tvLastName.setText(this.userList.get(position).lastName);

        byte[] image2 = this.productList.get(position).image;
        String productName2 = product.productName;
        int price2 = product.price;
        int amount2 = product.amount;
        String detail2 = product.description;
        int position2 = productList.get(position).pid;

        holder.image.setOnClickListener((view) -> {
            Intent intent = new Intent(context, DetailActivity.class);
            //intent.putExtra("product",product);
            System.out.println(position);
            intent.putExtra("position",position2);
            intent.putExtra("image",image2);
            intent.putExtra("productName",productName2);
            intent.putExtra("price",price2);
            intent.putExtra("detail",detail2);
            intent.putExtra("amount",amount2);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return  this.productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView productName;
        TextView price;
        TextView amount;

        public MyViewHolder(View view) {
            super(view);
             image = view.findViewById(R.id.viewImageProduct);
             productName = view.findViewById(R.id.productName);
             price = view.findViewById(R.id.price);
             amount = view.findViewById(R.id.amount);
        }
    }
}
