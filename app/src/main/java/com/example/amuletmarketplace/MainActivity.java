package com.example.amuletmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amuletmarketplace.Database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());
        db.userDao().getAllUsers();
        db.productDao().getAllProducts();

        EditText username = findViewById(R.id.userNameLogin);
        EditText password = findViewById(R.id.passwordLogin);
        TextView alert = findViewById(R.id.alertLogin);
        alert.setVisibility(View.INVISIBLE);

        Button registerLogin = findViewById(R.id.registerLogin);
        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNewUserActivity.class), 100);
            }
        });

        Button login = findViewById(R.id.LoginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    alert.setText("ข้อมูลไม่ครบ");
                    alert.setVisibility(View.VISIBLE);
                    username.getText().clear();
                    password.getText().clear();
                }else{
                    if(db.userDao().checkLogin(username.getText().toString(),password.getText().toString())){
                        startActivityForResult(new Intent(MainActivity.this, MarketplaceActivity.class), 100);
                    }else{
                        alert.setText("username หรือ password ไม่ถูกต้อง");
                        alert.setVisibility(View.VISIBLE);
                        username.getText().clear();
                        password.getText().clear();
                    }
                }
            }
        });
    }

}