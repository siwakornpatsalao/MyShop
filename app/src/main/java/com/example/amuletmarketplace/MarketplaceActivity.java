package com.example.amuletmarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amuletmarketplace.Database.AppDatabase;
import com.example.amuletmarketplace.Database.Product;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class MarketplaceActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView menu = findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });


        initRecyclerView();

        loadProductList();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

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

    public void showPopup(View v){
        PopupMenu popup =new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.addProduct:
                startActivityForResult(new Intent(MarketplaceActivity.this, AddNewProductActivity.class), 100);
                return true;
            case R.id.logout:
                startActivityForResult(new Intent(MarketplaceActivity.this, MainActivity.class), 100);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadProductList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}