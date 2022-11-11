package com.example.amuletmarketplace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amuletmarketplace.Database.AppDatabase;
import com.example.amuletmarketplace.Database.Product;

import org.web3j.abi.datatypes.Int;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AddNewProductActivity extends AppCompatActivity {

    Button button;
    ImageView productImage;
    Bitmap bitmap=null;
    int picture = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_product);

        productImage = findViewById(R.id.addImageProduct);
        final EditText productNameInput =  findViewById(R.id.addProductName);
        final EditText price =  findViewById(R.id.addPrice);
        final EditText description =  findViewById(R.id.addDescription);
        final EditText amount =  findViewById(R.id.addAmount);
        final TextView alert = findViewById(R.id.alert2);

        alert.setVisibility(View.INVISIBLE);

        Button saveButton =  findViewById(R.id.saveAddProduct);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productNameInput.getText().toString().isEmpty() || price.getText().toString().isEmpty() || description.getText().toString().isEmpty() || amount.getText().toString().isEmpty() || productImage==null) {
                    alert.setText("ข้อมูลไม่ครบ");
                    alert.setVisibility(View.VISIBLE);
                    productNameInput.getText().clear();
                    price.getText().clear();
                    description.getText().clear();
                    amount.getText().clear();
                }else if (!price.getText().toString().matches("\\d+\\.\\d+|\\d+")||(!amount.getText().toString().matches("\\d+\\.\\d+|\\d+"))){
                    alert.setText("ราคาหรือจำนวนควรเป็นจำนวนเต็มบวก");
                    alert.setVisibility(View.VISIBLE);
                    productNameInput.getText().clear();
                    price.getText().clear();
                    description.getText().clear();
                    amount.getText().clear();
                }
                else{
                    saveNewProduct(productNameInput.getText().toString(), Integer.parseInt(price.getText().toString()),
                            description.getText().toString(), Integer.parseInt(amount.getText().toString()),productImage.getDrawable());
                }
            }
        });

        Button market = findViewById(R.id.backProfileButton);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddNewProductActivity.this, MarketplaceActivity.class), 100);
            }
        });

        Button images = findViewById(R.id.changeImage);
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,picture);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode==picture){
                Uri selectImage = data.getData();
                if(null != selectImage){
                    productImage.setImageURI(selectImage);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectImage);
                        productImage.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void saveNewProduct(String productName, int price, String description, int amount, Drawable image) {
        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());

        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapImage = stream.toByteArray();

        Product product = new Product();
        product.productName = productName;
        product.price = price;
        product.description = description;
        product.amount = amount;
        product.image = bitMapImage;
        db.productDao().insertProduct(product);

        finish();

    }
}