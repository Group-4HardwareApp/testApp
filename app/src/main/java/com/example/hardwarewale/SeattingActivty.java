package com.example.hardwarewale;

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

public class SeattingActivty  extends AppCompatActivity {
    ActivitySettingBinding binding;
    Uri imageUri;

    SharedPreferences sp = null;
    AwesomeValidation awesomeValidation;
    Uri oldImageUri=imageUri;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(SeattingActivty.this);
        binding = ActivitySettingBinding.inflate(inflater);
        View v = binding.getRoot();
        setContentView(v);
        sp = getSharedPreferences("user",MODE_PRIVATE);
        prefrenceCall();
        awesomeValidation  =  new AwesomeValidation(BASIC);


        awesomeValidation.addValidation(binding.etName, "[^\\s*$][a-zA-Z\\s]+", "Enter correct name");
        awesomeValidation.addValidation(binding.etMobile, "^[0-9]{10}$", "Enter correct  Mobile number");
        awesomeValidation.addValidation(binding.etEmail, Patterns.EMAIL_ADDRESS, "Enter correct Email");
        awesomeValidation.addValidation(binding.etAddress, "[^\\s*$][a-zA-Z0-9,/\\s]+", "Enter correct Address");

        binding.etbtnEdit.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {

                                                     Intent in = new Intent();
                                                     in.setAction(Intent.ACTION_GET_CONTENT);
                                                     in.setType("image/*");
                                                     startActivityForResult(in, 1);

                if(oldImageUri!=imageUri){
                    File file = FileUtils.getFile(SeattingActivty.this, imageUri);
                    RequestBody requestFile =
                            RequestBody.create(
                                    MediaType.parse(getContentResolver().getType(imageUri)),
                                    file
                            );

                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    String userId= sp.getString("userId","user");
                    UserService.UserApi userApi= UserService.getUserApiInstance();
                    Call<User> call = userApi.updateUserImage(file,"userId");
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                           User user = response.body();
                            Toast.makeText(SeattingActivty.this, "Image Update", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("imageUrl",user.getImageUrl());
                            editor.putString("userId", user.getUserId());


                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
               }
            }
        });

    binding.btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isConnected()){
                if(awesomeValidation.validate()){
                    String name = binding.etName.getText().toString().trim();
                    String mobile = binding.etMobile.getText().toString().trim();
                    String email = binding.etEmail.getText().toString().trim();
                    String address = binding.etAddress.getText().toString().trim();
                    String userId = sp.getString("userId","userId here");
                    String token = sp.getString("token","token here");

                    User user = new User();
                    user.setName(name);
                    user.setMobile(mobile);
                    user.setEmail(email);
                    user.setAddress(address);
                    user.setUserId(userId);
                    user.setToken(token);

                    UserService.UserApi userApi = UserService.getUserApiInstance();
                    Call<User> call= userApi.updateUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                           if(response.code() ==  200){
                               User user= response.body();
                               Toast.makeText(SeattingActivty.this, "Update data", Toast.LENGTH_SHORT).show();
                               SharedPreferences.Editor editor = sp.edit();
                               editor.putString("name", user.getName());
                               editor.putString("mobile", user.getAddress());
                               editor.putString("email", user.getEmail());
                               editor.putString("address", user.getMobile());
                               editor.putString("token", user.getToken());
                               editor.putString("userId", user.getUserId());
                               editor.commit();
                               finish();

                           }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(SeattingActivty.this, "Enter Correct Input", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(SeattingActivty.this, "Please Connect Internet", Toast.LENGTH_SHORT).show();
            }
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

    private boolean isConnected() {
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
    }
}
