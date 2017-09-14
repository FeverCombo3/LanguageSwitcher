package com.fever.lan;

import android.app.Application;

import com.fever.language.LanguageCountry;
import com.fever.language.LanguageSwitcher;

/**
 * Created by YJQ on 2017/9/13.
 * Language Application
 */

public class LanguageApplication extends Application {

    public static final String[] PROJECT_LANGUAGES = {
            LanguageCountry.LANGUAGE_OPTION_EN,
            LanguageCountry.LANGUAGE_OPTION_RU,
            LanguageCountry.LANGUAGE_OPTION_ES,
            LanguageCountry.LANGUAGE_OPTION_IT,
            LanguageCountry.LANGUAGE_OPTION_ID,
            LanguageCountry.LANGUAGE_OPTION_TR,
            LanguageCountry.LANGUAGE_OPTION_DE,
            LanguageCountry.LANGUAGE_OPTION_ZH_CN,
            LanguageCountry.LANGUAGE_OPTION_ZH_TW
    };

    @Override
    public void onCreate() {
        super.onCreate();

        LanguageSwitcher.getInstance().initLanguage(getApplicationContext(),PROJECT_LANGUAGES);
    }
}
