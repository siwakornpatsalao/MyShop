package com.example.amuletmarketplace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import com.example.amuletmarketplace.Database.AppDatabase;
import com.example.amuletmarketplace.Database.Product;
import com.example.amuletmarketplace.Database.ProductDao;

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

public class DetailActivity extends AppCompatActivity {

    ProductDao productDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        Web3j web3 = Web3j.build(new HttpService("https://goerli.infura.io/v3/53fa151696b14216ba7b13c493e3c1f3"));
        Credentials credentials = Credentials.create("d0571789b6ea1136f1917c5022f8465523cf4feacd3aa29630b714568ddb4460");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        A a = A.load("0x3aaB46EeD5c2c7252679dDC9c618a979f785EC4F", web3, credentials, contractGasProvider);

        ImageView imageProduct = findViewById(R.id.imageProductm);
        TextView productName = findViewById(R.id.viewProductName);
        TextView productPrice = findViewById(R.id.viewPrice);
        TextView productAmount = findViewById(R.id.viewAmount);
        TextView productDetail = findViewById(R.id.viewDescription);
        TextView alert = findViewById(R.id.alert3);
        alert.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();

        Intent intent = getIntent();

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        int amountN = intent.getIntExtra("amount",0);
        int priceN = intent.getIntExtra("price",0);
        int position = intent.getIntExtra("position",0);

        Product product;
        AppDatabase database = AppDatabase.getDbInstance(getApplicationContext());
        List<Product> productList = database.productDao().getProductByID(position);
        if (productList.isEmpty()){
            product = null;
        }else{
            product = productList.get(0);
        }

        imageProduct.setImageBitmap(bmp);
        productName.setText(bundle.getString("productName"));
        productPrice.setText(String.valueOf(priceN));
        productAmount.setText(String.valueOf(amountN));
        productDetail.setText(bundle.getString("detail"));

        EditText amount = findViewById(R.id.editAmount);


        Button buy = findViewById(R.id.buyButton);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.getText().toString().isEmpty()){
                    alert.setText("ระบุจำนวนสินค้าที่ต้องการ");
                    alert.setVisibility(View.VISIBLE);
                    amount.getText().clear();
                }else if(!amount.getText().toString().matches("\\d+\\.\\d+|\\d+")){
                    alert.setText("จำนวนสินค้าควรเป็นจำนวนบวก");
                    alert.setVisibility(View.VISIBLE);
                    amount.getText().clear();
                }else if(Integer.parseInt(amount.getText().toString())>Integer.parseInt(productAmount.getText().toString())){
                    alert.setText("จำนวนสินค้าไม่เพียงพอ");
                    alert.setVisibility(View.VISIBLE);
                    amount.getText().clear();
                }else{
                    a.store(new BigInteger(amount.getText().toString())).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
                        @Override
                        public void accept(TransactionReceipt transactionReceipt) throws Exception {
                            Log.i("vac", "accept: ");
                        }
                    });

                    /*
                    a.retrieve().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
                        @Override
                        public void accept(BigInteger bigInteger) throws Exception {
                            //TextView textView = findViewById(R.id.text22);
                            //textView.setText(String.valueOf(bigInteger));
                            Log.i("vac", "accept: " + bigInteger);
                        }
                    });

                     */
                    String temp = amount.getText().toString();
                    int amount2 = Integer.parseInt(temp);
                    int newAmount = amountN - amount2;
                    //int sum = amount2*priceN;
                    assert product != null;
                    product.setAmount(newAmount);
                    System.out.println(product.getAmount());
                    database.productDao().update(product);
                    startActivityForResult(new Intent(DetailActivity.this, MarketplaceActivity.class), 100);
                }
            }
        });

        Button market = findViewById(R.id.backToMarketButton);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DetailActivity.this, MarketplaceActivity.class), 100);
            }
        });

    }
}
