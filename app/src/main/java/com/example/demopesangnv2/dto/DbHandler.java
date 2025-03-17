package com.example.demopesangnv2.dto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demopesangnv2.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "ProductDatabase";
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS products (" +
            "code TEXT PRIMARY KEY," +
            "name TEXT," +
            "price REAL," +
            "quantity INTEGER" +
            ")";
    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS products");
            onCreate(sqLiteDatabase);
        }

    }
    public void insert(Product product) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if(sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("code", product.getCode());
            values.put("name", product.getName());
            values.put("price", product.getPrice());
            values.put("quantity", product.getQuantity());
            sqLiteDatabase.insert("products", null, values);
        }


    }
    public void delete(String code) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("products", "code = ?", new String[]{code});
        sqLiteDatabase.close();
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {


            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM products", null);
            while(cursor.moveToNext()) {

                Product product = new Product();
                int index = cursor.getColumnIndex("code");
                product.setCode(cursor.getString(index));
                index = cursor.getColumnIndex("name");
                product.setName(cursor.getString(index));
                index = cursor.getColumnIndex("price");
                product.setPrice(cursor.getDouble(index));
                index = cursor.getColumnIndex("quantity");
                product.setQuantity(cursor.getInt(index));
                products.add(product);
            }
            cursor.close();



        }
        return products;
    }
    public void insertSampleProducts() {

        insert(new Product("P001", "Laptop", 1000.0, 10));
        insert(new Product("P002", "Smartphone", 500.0, 20));
        insert(new Product("P003", "Tablet", 300.0, 15));
        insert(new Product("P004", "Headphones", 100.0, 30));
        insert(new Product("P005", "Mouse", 50.0, 40));
    }
}

