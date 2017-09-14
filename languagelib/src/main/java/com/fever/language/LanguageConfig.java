package com.fever.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 语言设置类，调用SP
 * Created by YJQ on 2017/09/13.
 */
public class LanguageConfig {

    private SharedPreferences shardPreferences = null;

    public static LanguageConfig newInstance(Context context) {
        return new LanguageConfig(context);
    }

    private LanguageConfig(Context context) {
        if (null != context) {
            shardPreferences =  PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    private static final String LANGUAGE_SELECTED = "language_selected";
    private static final String COUNTRY_SELECTED = "country_selected";

    public void setLanguageValue(String language) {
        setStringValue(LANGUAGE_SELECTED, language);
    }

    public String getLanguageValue() {
        return getStringValue(LANGUAGE_SELECTED, LanguageCountry.LANGUAGE_OPTION_DEFAULT);
    }

    public void setCountryNameValue(String language) {
        setStringValue(COUNTRY_SELECTED, language);
    }

    public String getCountryNameValue() {
        return getStringValue(COUNTRY_SELECTED, LanguageCountry.COUNTRY_OPTION_DEFAULT);
    }


    private String getStringValue(String key, String defValue) {
        return null == shardPreferences ? defValue : shardPreferences.getString(key, defValue);
    }

    private void setStringValue(String key, String value) {
        if (null == shardPreferences) {
            return;
        }
        Editor editor = shardPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        if (null == shardPreferences) {
            return;
        }
        Editor editor = shardPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

}
