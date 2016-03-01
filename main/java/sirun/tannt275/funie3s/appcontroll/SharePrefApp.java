package sirun.tannt275.funie3s.appcontroll;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by TanNT on 12/2/2015.
 */
public class SharePrefApp {

    public static String TAG = SharePrefApp.class.getSimpleName();
    private static SharedPreferences preferences;

    public static SharedPreferences getPreferences() {
        if(preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(Funie3sApplication.getContext());
        return preferences;
    }
    public static void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue){
        return getPreferences().getBoolean(key, defaultValue);
    }

    public static String getString(String key, String defaultValue){
        return getPreferences().getString(key, defaultValue);
    }
    public static void putString(String key, String value){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defaultValue){
        return getPreferences().getInt(key, defaultValue);
    }

    public static void putInt(String key,int value){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
