package com.example.amuletmarketplace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Converter {
    @TypeConverter
    public static byte[] stringFromBitMap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    @TypeConverter
    public static Bitmap bitMapFromString(byte[] string) {
        return BitmapFactory.decodeByteArray(string, 0, string.length);
    }
}
