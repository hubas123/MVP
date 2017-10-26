package nz.co.udenbrothers.clockwork.app;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lexap on 13.10.2017.
 */

public class Preferences {
    private static Preferences sInstance;
    private final SharedPreferences p;

    private Preferences(Context context) {
        p = context.getSharedPreferences("app", MODE_PRIVATE);
    }

    public static void initInstance(Context context){
        sInstance = new Preferences(context);
    }
    public static Preferences getInstance(){
        if (sInstance==null)
            throw new RuntimeException("You must init instance first");
        return sInstance;
    }
    public void putStr(String key, String val){
        SharedPreferences.Editor editor = p.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public String getStr(String key){
        return p.getString(key, null);
    }

    public void putInt( String key, int val){
        SharedPreferences.Editor editor = p.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public int getInt(String key){
        return p.getInt(key, 0);
    }

    public void putLong( String key, long val){
        SharedPreferences.Editor editor = p.edit();
        editor.putLong(key, val);
        editor.apply();
    }

    public long getLong(String key){
        return p.getLong(key, 0);
    }

    public void putFloat( String key, float val){
        SharedPreferences.Editor editor = p.edit();
        editor.putFloat(key, val);
        editor.apply();
    }

    public float getFloat(String key){
        return p.getFloat(key, 0f);
    }

    public void putBool(String key, Boolean val){
        SharedPreferences.Editor editor = p.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public Boolean getBool(String key, Boolean def){
        return p.getBoolean(key, def);
    }

    public void clear(){
        SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }

}
