package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.SettingAdapter;
import com.example.myapplication.models.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView settingsRecyclerView;
    private SettingAdapter settingsAdapter;
    private List<Setting> settingsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsRecyclerView = (RecyclerView) findViewById(R.id.settingRecyclerView);
        settingsList = getSettings();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        settingsRecyclerView.setLayoutManager(layoutManager);

        settingsAdapter = new SettingAdapter(this,settingsList);
        settingsRecyclerView.setAdapter(settingsAdapter);

        settingsList.addAll(getSettings());
        settingsAdapter.notifyDataSetChanged();
    }
    public List<Setting> getSettings()
    {
        List<Setting> settings_List = new ArrayList<>();


        settings_List.add(new Setting(getString(R.string.language_settings_title),getString(R.string.language_settings_description),R.color.colorPrimary,R.drawable.language_icon));
        settings_List.add(new Setting(getString(R.string.themes_settings_title),getString(R.string.themes_settings_description),R.color.colorPrimary,R.drawable.theme_icon2));
        settings_List.add(new Setting(getString(R.string.account_settings_title),getString(R.string.account_settings_description),R.color.colorPrimary,R.drawable.account_icon));
        settings_List.add(new Setting(getString(R.string.notification_settings_title),getString(R.string.notifications_settings_description),R.color.colorPrimary,R.drawable.notification_icon));
        settings_List.add(new Setting(getString(R.string.accessibility_settings_title),getString(R.string.accessibility_settings_description),R.color.colorPrimary,R.drawable.accessibility));
        settings_List.add(new Setting(getString(R.string.help_settings_title),getString(R.string.help_settings_description),R.color.colorPrimary,R.drawable.help_icon));
        return settings_List;

    }

}