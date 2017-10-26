package nz.co.udenbrothers.clockwork.activities.common;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.activities.boss.BossHomeActivity;
import nz.co.udenbrothers.clockwork.activities.staff.StaffHomeActivity;
import nz.co.udenbrothers.clockwork.app.Preferences;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.temps.Screen;


public class SplashActivity extends MainActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            int versionCode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
            if(versionCode != Preferences.getInstance().getInt("version")){
                Preferences.getInstance().clear();
                Preferences.getInstance().putInt("version", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            alert("problem versioning this app");
        }


        handler = new Handler();


        if(Profile.role() == 1){
            handler.postDelayed(()->toActivity(StaffHomeActivity.class), 600);
        }
        else if (Profile.role() == 2){
            handler.postDelayed(()->toActivity(BossHomeActivity.class), 600);
        }
        else {
            handler.postDelayed(()->toActivity(SignInActivity.class), 600);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Screen.density = metrics.density;

        RelativeLayout mainRe = findViewById(R.id.SplashMainScreen);
        final ViewTreeObserver observer= mainRe.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                Screen.width  = mainRe.getWidth();
                Screen.height = mainRe.getHeight();
                if (Screen.width > 0 && Screen.height  > 0) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        mainRe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mainRe.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    //DO SOMETHING WITH NON-ZERO DIMENSIONS
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
