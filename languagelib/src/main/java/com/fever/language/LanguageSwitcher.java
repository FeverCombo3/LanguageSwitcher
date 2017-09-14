package com.fever.language;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 语言切换初始化
 * Created by YJQ on 2017-09-13.
 */

public class LanguageSwitcher {

    private static LanguageSwitcher mInstance;
    private static String[] mLanguages;

    private LanguageSwitcher(){};

    public static LanguageSwitcher getInstance(){
        if(mInstance == null){
            mInstance = new LanguageSwitcher();
        }
        return mInstance;
    }

    public void initLanguage(Context context,String[] languages){
        LanguageConfig config = LanguageConfig.newInstance(context);

        String language = config.getLanguageValue();
        String country = config.getCountryNameValue();

        if(language.equalsIgnoreCase(LanguageCountry.LANGUAGE_OPTION_DEFAULT) //第一次安装，设置为系统默认语言
                && country.equalsIgnoreCase(LanguageCountry.COUNTRY_OPTION_DEFAULT)){
            Locale locale = Locale.getDefault();
            config.setLanguageValue(locale.getLanguage());
            config.setCountryNameValue(locale.getCountry());
        }else {//设置为选择的语言
            Locale locale = new Locale(language,country);
            Locale.setDefault(locale);
            Configuration configuration = context.getResources().getConfiguration();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            configuration.locale = locale;
            context.getResources().updateConfiguration(configuration, metrics);
        }

        this.mLanguages = languages;
    }

    public String[] getLanguages(){
        return mLanguages;
    }
}
