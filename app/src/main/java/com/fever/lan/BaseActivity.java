package com.fever.lan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fever.language.LanguageObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by YJQ on 2017/9/13.
 * Language BaseActivity
 */

public class BaseActivity extends AppCompatActivity implements Observer{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageObservable.getInstance().addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LanguageObservable.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        recreate();
    }
}
