package com.example.amuletmarketplace.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void insertUser(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT EXISTS(SELECT * FROM user WHERE userName = :username)")
    boolean checkUser(String username);

    @Query("SELECT EXISTS(SELECT * FROM user WHERE userName = :username AND password = :password)")
    boolean checkLogin(String username,String password);
}

