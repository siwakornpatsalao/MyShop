package com.example.amuletmarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amuletmarketplace.Database.AppDatabase;
import com.example.amuletmarketplace.Database.Product;

import java.util.List;

public class MarketplaceActivity extends AppCompatActivity {
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MarketplaceActivity.this, AddNewProductActivity.class), 100);
            }
        });

        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MarketplaceActivity.this, MainActivity.class), 100);
            }
        });

        initRecyclerView();

        loadProductList();

        /*Button detail = findViewById(R.id.toDetailButton);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MarketplaceActivity.this, DetailActivity.class), 100);
            }
        });
         */
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        productListAdapter = new ProductListAdapter(this);
        recyclerView.setAdapter(productListAdapter);
    }

    private void loadProductList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Product> productList =db.productDao().getAllProducts();
        productListAdapter.setProductList(productList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadProductList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}