package nz.co.udenbrothers.clockwork.activities.boss;

import android.os.Bundle;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.app.App;
import nz.co.udenbrothers.clockwork.app.V;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.ItemMaker;
import nz.co.udenbrothers.clockwork.temps.Profile;

public class BossProfileActivity extends BossActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_profile);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        TextView name = findViewById(R.id.profileNameTxt);
        name.setText(Profile.firstName() + " " + Profile.lastName());
        TextView email = findViewById(R.id.profileMailTxt);
        email.setText(Profile.email());
        TextView company = findViewById(R.id.profileComTxt);
        if(Profile.company() == null){
            company.setText("N/A");
        }else {
            company.setText(Profile.company());
        }

        clicked(R.id.editProfileButton,() -> pushActivity(BossEditProfileActivity.class));

        ItemMaker itemMaker = new ItemMaker(this);
        CollectionView collectionView = findViewById(R.id.noticeList);
        collectionView.init(itemMaker.toItems(((App)getApplication()).getDaoSession().getNoticeItemDao().loadAll(), V.NOTICE));
    }
}
