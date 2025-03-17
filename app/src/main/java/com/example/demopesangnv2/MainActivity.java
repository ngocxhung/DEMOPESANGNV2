package com.example.demopesangnv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demopesangnv2.adapter.ProductAdapter;
import com.example.demopesangnv2.dto.DbHandler;
import com.example.demopesangnv2.entity.Product;

import java.util.AbstractList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProductList;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private DbHandler dbHandler;
    private Button btAddNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHandler = new DbHandler(this);

        productList = dbHandler.getAllProducts();

        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        ProductAdapter productAdapter = new ProductAdapter(productList, this, dbHandler);
        recyclerViewProductList.setAdapter(productAdapter);

        btAddNewProduct = findViewById(R.id.btAddNewProduct);
        btAddNewProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(intent);
        });

    }
}