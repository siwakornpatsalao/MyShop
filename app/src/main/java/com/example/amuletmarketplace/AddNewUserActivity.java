package com.example.amuletmarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amuletmarketplace.Database.AppDatabase;
import com.example.amuletmarketplace.Database.User;

public class AddNewUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());

        final EditText nameInput =  findViewById(R.id.registerName);
        final EditText usernameInput =  findViewById(R.id.registerUsername);
        final EditText passwordInput = findViewById(R.id.passwordRegister);
        final EditText verify = findViewById(R.id.verifyPassword);
        final TextView alert = findViewById(R.id.alert);

        alert.setVisibility(View.INVISIBLE);

        Button registerButton =  findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameInput.getText().toString().isEmpty() || usernameInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()){
                    alert.setText("ข้อมูลไม่ครบ");
                    alert.setVisibility(View.VISIBLE);
                    nameInput.getText().clear();
                    usernameInput.getText().clear();
                    passwordInput.getText().clear();
                    verify.getText().clear();
                }else if(!passwordInput.getText().toString().equals(verify.getText().toString())){
                    alert.setText("ยืนยันรหัสผ่านไม่ถูกต้อง");
                    alert.setVisibility(View.VISIBLE);
                    nameInput.getText().clear();
                    usernameInput.getText().clear();
                    passwordInput.getText().clear();
                    verify.getText().clear();
                }else {
                    if(db.userDao().checkUser(usernameInput.getText().toString())){
                        alert.setText("มี username นี้ในระบบแล้ว");
                        alert.setVisibility(View.VISIBLE);
                        nameInput.getText().clear();
                        usernameInput.getText().clear();
                        passwordInput.getText().clear();
                    }else{
                        saveNewUser(nameInput.getText().toString(), usernameInput.getText().toString()
                                , Integer.parseInt(passwordInput.getText().toString()));
                        startActivityForResult(new Intent(AddNewUserActivity.this, MainActivity.class), 100);
                    }
                }
            }
        });

        Button login = findViewById(R.id.backToLoginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddNewUserActivity.this, MainActivity.class), 100);
            }
        });
    }

    private void saveNewUser(String name, String username, int password) {
        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());

        User user = new User();
        user.name = name;
        user.userName = username;
        user.password = password;
        db.userDao().insertUser(user);

        finish();

    }
}
