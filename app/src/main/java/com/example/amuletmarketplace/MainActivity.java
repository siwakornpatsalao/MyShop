package com.example.amuletmarketplace;

import static javax.crypto.Cipher.PRIVATE_KEY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amuletmarketplace.Database.AppDatabase;
import com.example.amuletmarketplace.A;
import com.example.amuletmarketplace.Database.Product;
import com.example.amuletmarketplace.Database.UserDao;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;


import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*
        Web3j web3 = Web3j.build(new HttpService("https://goerli.infura.io/v3/53fa151696b14216ba7b13c493e3c1f3"));
        Credentials credentials = Credentials.create("0xDF3782EeC35cf02C8C8e65000Bb2c79C954869C8");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        A a = A.load("0xdeFCbcb831ceDdb47A5bfc4ea047f1185fC64d02", web3, credentials, contractGasProvider);
        //onclick
        a.retrieve().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
            @Override
            public void accept(BigInteger bigInteger) throws Exception {
                Log.i("vac", "accept: " + bigInteger);
            }
        });
        //เก็บค่าใน
        a.store(new BigInteger("123")).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                Log.i("vac", "accept: ");
            }
        });

         */



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