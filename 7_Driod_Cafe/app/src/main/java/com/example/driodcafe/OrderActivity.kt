package com.example.driodcafe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.driodcafe.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderedDessertId = intent.extras?.getStringArray(MainActivity.ORDER_CART_BUNDLE_KEY)
        if (!orderedDessertId.isNullOrEmpty()) {
            var screenLabelStr = "You have ordered "

            orderedDessertId.forEach {
                val dessertId = DessertId.valueOf(it)
                val dessertName = when (dessertId) {
                    DessertId.DONUT -> "Donut"
                    DessertId.ICE_CREAM -> "Ice cream"
                    DessertId.FROYO -> "FroYo"
                }
                screenLabelStr += "$dessertName, "
            }
            binding.orderScreenTitleTv.text = screenLabelStr
        } else {
            binding.orderScreenTitleTv.text = "You order nothing! :(("
        }

    }
}