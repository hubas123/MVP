package nz.co.udenbrothers.clockwork.activities.common;

import android.os.Bundle;

import java.util.List;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.activities.boss.BossHomeActivity;
import nz.co.udenbrothers.clockwork.activities.staff.StaffHomeActivity;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.models.db.DaoSession;
import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItemDao;
import nz.co.udenbrothers.clockwork.network.Api;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.MyDate;


public class LoadingActivity extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        saveShifts();

    }

    private void saveShifts() {
        List<ShiftItem> shifts =
                ((App)getApplication()).getDaoSession().getShiftItemDao()
                .queryBuilder().where(ShiftItemDao.Properties.Uploaded.eq(0))
                .list();


        Api.saveShifts(this, shifts, new Api.OnApiResultListener<Object>() {
            @Override
            public void onFailed(int statusCode, String errorMsg) {
                alert(errorMsg);
                getProjects();
            }

            @Override
            public void onSuccess(Object result) {
                getProjects();
            }

            @Override
            public void onNoConnection() {
                alert(R.string.no_internet_connection);
                getProjects();
            }
        });
    }

    private void getProjects() {
        Api.getProjects(this, new Api.OnApiResultListener<List<ProjectItem>>() {
            @Override
            public void onFailed(int statusCode, String errorMsg) {
                alert(getString(R.string.failed_download_projects,errorMsg));
                getShifts();
            }

            @Override
            public void onSuccess(List<ProjectItem> result) {
                DaoSession session = ((App)getApplication()).getDaoSession();
                if (result!=null)
                    for (int i=0;i<result.size();i++)
                        session.insertOrReplace(result.get(i));
                getShifts();
            }

            @Override
            public void onNoConnection() {
                alert(R.string.no_internet_connection);
                getShifts();
            }
        });
    }

    private void getShifts() {
        Api.getShifts(this, new Api.OnApiResultListener<List<ShiftItem>>() {
            @Override
            public void onFailed(int statusCode, String errorMsg) {
                alert(getString(R.string.failed_download_shifts,errorMsg));
                shiftsDownloaded();
            }

            @Override
            public void onSuccess(List<ShiftItem> result) {
                DaoSession session = ((App)getApplication()).getDaoSession();
                if (result!=null) {
                    for (ShiftItem shift : result) {
                        shift.setShiftTimeEndOnUtc(MyDate.convert(shift.getShiftTimeEndOnUtc()));
                        shift.setShiftTimeStartOnUtc(MyDate.convert(shift.getShiftTimeStartOnUtc()));
                        session.getShiftItemDao().insertOrReplace(shift);
                    }
                }
                shiftsDownloaded();
            }



            @Override
            public void onNoConnection() {
                alert(R.string.no_internet_connection);
                shiftsDownloaded();
            }
        });


    }

    private void shiftsDownloaded() {
        if (Profile.role() == 1) {
            toActivity(StaffHomeActivity.class);
        } else if (Profile.role() == 2) {
            getUsers();
        } else {
            toActivity(SignInActivity.class);
        }
    }

    private void getUsers() {
        Api.getUsers(this, new Api.OnApiResultListener<Object>() {
            @Override
            public void onFailed(int statusCode, String errorMsg) {
                alert(getString(R.string.failed_download_users,errorMsg));
                usersDownloaded();

            }

            @Override
            public void onSuccess(Object result) {
                usersDownloaded();
            }

            @Override
            public void onNoConnection() {
                alert(R.string.no_internet_connection);
                usersDownloaded();
            }
        });
    }

    private void usersDownloaded() {
        toActivity(BossHomeActivity.class);
    }

    @Override
    public void onBackPressed() {}
}
