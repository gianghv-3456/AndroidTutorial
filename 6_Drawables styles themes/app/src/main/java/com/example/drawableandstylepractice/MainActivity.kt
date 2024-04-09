package com.example.drawableandstylepractice

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.example.drawableandstylepractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TEAM_1_COUNT_SAVE_STATE_KEY = "com.example.drawandstyle.TEAM1SAVESTATEKEY"
        private const val TEAM_2_COUNT_SAVE_STATE_KEY = "com.example.drawandstyle.TEAM2SAVESTATEKEY"
    }

    private lateinit var binding: ActivityMainBinding
    private val team1Count = MutableLiveData(0)
    private val team2Count = MutableLiveData(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNightModeFlagOnRun()
        changeTheOptionMenuTitleByUiMode()
        registerButtonClickEvents()
        registerOptionMenuEvent()
        observeLiveDataForCountTextViews()
    }

    private fun registerButtonClickEvents() {
        binding.team0AddBtn.setOnClickListener {
            val currentValue = team1Count.value!!
            team1Count.postValue(currentValue + 1)
        }

        binding.team0RemoveBtn.setOnClickListener {
            var currentValue = team1Count.value!!
            currentValue--

            if (currentValue < 0) currentValue = 0

            team1Count.postValue(currentValue)
        }

        binding.team1AddBtn.setOnClickListener {
            val currentValue = team2Count.value!!
            team2Count.postValue(currentValue+1)
        }

        binding.team1RemoveBtn.setOnClickListener {
            var currentValue = team2Count.value!!
            currentValue--
            if (currentValue < 0) currentValue = 0
            team2Count.postValue(currentValue)
        }
    }

    private fun observeLiveDataForCountTextViews() {
        team1Count.observe(this) {
            binding.team0Tv.text = it.toString()
        }


        team2Count.observe(this) {
            binding.team1Tv.text = it.toString()
        }
    }

    private fun registerOptionMenuEvent() {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.theme_mode_appbar_menu -> {
                    toggleDarkMode()
                }
            }

            return@setOnMenuItemClickListener true
        }
    }


    private fun toggleDarkMode() {
        Log.d("ToggleDarkFunc", "current night mode: \t${NightModeUtils.isNightMode}")
        if (NightModeUtils.isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            recreate()
            // Night mode is active, we're using dark theme
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            recreate()
            // Night mode is not active, we're using the light theme
        }

        NightModeUtils.isNightMode = !NightModeUtils.isNightMode
    }


    private fun changeTheOptionMenuTitleByUiMode() {
        val stringIdToSet =
            if (NightModeUtils.isNightMode) R.string.day_mode_appbar_menu else R.string.night_mode_appbar_menu
        binding.topAppBar.menu.findItem(R.id.theme_mode_appbar_menu).setTitle(stringIdToSet)
    }

    /**
     * Detect current UI Mode and store that mode into flag
     */
    private fun setNightModeFlagOnRun() {
        if (NightModeUtils.isFirstRun) {
            NightModeUtils.isFirstRun = false

            val currentNightMode =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            NightModeUtils.isNightMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES
        }
    }

    /**
     * Save and restore state of activity (count number) when change app theme
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        team1Count.value?.let { outState.putInt(TEAM_1_COUNT_SAVE_STATE_KEY, it) }
        team2Count.value?.let { outState.putInt(TEAM_2_COUNT_SAVE_STATE_KEY, it) }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val team1 = savedInstanceState.getInt(TEAM_1_COUNT_SAVE_STATE_KEY, 0)
        val team2 = savedInstanceState.getInt(TEAM_2_COUNT_SAVE_STATE_KEY, 0)

        team1Count.postValue(team1)
        team2Count.postValue(team2)
    }
}