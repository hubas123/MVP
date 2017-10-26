package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.app.Preferences;


public class Profile {

    public static String firstName(){
        return Preferences.getInstance().getStr("firstName");
    }

    public static String lastName(){
        return Preferences.getInstance().getStr("lastName");
    }

    public static void firstName(String n){
        Preferences.getInstance().putStr("firstName", n);
    }

    public static void lastName(String n){
        Preferences.getInstance().putStr("lastName", n);
    }

    public static int role(){
        return Preferences.getInstance().getInt("role");
    }

    public static void role(int r){
        Preferences.getInstance().putInt("role", r);
    }

    public static void userID(String s){
        Preferences.getInstance().putStr("userID", s);
    }

    public static String userID(){
        return Preferences.getInstance().getStr("userID");
    }

    public static void token(String s){
        Preferences.getInstance().putStr("token", s);
    }

    public static String token(){
        return Preferences.getInstance().getStr("token");
    }

    public static void email(String s){
        Preferences.getInstance().putStr("email", s);
    }

    public static String email(){
        return Preferences.getInstance().getStr("email");
    }

    public static void company(String s){
        Preferences.getInstance().putStr("company", s);
    }

    public static String company(){
        return Preferences.getInstance().getStr("company");
    }

    public static void bussID(String s){
        Preferences.getInstance().putStr("bussID", s);
    }

    public static String bussID(){
        return Preferences.getInstance().getStr("bussID");
    }

    public static void logout(){
        token(null);
        userID(null);
        role(0);
    }
}
