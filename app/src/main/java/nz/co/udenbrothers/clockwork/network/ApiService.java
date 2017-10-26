package nz.co.udenbrothers.clockwork.network;

import java.util.List;

import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * API calls
 */
public interface ApiService {
    @POST("v1/authentication/login")
    Call<LoginInfo> signin(@Body RequestBody body);

    @POST("v1/projects/shifts/save")
    Call<Object> saveShifts(@Body RequestBody body);

    @GET("v1/projects")
    Call<List<ProjectItem>> getProjects();

    @POST("v1/projects/shifts")
    Call<List<ShiftItem>> getShifts(@Body RequestBody body);

    @GET("v1/users")
    Call<Object> getUsers();
}
