package com.example.amuletmarketplace.Database;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Product")
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "Image")
    public byte[] image;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "Description")
    public String description;

    public byte[] getImage(){return image;}

    public long getId() {
        return pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
