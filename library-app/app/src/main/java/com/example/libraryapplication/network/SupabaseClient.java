package com.example.libraryapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
public class SupabaseClient {
    private static final String BASE_URL = "https://nkrupyqxoyngryuhisrx.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5rcnVweXF4b3luZ3J5dWhpc3J4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcwMjg4OTUsImV4cCI6MjA2MjYwNDg5NX0.2xLUKFWqedMj-36Lr5QI8Ekpsaay265lAwK-YEOkdhM";
    private static Retrofit retrofit = null;
    public static SupabaseApi getApi() {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("apikey", API_KEY)
                                .header("Authorization", "Bearer " + API_KEY)
                                .header("Content-Type", "application/json")
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit.create(SupabaseApi.class);
    }
}