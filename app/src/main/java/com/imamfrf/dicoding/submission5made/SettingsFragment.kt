package com.imamfrf.dicoding.submission5made


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.firebase.messaging.FirebaseMessaging


class SettingsFragment : PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val sharedPref = SharedPrefManager(context as Context).getInstance(context as Context)

        val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("daily_reminder")
        val releaseReminderSwitch = findPreference<SwitchPreferenceCompat>("release_reminder")
        val languagePreference = findPreference<Preference>("preference_language")


        dailyReminderSwitch?.isChecked = sharedPref.checkDailyReminder() == true

        releaseReminderSwitch?.isChecked = sharedPref.checkReleaseReminder() == true

        dailyReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                val dailySwitch = preference as SwitchPreferenceCompat

                if (dailySwitch.isChecked){
                    sharedPref.setDailyReminder(false)
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("DailyReminder")

                } else{
                    sharedPref.setDailyReminder(true)
                    FirebaseMessaging.getInstance().subscribeToTopic("DailyReminder")

                }
                true
            }

        releaseReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, _ ->
                val releaseSwitch = preference as SwitchPreferenceCompat

                if (releaseSwitch.isChecked){
                    sharedPref.setReleaseReminder(false)
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("ReleaseReminder")

                } else{
                    sharedPref.setReleaseReminder(true)
                    FirebaseMessaging.getInstance().subscribeToTopic("ReleaseReminder")
                }
                true
            }

        languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }
}
