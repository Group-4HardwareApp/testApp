package com.example.hardwarewale;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.hardwarewale.api.UserService;
import com.example.hardwarewale.bean.User;
import com.example.hardwarewale.databinding.ActivitySettingBinding;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;


public class SettingActivity  extends AppCompatActivity {
    ActivitySettingBinding binding;
    Uri imageUri;

    SharedPreferences sp = null;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user",MODE_PRIVATE);
        LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
        binding = ActivitySettingBinding.inflate(inflater);
        View v = binding.getRoot();
        setContentView(v);
  //      prefrenceCall();

        Intent in = getIntent();
        awesomeValidation  =  new AwesomeValidation(BASIC);

/*
        awesomeValidation.addValidation(binding.etName, "[^\\s*$][a-zA-Z\\s]+", "Enter correct name");
        awesomeValidation.addValidation(binding.etMobile, "^[0-9]{10}$", "Enter correct  Mobile number");
        awesomeValidation.addValidation(binding.etEmail, Patterns.EMAIL_ADDRESS, "Enter correct Email");
        awesomeValidation.addValidation(binding.etAddress, "[^\\s*$][a-zA-Z0-9,/\\s]+", "Enter correct Address");








        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (isConnected()) {
                    Log.e("isConnected", "TRUE");
                    Log.e("VALIDATION", "" + awesomeValidation.validate());

                    if (awesomeValidation.validate()) {
                        String name = binding.etName.getText().toString().trim();
                        String mobile = binding.etMobile.getText().toString().trim();
                        String email = binding.etEmail.getText().toString().trim();
                        String address = binding.etAddress.getText().toString().trim();
                        String userId = sp.getString("userId","id here");
                        String token = sp.getString("token","token here");

                        User u =new User();
                        u.setName(name);
                        u.setMobile(mobile);
                        u.setEmail(email);
                        u.setAddress(address);
                        u.setUserId(userId);
                        u.setToken(token);


                        Log.e("prec","Updated"+u.getName()+u.getUserId()+u.getAddress());



                        UserService.UserApi userApi = UserService.getUserApiInstance();
                        Call<User> call = userApi.updateUser(u);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.code() == 200) {
                                    User user = response.body();
                                    Toast.makeText(SettingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("imageUrl", user.getImageUrl());
                                    editor.putString("name", user.getName());
                                    editor.putString("mobile", user.getAddress() );
                                    editor.putString("email", user.getEmail());
                                    editor.putString("address",user.getMobile());
                                    editor.putString("token", user.getToken());
                                    editor.putString("userId", user.getUserId());
                                    editor.commit();
                                    finish();
                                } else
                                    Toast.makeText(SettingActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(SettingActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                Log.e("THROWED", "" + t);
                            }
                        });
                    }

                } else {
                    Toast.makeText(SettingActivity.this, "Enter Correct input", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void prefrenceCall() {

        Picasso.get().load(sp.getString("imageUrl","imageUrl here")).into(binding.civImage);
        binding.etName.setText(sp.getString("name","name here"));
        binding.etMobile.setText(sp.getString("mobile","mobile here"));
        binding.etEmail.setText(sp.getString("email","email here"));
        binding.etAddress.setText(sp.getString("address","address here"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            binding.etbtnEdit.setImageURI(imageUri);
            Toast.makeText(this, "FIRST" + imageUri, Toast.LENGTH_SHORT).show();
            binding.civImage.setImageURI(imageUri);
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
*/    }


}

