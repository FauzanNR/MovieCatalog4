package com.example.moviecatalog4.API_HELPER;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("{type}")
    Call<ResponseBody> dataRequest(@Path("type") String type);
}
