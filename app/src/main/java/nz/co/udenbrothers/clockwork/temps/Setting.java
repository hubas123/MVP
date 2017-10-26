package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.app.Preferences;

/**
 * Created by user on 29/08/2017.
 */

public class Setting {

    public static boolean wifi(){
        return Preferences.getInstance().getBool("wifi", false);
    }

    public static void wifi(boolean b){
        Preferences.getInstance().putBool("wifi", b);
    }

    public static boolean notification(){
        return Preferences.getInstance().getBool("notification", true);
    }

    public static void notification(boolean b){
        Preferences.getInstance().putBool("notification", b);
    }

    public static boolean reminder(){
        return Preferences.getInstance().getBool("reminder", false);
    }

    public static void reminder(boolean b){
        Preferences.getInstance().putBool("reminder", b);
    }

    public static boolean wedge(){
        return Preferences.getInstance().getBool("wedge", true);
    }

    public static void wedge(boolean b){
        Preferences.getInstance().putBool("wedge", b);
    }
}
