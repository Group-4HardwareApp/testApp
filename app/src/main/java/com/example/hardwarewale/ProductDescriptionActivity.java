package com.example.hardwarewale;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hardwarewale.api.FavoriteService;
import com.example.hardwarewale.bean.Favorite;
import com.example.hardwarewale.bean.Product;
import com.example.hardwarewale.databinding.ProductDescriptionBinding;
import com.example.hardwarewale.utility.InternetConnectivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescriptionActivity extends AppCompatActivity {
    ProductDescriptionBinding binding;
    Product product;
    InternetConnectivity connectivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProductDescriptionBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        Intent in = getIntent();
        product = (Product) in.getSerializableExtra("product");

        binding.tvProductName.setText("" + product.getName());
        binding.tvProductPrice.setText("₹ "+product.getPrice());
        binding.tvBrand.setText("" + product.getBrand());
        double discount = product.getDiscount();
        int off = (int)discount;
        binding.tvProductDiscount.setText(""+ off + "% Off");
        binding.tvProductDescription.setText("" + product.getDescription());
        binding.tvQuantity.setText("" + product.getQtyInStock());
        Picasso.get().load(product.getImageUrl()).placeholder(R.mipmap.app_logo).into(binding.ivProductImage);

        // binding.tvProductDescription.setTextColor(this.getResources().getColor(R.color.black));

        binding.btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDescriptionActivity.this, "Buy clicked", Toast.LENGTH_SHORT).show();
               // Intent in = new Intent(ProductDescriptionActivity.this, BuyActivity.class);
               // in.putExtra("product",product);
               // startActivity(in);
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedToInternet(ProductDescriptionActivity.this)){
                    Toast.makeText(ProductDescriptionActivity.this, "Add to cart clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.ivAddtoFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedToInternet(ProductDescriptionActivity.this)){
                    FavoriteService.FavoriteApi favoriteApi = FavoriteService.getFavoriteApiInstance();
                    Call<Favorite> call = favoriteApi.addFavorite();
                    call.enqueue(new Callback<Favorite>() {
                        @Override
                        public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                            binding.ivAddtoFavorite.setColorFilter(R.color.yellow);

                            Toast.makeText(ProductDescriptionActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Favorite> call, Throwable t) {
                            Toast.makeText(ProductDescriptionActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.e("Error ","===>" + t);
                        }
                    });
                }
            }
        });

    }// eOf onCreate

    public boolean isConnectedToInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
