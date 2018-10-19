package in.restroin.restroin.utils;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {
    private String authToken;

    public BasicAuthInterceptor(String authToken){
        this.authToken = authToken;
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticationRequest = request.newBuilder().addHeader("Authorization", authToken).build();
        return chain.proceed(authenticationRequest);
    }
}
