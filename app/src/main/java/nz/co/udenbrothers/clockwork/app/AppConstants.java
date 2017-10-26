package nz.co.udenbrothers.clockwork.app;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by lexap on 13.10.2017.
 */

public class AppConstants {
    public static final class API{

        public static final HttpLoggingInterceptor.Level API_DEBUG_LEVEL = HttpLoggingInterceptor.Level.BODY;
        public static final String BASE_URL = "https://clockwork-api.azurewebsites.net/";
    }

    public static final class Database {
        public static final String DB_NAME = "shifts.db";
    }
}
