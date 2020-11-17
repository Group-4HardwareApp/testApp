package com.example.hardwarewale.api;

import com.example.hardwarewale.bean.Favorite;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.example.hardwarewale.utility.ServerAddress.BASE_URL;

public class FavoriteService {

    public static FavoriteApi favoriteApi;
    public static FavoriteApi getFavoriteApiInstance(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        if (favoriteApi == null)
            favoriteApi = retrofit.create(FavoriteApi.class);
        return favoriteApi;
    }

    public interface FavoriteApi{
        @GET("/favorite/")
        public Call<Favorite> addFavorite();
    }
}
