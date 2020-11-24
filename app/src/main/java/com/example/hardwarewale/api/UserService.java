package com.example.hardwarewale.api;



import com.example.hardwarewale.bean.User;
import com.example.hardwarewale.utility.ServerAddress;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class UserService {
public static UserApi userApi;
    public static UserApi getUserApiInstance(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerAddress.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        if(userApi == null)
            userApi = retrofit.create(UserApi.class);
        return userApi;
    }
    public interface UserApi{
        @Multipart
        @POST("/user/save")
        public Call<User> saveUser(@Part  MultipartBody.Part file,
                                   @Part("name")RequestBody name,
                                   @Part("address") RequestBody address,
                                   @Part("mobile") RequestBody mobile,
                                   @Part("email")RequestBody email,
                                   @Part("token") RequestBody token);



      @POST("/user/updateUser")
        Call<User> updateUser(@Body User user);

      @Multipart
      @POST("/user/updateUserImage")
      Call<User> updateUserImage(File file, String userId);
    }

}
