package com.fever.language;

import java.util.Observable;

/**
 * 语言切换的被观察者，控制切换
 * Created by YJQ on 2017-09-13.
 */

public class LanguageObservable extends Observable{

    private static LanguageObservable mInstance;

    private LanguageObservable() {
    }

    public static LanguageObservable getInstance(){
        if(mInstance == null){
            mInstance = new LanguageObservable();
        }
        return mInstance;
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }


    public void unregisterAll(){
        mInstance.deleteObservers();
        mInstance = null;
    }

}
