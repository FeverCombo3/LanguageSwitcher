package com.fever.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 设置语言
 * Created by YJQ on 2017/09/13.
 * */
public class SelectLanguageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

	private List<LanguageCountry> mLanguageList = null;
	private ListView mLanguageListView = null;
	private LanguageAdapter mLanguageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_language);

		initViews();
		initLanguageData();
	}

	private void initViews() {
		mLanguageListView = (ListView) findViewById(R.id.languages);
		mLanguageListView.setOnItemClickListener(this);
	}

	private void initLanguageData() {
		mLanguageList = new ArrayList<>();
		String[] languages = LanguageSwitcher.getInstance().getLanguages();
		for (String language : languages) {
			mLanguageList.add(new LanguageCountry(this, language));
		}

		mLanguageAdapter = new LanguageAdapter(this, mLanguageList);
		mLanguageListView.setAdapter(mLanguageAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (null != mLanguageList) {
			LanguageCountry item = mLanguageList.get(position);
			if (null != mLanguageAdapter && null != item) {
				mLanguageAdapter.setCurrentLang(item.getLanguage());
				mLanguageAdapter.setCurrentCountry(item.getCountry());
				mLanguageAdapter.notifyDataSetChanged();
			}

			selectLanguage(item);
		}
	}

	private void selectLanguage(LanguageCountry item) {
		LanguageConfig config = LanguageConfig.newInstance(this);

		config.setLanguageValue(item.getLanguage());
		config.setCountryNameValue(item.getCountry());
		setLanguage(item, this);

		LanguageObservable.getInstance().notifyObservers();

		SelectLanguageActivity.this.finish();
	}

	// 更新app当前语言
	public static void setLanguage(LanguageCountry languageCountry, Context context) {
		if (null == languageCountry || null == context) {
			return;
		}
		Locale locale = new Locale(languageCountry.getLanguage(), languageCountry.getCountry());
		Locale.setDefault(locale);
		Resources res = context.getResources();
		if (null != res) {
			Configuration config = res.getConfiguration();
			if (null != config) {
				config.locale = locale;
			}
			DisplayMetrics dm = res.getDisplayMetrics();
			if (null != config && null != dm) {
				res.updateConfiguration(config, dm);
			}
		}
	}

	/**
	 * Adapter
	 */
	private class LanguageAdapter extends BaseAdapter {

		private List<LanguageCountry> languages;
		private LayoutInflater inflater;
		private String mCurrentLang;
		private String mCurrentCountry;

		public LanguageAdapter(Context context, List<LanguageCountry> languages) {
			this.languages = languages;
			inflater = LayoutInflater.from(context);

			LanguageConfig config = LanguageConfig.newInstance(context);
			mCurrentLang = config.getLanguageValue();
			mCurrentCountry = config.getCountryNameValue();
		}

		public void setCurrentLang(String currentLang) {
			mCurrentLang = currentLang;
		}

		public void setCurrentCountry(String currentCon) {
			mCurrentCountry = currentCon;
		}

		@Override
		public int getCount() {
			return languages.size();
		}

		@Override
		public LanguageCountry getItem(int position) {
			return languages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_language, null);
				holder = new ViewHolder();
				holder.language = (CheckedTextView) convertView.findViewById(R.id.language);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			LanguageCountry languageCountry = getItem(position);
			String languageName = languageCountry.getLanguageName();
			holder.language.setText(languageName);

			if (mCurrentLang.equalsIgnoreCase(languageCountry.getLanguage())) {
				if (LanguageCountry.LANGUAGE_OPTION_ZH.equalsIgnoreCase(languageCountry.getLanguage())) {
					if (mCurrentCountry.equalsIgnoreCase(languageCountry.getCountry())) {
						holder.language.setChecked(true);
					} else {
						holder.language.setChecked(false);
					}
				} else {
					holder.language.setChecked(true);
				}
			} else {
				holder.language.setChecked(false);
			}


			return convertView;
		}

		private class ViewHolder {
			public CheckedTextView language;
		}

	}

}
