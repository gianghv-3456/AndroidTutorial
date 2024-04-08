package com.example.songdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.songdetail.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadArticleImage()

        val bookList = listOf("Dune 1", "Dune 2", "Dune 3", "Dune 4")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bookList)
        binding.bookListView.adapter = adapter

        binding.bookListView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> openDetailScreen("Dune 1", getString(R.string.dune_1_detail))
                1 -> openDetailScreen("Dune 2", getString(R.string.dune_2_content))
                2 -> openDetailScreen("Dune 3", "Haven't written yet!!")
                3 -> openDetailScreen("Dune 4", "Haven't written yet!!")
            }
        }
    }

    private fun openDetailScreen(bookName: String, bookDetail: String) {
        val fragment = BookDetailFragment.newInstance(bookName, bookDetail)
        supportFragmentManager.beginTransaction().add(R.id.detail_fragment_container, fragment)
            .commit()
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