package com.example.moviecatalog4.API_HELPER;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClien {

    private static Retrofit retrofit;
    public static Retrofit initializeRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request.Builder onGoing = chain.request().newBuilder();
                        return chain.proceed(onGoing.build());
                    }
                })
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUtility.BASE_URL_API)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
