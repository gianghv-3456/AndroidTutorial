package com.example.driodcafe

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable

class DessertListAdapter(private val list: List<Dessert>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v = View.inflate(parent?.context, R.layout.dessert_list_item, null)
        val iconImageView = v.findViewById<ImageView>(R.id.dessert_icon_iv)
        iconImageView.setImageDrawable(getDrawable(parent?.context!!, list[position].iconDrawable))
        val descriptionTextView = v.findViewById<TextView>(R.id.dessert_description_tv)
        descriptionTextView.text = list[position].description
        Log.d("HAHA LIST", "item: ${list[position].name}")

        return v
    }
}