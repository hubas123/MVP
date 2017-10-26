package nz.co.udenbrothers.clockwork.activities.boss;

import android.os.Bundle;

import nz.co.udenbrothers.clockwork.R;

public class BossMyTeamActivity extends BossActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_my_team);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        clicked(R.id.addStaffButton, ()-> pushActivity(AddStaffActivity.class));

    }
}
