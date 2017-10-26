package nz.co.udenbrothers.clockwork.activities.common;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.app.Preferences;
import nz.co.udenbrothers.clockwork.temps.Profile;

public class WorkdaysSelectActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workdays_select);

        RelativeLayout backG = findViewById(R.id.backG);
        if(Profile.role() == 1) backG.setBackgroundColor(0xFFFFAF4D);
        else backG.setBackgroundColor(0xFF00BFFF);

        CheckBox mon = findViewById(R.id.checkMon);
        CheckBox tue = findViewById(R.id.checkTue);
        CheckBox wed = findViewById(R.id.checkWed);
        CheckBox thu = findViewById(R.id.checkThu);
        CheckBox fri = findViewById(R.id.checkFri);
        CheckBox sat = findViewById(R.id.checkSat);
        CheckBox sun = findViewById(R.id.checkSun);

        if(Preferences.getInstance().getBool("monday",true)) mon.setChecked(true);
        if(Preferences.getInstance().getBool("tuesday",true)) tue.setChecked(true);
        if(Preferences.getInstance().getBool("wednesday",true)) wed.setChecked(true);
        if(Preferences.getInstance().getBool("thursday",true)) thu.setChecked(true);
        if(Preferences.getInstance().getBool("friday",true)) fri.setChecked(true);
        if(Preferences.getInstance().getBool("sunday",true)) sun.setChecked(true);
        if(Preferences.getInstance().getBool("saturday",true)) sat.setChecked(true);

        clicked(mon,()->{
            if(Preferences.getInstance().getBool("monday",true)){
                Preferences.getInstance().putBool("monday", false);
                mon.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("monday", true);
                mon.setChecked(true);
            }
        });
        clicked(tue,()->{
            if(Preferences.getInstance().getBool("tuesday",true)){
                Preferences.getInstance().putBool("tuesday", false);
                tue.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("tuesday", true);
                tue.setChecked(true);
            }
        });
        clicked(wed,()->{
            if(Preferences.getInstance().getBool("wednesday",true)){
                Preferences.getInstance().putBool("wednesday", false);
                wed.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("wednesday", true);
                wed.setChecked(true);
            }
        });
        clicked(thu,()->{
            if(Preferences.getInstance().getBool("thursday",true)){
                Preferences.getInstance().putBool("thursday", false);
                thu.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("thursday", true);
                thu.setChecked(true);
            }
        });
        clicked(fri,()->{
            if(Preferences.getInstance().getBool("friday",true)){
                Preferences.getInstance().putBool("friday", false);
                fri.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("friday", true);
                fri.setChecked(true);
            }
        });
        clicked(sat,()->{
            if(Preferences.getInstance().getBool("saturday",true)){
                Preferences.getInstance().putBool("saturday", false);
                sat.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("saturday", true);
                sat.setChecked(true);
            }
        });
        clicked(sun,()->{
            if(Preferences.getInstance().getBool("sunday",true)){
                Preferences.getInstance().putBool("sunday", false);
                sun.setChecked(false);
            }
            else {
                Preferences.getInstance().putBool("sunday", true);
                sun.setChecked(true);
            }
        });

        clicked(R.id.comButton, this::finish);
    }
}
