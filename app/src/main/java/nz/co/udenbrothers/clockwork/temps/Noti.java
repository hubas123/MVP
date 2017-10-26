package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.app.Preferences;

/**
 * Created by user on 29/08/2017.
 */

public class Noti {

    public static int COhour(){
        return Preferences.getInstance().getInt("COhour");
    }

    public static void COhour(int r){
        Preferences.getInstance().putInt("COhour", r);
    }

    public static int CIhour(){
        return Preferences.getInstance().getInt("CIhour");
    }

    public static void CIhour(int r){
        Preferences.getInstance().putInt("CIhour", r);
    }

    public static int COmin(){
        return Preferences.getInstance().getInt("COmin");
    }

    public static void COmin(int r){
        Preferences.getInstance().putInt("COmin", r);
    }

    public static int CImin(){
        return Preferences.getInstance().getInt("CImin");
    }

    public static void CImin(int r){
        Preferences.getInstance().putInt("CImin", r);
    }

}
