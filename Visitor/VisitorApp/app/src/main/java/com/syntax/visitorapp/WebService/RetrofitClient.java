package com.syntax.visitorapp.WebService;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static String SID="";

    public static String DateFormatter(String inputDate, String outputFormat) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
            SimpleDateFormat sdfo = new SimpleDateFormat(outputFormat.trim());
            String strDate = sdfo.format(date1);
            return strDate + "";
        } catch (Exception q) {
            Log.e("UTILITYCLASS_ERR", q.toString());
            return inputDate;

        }

    }
    Retrofit retrofit = null;
private OkHttpClient getClient() {
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(50000, TimeUnit.SECONDS)
            .readTimeout(50000, TimeUnit.SECONDS)
            .build();
    return client;
}
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.212:8080/VisitorServer/Android/").
                    addConverterFactory(GsonConverterFactory.create(gson)).
                    client(getClient()).
                    build();
        }
        return  retrofit;
    }
}
