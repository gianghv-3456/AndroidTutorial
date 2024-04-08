package com.example.activitylabpractice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.activitylabpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val DATA_SEND_KEY = "com.example.activitylabpractice.main.data_send_key"
        const val DATA_REPLY_KEY = "com.example.activitylabpractice.main.data_reply_key"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextViewsVisibility(false)
        binding.button.setOnClickListener { startSecondActivity() }
    }

    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        attachMessageToIntent(intent)
        getResult.launch(intent)
    }

    private fun attachMessageToIntent(intent: Intent) {
        val message = binding.editTextId.text.toString()
        intent.putExtra(DATA_SEND_KEY, message)

        //clear edittext
        binding.editTextId.setText("")
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val reply = it.data?.getStringExtra(DATA_REPLY_KEY)
                if (!reply.isNullOrEmpty()) {
                    setTextViewsVisibility(true)
                    updateMessageTextView(reply)
                } else {
                    setTextViewsVisibility(false)
                }
            } else {
                Log.d("Haha","result code not ok")
            }
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