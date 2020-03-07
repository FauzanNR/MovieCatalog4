package com.example.moviecatalog4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Sampler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.moviecatalog4.notification.NewToday;
import com.example.moviecatalog4.notification.ReminderEveryday;

import java.util.prefs.Preferences;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference
            .OnPreferenceChangeListener {

        SwitchPreference reminder;
        SwitchPreference today;
        Preference preference;
        NewToday newToday;
        ReminderEveryday reminderEveryday;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            reminder = findPreference(getString(R.string.reminder_everyday));
            today = findPreference(getString(R.string.movie_today));
            reminder.setOnPreferenceChangeListener(this);
            today.setOnPreferenceChangeListener(this);
            preference = findPreference(getString(R.string.Bahasa));
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;

                }
            });

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            newToday = new NewToday();
            reminderEveryday = new ReminderEveryday();
            String getKey = preference.getKey();
            boolean value = (boolean) newValue;
            if (getKey.equals(getString(R.string.reminder_everyday))){
                if (value){
                    reminderEveryday.setAlarm(getActivity());
                }else {
                    reminderEveryday.cancelAlarm(getActivity());
                }
            }else {
                if (value){
                    newToday.setAlarm(getActivity());
                }else {
                    newToday.cancelAlarm(getActivity());
                }
            }
            return true;
        }
    }
}