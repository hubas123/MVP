package nz.co.udenbrothers.clockwork.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor adds the auth header to all requests
 */
public class ApiRequestInterceptor implements Interceptor {

    private String token;
    private String authType;

    private String getCredentialsForOAuth() {
        if (token==null)
            return null;
        return authType + " " + token;
    }

    public void setAuthToken(String authType,String token) {
        this.authType = authType;
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final String authorizationValue = getCredentialsForOAuth();
        if (authorizationValue != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", authorizationValue)
                    .build();
        }
        return chain.proceed(request);
    }
}