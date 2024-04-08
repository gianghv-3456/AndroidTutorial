package com.example.activitylabpractice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.activitylabpractice.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("HAHA","data extras: ${intent.extras}")
        if (hasMessageInIntentExtra(intent)) {
            setTextViewsVisibility(true)
            Log.d("HAHA","data extras str: ${intent.extras?.getString(MainActivity.DATA_SEND_KEY)}")
            val message = intent.extras?.getString(MainActivity.DATA_SEND_KEY)
            updateMessageTextView(message.toString())
        } else {
            setTextViewsVisibility(false)
        }

        binding.button.setOnClickListener { replyMessageToMainActivity() }

    }

    private fun replyMessageToMainActivity() {
        val message = binding.editTextId.text.toString()

        val replyIntent = Intent()
        replyIntent.putExtra(MainActivity.DATA_REPLY_KEY,message)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    private val hasMessageInIntentExtra: (Intent) -> Boolean = {
        it.extras?.containsKey(MainActivity.DATA_SEND_KEY) == true
    }

    private fun updateMessageTextView(str: String) {
        binding.textView2.text = str
    }

    private fun setTextViewsVisibility(visible: Boolean) {
        if (visible) {
            binding.textView.visibility = View.VISIBLE
            binding.textView2.visibility = View.VISIBLE
        } else {
            binding.textView.visibility = View.INVISIBLE
            binding.textView2.visibility = View.INVISIBLE
        }
    }
}