 package android.example.dogs.view


import android.example.dogs.R
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

 class SettingsFragment : PreferenceFragmentCompat() {
     override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
         setPreferencesFromResource(R.xml.preferences, rootKey)
     }
 }