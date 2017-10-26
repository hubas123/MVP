package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.app.Preferences;

/**
 * Created by user on 29/08/2017.
 */

public class Act {
    public static String current(){
        return Preferences.getInstance().getStr("current");
    }

    public static String startTime(){
        return Preferences.getInstance().getStr("startTime");
    }

    public static void current(String n){
        Preferences.getInstance().putStr("current", n);
    }

    public static void startTime(String n){
        Preferences.getInstance().putStr("startTime", n);
    }
}
