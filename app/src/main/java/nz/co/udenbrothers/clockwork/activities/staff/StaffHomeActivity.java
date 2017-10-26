package nz.co.udenbrothers.clockwork.activities.staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HomeItemMaker;
import nz.co.udenbrothers.clockwork.models.db.ProjectItem;
import nz.co.udenbrothers.clockwork.models.db.ShiftItem;
import nz.co.udenbrothers.clockwork.serverices.UploadService;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Popup;

public class StaffHomeActivity extends StaffActivity {

    private HomeItemMaker homeItemMaker;
    private CollectionView collectionView;
    @BindView(R.id.activityButton)
    Button buttonScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);
        ButterKnife.bind(this);

        clicked(R.id.imageHam, () -> sideMenu.show());

        homeItemMaker = new HomeItemMaker(this);
        collectionView = findViewById(R.id.siteList);
        collectionView.init(homeItemMaker.fetch());

        clicked(R.id.activityButton, () -> new IntentIntegrator(this).initiateScan());

        if (collectionView.getAdapter()!=null)
            collectionView.getAdapter().registerAdapterDataObserver(observer);
        updateButtonTitle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newName = Kit.QrScanResult(IntentIntegrator.parseActivityResult(requestCode, resultCode, data));
        if (newName.equals(""))
            return;
        ProjectItem newPro = new ProjectItem(newName);
        if (!homeItemMaker.search(newName))
            ((App)getApplication()).getDaoSession().getProjectItemDao().insert(newPro);
        if (!(Act.current() == null)) {
            ShiftItem shift = new ShiftItem(Act.current(), Act.startTime(), MyDate.dateToStr(new Date()), Profile.userID());
            ((App)getApplication()).getDaoSession().getShiftItemDao().insert(shift);

            Popup popup = new Popup(this, R.layout.comment_layout);
            TextView title = popup.getView(R.id.boxTitle);
            title.setText(getString(R.string.clocked_out,newName));
            EditText commentBox = popup.getView(R.id.commentBox);
            popup.clicked(R.id.saveButton, () -> {
                shift.setComment(commentBox.getText().toString().trim());
                ((App)getApplication()).getDaoSession().getShiftItemDao().update(shift);
                startService(new Intent(this, UploadService.class));
                popup.dismiss();
            });
            popup.show();

            if (!Act.current().equals(newName))
                setNameNtime(newName);
            else
                Act.current(null);
        } else {
            setNameNtime(newName);
        }
        collectionView.refresh(homeItemMaker.fetch());
    }

    private void setNameNtime(String name) {
        Act.current(name);
        Act.startTime(MyDate.dateToStr(new Date()));
    }

    @Override
    protected void onDestroy() {
        if (collectionView.getAdapter()!=null)
            collectionView.getAdapter().unregisterAdapterDataObserver(observer);
        super.onDestroy();
    }
    private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateButtonTitle();
        }
    };

    private void updateButtonTitle() {
        if (Act.current()==null){
            buttonScan.setText(R.string.start_activity);
        }
        else{
            buttonScan.setText(R.string.switch_end_actvity);
        }
    }
}
