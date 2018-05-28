package com.example.hunter.popularmovies.Model;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hunter.popularmovies.R;


public class SettingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = this.getSupportActionBar();
        if( actionBar != null ){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        getFragmentManager().beginTransaction().replace(android.R.id.content,new MenuFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
       if(id == android.R.id.home){
           NavUtils.navigateUpFromSameTask(this);
       }
        return super.onOptionsItemSelected(item);
    }

    public static class MenuFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}