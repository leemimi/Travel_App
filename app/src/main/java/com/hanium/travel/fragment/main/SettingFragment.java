package com.hanium.travel.fragment.main;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.hanium.travel.R;

public class SettingFragment extends PreferenceFragmentCompat {

    public static SettingFragment newInstance() {
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);


    }
}
