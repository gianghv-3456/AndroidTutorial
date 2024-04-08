package com.example.fragmentpractice

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentpractice.databinding.ActivityMainBinding
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isFeedbackFragmentOpened = false
    private val feedbackFragmentTag = "mainactivity.feedbackfragment.tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadArticleImage()

        binding.showFeedbackFragmentBtn.setOnClickListener {
            if (isFeedbackFragmentOpened) {
                binding.showFeedbackFragmentBtn.text = getString(R.string.open)
                hideFeedbackFragment()
            } else {
                binding.showFeedbackFragmentBtn.text = getString(R.string.close)
                showFeedbackFragment()
            }

            isFeedbackFragmentOpened = !isFeedbackFragmentOpened
        }

    }

    private fun showFeedbackFragment() {
        val feedbackFragment = FeedbackFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, feedbackFragment, feedbackFragmentTag).commit()
    }

    @SuppressLint("CommitTransaction")
    private fun hideFeedbackFragment() {
        val fragment = supportFragmentManager.findFragmentByTag(feedbackFragmentTag)
        if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    private fun loadArticleImage() {
        try {
            // get input stream
            val ims = assets.open("dune_book.jpg")
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            binding.articleIconIv.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
            return
        }
    }

}