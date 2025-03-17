package com.example.demopesangnv2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demopesangnv2.dto.DbHandler;
import com.example.demopesangnv2.entity.Product;

public class CreateProductActivity extends AppCompatActivity {
    private EditText etProductCode, etProductName, etPrice, etQuantity;
    private Button btSave;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_product);
        etProductCode = findViewById(R.id.etProductCode);
        etProductName = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etPrice);
        etQuantity = findViewById(R.id.etQuantity);
        btSave = findViewById(R.id.btSave);

        dbHandler = new DbHandler(this);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etProductCode.getText().toString().trim();
                String name = etProductName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String quantityStr = etQuantity.getText().toString().trim();

                if (code.isEmpty() || name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                    Toast.makeText(CreateProductActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double price = Double.parseDouble(priceStr);
                    int quantity = Integer.parseInt(quantityStr);

                    Product product = new Product(code, name, price, quantity);
                    dbHandler.insert(product);

                    Toast.makeText(CreateProductActivity.this, "Product saved successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Return to MainActivity
                } catch (NumberFormatException e) {
                    Toast.makeText(CreateProductActivity.this, "Invalid price or quantity", Toast.LENGTH_SHORT).show();
                }
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });
            }
        });
    }
}