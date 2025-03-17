package com.example.demopesangnv2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.demopesangnv2.R;
import com.example.demopesangnv2.dto.DbHandler;
import com.example.demopesangnv2.entity.Product;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;
    private DbHandler dbHandler;

    public ProductAdapter(List<Product> productList, Context context, DbHandler dbHandler) {
        this.productList = productList;
        this.context = context;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        Product product = productList.get(position);

        holder.tvProductCode.setText(product.getCode());
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.valueOf(product.getPrice()));
        holder.tvProductQuantity.setText(String.valueOf(product.getQuantity()));

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvProductCode;
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView tvProductQuantity;
        private TextView tvAction;
        private final ProductAdapter productAdapter;

        public ViewHolder(@NonNull View itemView, ProductAdapter productAdapter) {
            super(itemView);

            tvProductCode = itemView.findViewById(R.id.tvProductCode);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvAction = itemView.findViewById(R.id.tvAction);
            this.productAdapter = productAdapter;
            tvAction.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete??");

            builder.setPositiveButton("Yes", (dialog, which) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Product product = productList.get(position);
                    // Delete from database
                    dbHandler.delete(product.getCode());
                    // Remove from list and update UI
                    productList.remove(position);
                    productAdapter.notifyItemRemoved(position);
                }
                dialog.dismiss();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        }

    }
}

