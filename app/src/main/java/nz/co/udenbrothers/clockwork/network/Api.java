package nz.co.udenbrothers.clockwork.network;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.serverObjects.GetShiftsInfo;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.SigninInfo;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Json;
import nz.co.udenbrothers.clockwork.util.ConnectionHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */

public class Api {

    public interface OnApiResultListener<T>
    {
        void onFailed(int statusCode,String errorMsg);

        void onSuccess(T result);

        void onNoConnection();
    }
    public static class SimpleApiResultListener<T> implements OnApiResultListener<T>{
        @Override
        public void onFailed(int statusCode,String errorMsg) {}

        @Override
        public void onSuccess(T result) {}

        @Override
        public void onNoConnection() {}
    }


    private static class ApiResponseCallback<T> implements Callback<T> {
        private final OnApiResultListener<T> callback;

        ApiResponseCallback(@NonNull OnApiResultListener<T> callback){
            this.callback = callback;
        }
        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
            if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
            } else {
                callback.onFailed(response.code(),response.message());
            }
        }

        @Override
        public void onFailure(@NonNull Call<T> call, Throwable t) {
            callback.onFailed(-1, t.getMessage());
        }
    }

    public static Call<LoginInfo> signin(@NonNull Context context, @NonNull SigninInfo profile, @NonNull final OnApiResultListener<LoginInfo> callback){
        if (ConnectionHelper.isConnected(context)){
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), Json.to(profile));
            Call<LoginInfo> call = ApiFactory.getApiService().signin(body);
            call.enqueue(new ApiResponseCallback<>(callback));
            return call;
        }
        else{
            callback.onNoConnection();
            return null;
        }
    }

    public static Call<Object> saveShifts(@NonNull Context context, @NonNull List<ShiftItem> shiftList, @NonNull final OnApiResultListener<Object> callback){
        if (ConnectionHelper.isConnected(context)){
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), Json.to(shiftList));
            Call<Object> call = ApiFactory.getApiService().saveShifts(body);
            call.enqueue(new ApiResponseCallback<>(callback));
            return call;
        }
        else{
            callback.onNoConnection();
            return null;
        }
    }

    public static Call<List<ProjectItem>> getProjects(@NonNull Context context, @NonNull final OnApiResultListener<List<ProjectItem>> callback){
        if (ConnectionHelper.isConnected(context)){
            Call<List<ProjectItem>> call = ApiFactory.getApiService().getProjects();
            call.enqueue(new ApiResponseCallback<>(callback));
            return call;
        }
        else{
            callback.onNoConnection();
            return null;
        }
    }

    public static Call<List<ShiftItem>> getShifts(@NonNull Context context,@NonNull final OnApiResultListener<List<ShiftItem>> callback){
        if (ConnectionHelper.isConnected(context)){
            GetShiftsInfo info = new GetShiftsInfo(Profile.userID());
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), Json.to(info));
            Call<List<ShiftItem>> call = ApiFactory.getApiService().getShifts(body);
            call.enqueue(new ApiResponseCallback<>(callback));
            return call;
        }
        else{
            callback.onNoConnection();
            return null;
        }
    }

    public static Call<Object> getUsers(@NonNull Context context,@NonNull final OnApiResultListener<Object> callback){
        if (ConnectionHelper.isConnected(context)){
            Call<Object> call = ApiFactory.getApiService().getUsers();
            call.enqueue(new ApiResponseCallback<>(callback));
            return call;
        }
        else{
            callback.onNoConnection();
            return null;
        }
    }

}
