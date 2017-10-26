package nz.co.udenbrothers.clockwork.serverices;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

import nz.co.udenbrothers.clockwork.app.ApiLinks;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItemDao;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Json;
import nz.co.udenbrothers.clockwork.tools.RequestTask;



public class UploadService extends IntentService {


    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        List<ShiftItem> shifts = ((App)getApplication()).getDaoSession().getShiftItemDao().queryBuilder().where(
                ShiftItemDao.Properties.Uploaded.eq(0)
        ).list();

        Response response = RequestTask.myHttpConnection("POST", Json.to(shifts), ApiLinks.SAVE_SHIFTS, Profile.token());

        if(response.statusCode >= 200 && response.statusCode < 300){
            for (ShiftItem shift: shifts){
                shift.setUploaded(1);
                ((App)getApplication()).getDaoSession().getShiftItemDao().update(shift);
            }
        }

    }

}
