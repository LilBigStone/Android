package com.karpov.lab2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.karpov.lab2.others.Constants.ACTION_TIMER_FRAGMENT
import com.karpov.lab2.timer.TimerService
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val themeId = when (preference.getString("theme", "Light")) {
            "Night" -> R.style.DarkTheme
            "Light" -> R.style.DayLightTheme
            else -> R.style.DayLightTheme
        }
        this.theme?.applyStyle(themeId, true)
        val locale = Locale(preference.getString("locale", "Ru"))
        val config = resources.configuration
        val appConfig = application.resources.configuration
        val appRes = application.resources
        if (locale.language != config.locale.language) {
            appConfig.locale = locale
            config.locale = locale
            appRes.updateConfiguration(appConfig, appRes.displayMetrics)
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        setContentView(R.layout.main_activity)
        navigateToTimerFragmentIfNeeded(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTimerFragmentIfNeeded(intent)
    }

    private fun navigateToTimerFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_TIMER_FRAGMENT) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
                R.id.action_global_timerFragment,
                bundleOf("Key" to TimerService.key.value)
            )
        }

    }
}