package com.example.driodcafe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.driodcafe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val itemList = mutableListOf<Dessert>()
    private val cart = mutableListOf<Dessert>()
    private val cartLiveData = MutableLiveData(cart)

    /**
     * extension function make dessert list live data listen to list's addition
     */
    private fun MutableList<Dessert>.addElement(x: Dessert) {
        this.add(x)
        cartLiveData.value = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFakeData()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDessertList()
        addDessertListClickEvent()
        observeCartChange()
        addFloatingButtonClickEvent()

    }

    private fun addFloatingButtonClickEvent() {
        binding.fab.setOnClickListener{
            openOrderActivity()
        }
    }

    private fun observeCartChange() {
        cartLiveData.observe(this) {
            binding.badgeFabBtn.text = cart.size.toString()
        }
    }

    private fun addDessertListClickEvent() {
        binding.dessertListLv.setOnItemClickListener { _, _, position, _ ->
            val e = itemList[position]
            if (cart.contains(e))
                return@setOnItemClickListener

            cartLiveData.value?.addElement(itemList[position])

            Toast.makeText(
                this, "You ordered a(an) ${itemList[position].name}.", Toast.LENGTH_SHORT
            ).show()
            return@setOnItemClickListener
        }
    }

    private fun showDessertList() {
        val adapter = DessertListAdapter(itemList)
        binding.dessertListLv.adapter = adapter

    }

    private fun openOrderActivity() {

    }

    /**
     * Fake data for dessert list
     */
    private fun initFakeData() {
        itemList.add(
            Dessert(
                "Donut", "Donuts are glazed and sprinkled with candy", R.drawable.donut_circle
            )
        )
        itemList.add(
            Dessert(
                "Ice Cream Sandwich",
                "Ice Cream Sandwiches have chocolate wafers and vanilla filling",
                R.drawable.icecream_circle
            )
        )
        itemList.add(
            Dessert(
                "FroYo", "FroYo is premium self-serve frozen yogurt", R.drawable.froyo_circle
            )
        )
    }

}