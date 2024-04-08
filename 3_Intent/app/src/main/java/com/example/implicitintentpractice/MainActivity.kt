package com.example.implicitintentpractice

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.implicitintentpractice.databinding.ActivityMainBinding
import java.net.URI
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.webBtn.setOnClickListener { openWebBrowserForUrl() }
        binding.openLocationBtn.setOnClickListener { openMapForLocation() }
        binding.shareTextBtn.setOnClickListener { shareInputtedText() }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openWebBrowserForUrl() {

        val inputtedUrl = binding.webUrlEdt.text.toString()

        if (!isValidUrl(inputtedUrl)) return

        val uri = Uri.parse(inputtedUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivityHandleIntent(intent)
    }

    private fun startActivityHandleIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("Implicit Intents", "Can't handle this intent!")
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            URI(url)
            true
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            false
        }
    }

    private fun openMapForLocation() {
        // Convert location inputted by user into Google Map's Query Uri
        val location = binding.locationEdt.text.toString()
        val gmmIntentUri = convertLocationToGGMapsSearchableUri(location)

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps")

        startActivityHandleIntent(mapIntent)
    }

    private fun convertLocationToGGMapsSearchableUri(location: String): Uri {
        val uriPrefix = "geo:0,0?q="

        // replace space character in location by "+" for uri friendly
        val uriStr = uriPrefix + location.replace(" ", "+")

        return Uri.parse(uriStr)
    }

    private fun shareInputtedText() {
        val sharingText = binding.shareTextEdt.text.toString().trim()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, sharingText)

        // set a title for the share dialog
        val chooserTitle = "Share via"
        startActivity(Intent.createChooser(intent, chooserTitle))
    }
}