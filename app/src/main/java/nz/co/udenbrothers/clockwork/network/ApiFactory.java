package nz.co.udenbrothers.clockwork.network;

/**
 */

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import nz.co.udenbrothers.clockwork.app.AppConstants;
import nz.co.udenbrothers.clockwork.temps.Profile;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Init retrofit interface and provide api services
 */
public class ApiFactory
{
	private static final String TAG = ApiFactory.class.getSimpleName();

	private static final int CONNECT_TIMEOUT = 15;
	private static final int WRITE_TIMEOUT = 60;
	private static final int TIMEOUT = 60;

	private static final OkHttpClient CLIENT;
	private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
	private static final ApiRequestInterceptor authInterceptor = new ApiRequestInterceptor();

	private static Retrofit sRetrofit;
	private static ApiService service;

	static {
		loggingInterceptor.setLevel(AppConstants.API.API_DEBUG_LEVEL);
		CLIENT = new OkHttpClient.Builder()
				.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(TIMEOUT, TimeUnit.SECONDS)
				.addInterceptor(authInterceptor)
				.addInterceptor(loggingInterceptor)
				.build();
	}

	/**
	 * Get api service
	 *
	 * @return api service
	 */
	@NonNull
	static ApiService getApiService()
	{
		if (service==null)
			service = getRetrofit().create(ApiService.class);
		if (Profile.role()==0)
			setAuth(null,null);
		else
			setAuth("Basic",Profile.token());
		return service;
	}

	@NonNull
	private static Retrofit getRetrofit()
	{
		if (sRetrofit==null) {
			GsonBuilder builder = new GsonBuilder();
			builder.disableHtmlEscaping();
			Gson gson = builder.create();
			sRetrofit = new Retrofit.Builder()
					.baseUrl(AppConstants.API.BASE_URL)
					.addConverterFactory(ScalarsConverterFactory.create())
					.addConverterFactory(GsonConverterFactory.create(gson))
					.client(CLIENT)
					.build();
		}
		return sRetrofit;
	}

	private static void setAuth(String authType,String token){
		authInterceptor.setAuthToken(authType,token);
	}
}
