package com.example.hardwarewale;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.hardwarewale.adapter.ProductAdapter;
import com.example.hardwarewale.api.ProductService;
import com.example.hardwarewale.bean.Category;
import com.example.hardwarewale.bean.Product;
import com.example.hardwarewale.databinding.ProductsScreenBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    ProductsScreenBinding binding;
    ProductAdapter adapter;
    ActionBarDrawerToggle toggle;
    Category category;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProductsScreenBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        getNavigationDrawer();
        Intent in = getIntent();
        category = (Category) in.getSerializableExtra("category");
        showProducts();
    }

    private void showProducts() {
        if(isConnectedToInternet(this)){
            ProductService.ProductApi productApi = ProductService.getProductApiInstance();
            Call<ArrayList<Product>> call = productApi.viewProductByCategory(category.getCategoryId());
            call.enqueue(new Callback<ArrayList<Product>>() {
                @Override
                public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                    ArrayList<Product> productList = response.body();
                    //Log.e("Response ","==>"+response.code());
                    for (Product p : productList)
                        Log.e("Product ","===> " +p);
                    adapter = new ProductAdapter(ProductActivity.this,productList);
                    binding.rvProduct.setAdapter(adapter);
                    binding.rvProduct.setLayoutManager(new GridLayoutManager(ProductActivity.this,2));

                    adapter.setOnItemClicklistner(new ProductAdapter.OnRecyclerViewClick() {
                        @Override
                        public void onItemClick(Product product, int position) {
                            Intent in = new Intent(ProductActivity.this, ProductDescriptionActivity.class);
                            in.putExtra("product",product);
                            startActivity(in);
                        }
                    });
                }

                @Override
                public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

                }
            });
        }
        else
            Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
    }

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

    private void getNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(this,binding.drawer,binding.toolbar,R.string.open,R.string.close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id ==  R.id.menuHome){
                    Toast.makeText(ProductActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuCart){
                    Toast.makeText(ProductActivity.this, "Cart clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuSetting){
                    Toast.makeText(ProductActivity.this, "Setting clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuFavorites){
                    Toast.makeText(ProductActivity.this, "Favorites clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuShopByCategoty){
                    Toast.makeText(ProductActivity.this, "Shop by category clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuManageOrders){
                    Toast.makeText(ProductActivity.this, "Manage Order clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuOrderHistory){
                    Toast.makeText(ProductActivity.this, "Order History clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuLogout){
                    Toast.makeText(ProductActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuAboutus){
                    Toast.makeText(ProductActivity.this, "Aboutus clicked", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menuContactus){
                    Toast.makeText(ProductActivity.this, "Contactus clicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
