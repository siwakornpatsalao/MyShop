package com.example.amuletmarketplace.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "userName")
    public String userName;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "password")
    public int password;

    public long getId() {
        return uid;
    }
}