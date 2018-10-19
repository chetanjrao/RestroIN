package in.restroin.restroin.utils;

import android.text.TextUtils;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private final String API_BASE_URL = "https://www.restroin.in/developers/api/v2/";
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    private Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private Retrofit retrofit = builder.build();

    public <S> S createService(Class<S> serviceClass){
        return createService(serviceClass, null, null);
    }

    public <S> S createService(Class<S> serviceClass, String username, String password){
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            String accessToken = Credentials.basic(username, password);
            return createService(serviceClass, accessToken);
        }
        return createService(serviceClass, null);
    }

    public <S> S createService(Class<S> serviceClass,final String accessToken){
        if(!TextUtils.isEmpty(accessToken)){
            BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(accessToken);
            if(!httpClient.interceptors().contains(authInterceptor)){
                httpClient.addInterceptor(authInterceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }

}
