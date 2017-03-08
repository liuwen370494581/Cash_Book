package star.liuwen.com.cash_books.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

    // ====================StringPreference========================

    /**
     * 设置StringPreference
     *
     * @param context
     * @param key
     * @param content
     */
    public static void setStringPreferences(Context context, String key, String content) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, content);
        editor.commit();
    }

    /**
     * 获取StringPreference
     *
     * @param context
     * @param key
     * @param defaultStr
     * @return
     */
    public static String getStringPreferences(Context context, String key, String defaultStr) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defaultStr);
    }

    // ====================IntegerPreference========================

    /**
     * 设置IntPreference
     *
     * @param context
     * @param key
     * @param content
     */
    public static void setIntPreferences(Context context, String key, int content) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, content);
        editor.commit();
    }

    /**
     * 获取IntPreference
     *
     * @param context
     * @param key
     * @param defaultStr
     * @return
     */
    public static int getIntPreferences(Context context, String key, int defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, defaultValue);
    }

    // ====================BooleanPreference========================

    /**
     * 设置BooleanPreference
     *
     * @param context
     * @param key
     * @param content
     */
    public static void setBooleanPreferences(Context context, String key, boolean content) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, content);
        editor.commit();
    }

    /**
     * 获取BooleanPreference
     *
     * @param context
     * @param key
     * @param defaultBoolean
     * @return
     */
    public static boolean getBooleanPreferences(Context context, String key, boolean defaultBoolean) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, defaultBoolean);
    }

    // ====================FloatPreference========================

    /**
     * 设置FloatPreference
     *
     * @param context
     * @param key
     * @param content
     */
    public static void setFloatPreferences(Context context, String key, float content) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, content);
        editor.commit();
    }

    /**
     * 获取FloatPreference
     *
     * @param context
     * @param key
     * @param defaultFloat
     * @return
     */
    public static float getFloatPreferences(Context context, String key, float defaultFloat) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(key, defaultFloat);
    }

    // ====================LongPreference========================

    /**
     * 设置LongPreference
     *
     * @param context
     * @param key
     * @param content
     */
    public static void setLongPreferences(Context context, String key, long content) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, content);
        editor.commit();
    }

    /**
     * 获取LongPreference
     *
     * @param context
     * @param key
     * @param defaultLong
     * @return
     */
    public static long getLongPreferences(Context context, String key, long defaultLong) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, defaultLong);
    }


    public static void cleanSharePreferences(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }


}
