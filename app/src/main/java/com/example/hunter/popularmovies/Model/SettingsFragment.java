package com.example.hunter.popularmovies.Model;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.hunter.popularmovies.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
    }
}
