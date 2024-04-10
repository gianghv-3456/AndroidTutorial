package com.example.dialognpicker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dialognpicker.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alertOpenBtn.setOnClickListener {
            println("SHOW DIALOG")
            Log.d("HAHA", "show dialog")
            openAlertDialog()
        }

        binding.datePickerBtn.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)
            val dateStr = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD).format(date)
            Toast.makeText(this, "selected: $dateStr", Toast.LENGTH_SHORT).show()
        }
        datePicker.show(supportFragmentManager, "datepickermain")

    }

    private fun openAlertDialog() {
        println("SHOW DIALOG")
        val builder = AlertDialog.Builder(this)
        val alertDialog =
            builder.setTitle("Alert").setMessage("Click OK to continue or Cancel to stop.")
                .setCancelable(true)

                .setPositiveButton("OK") { dialog, _ ->
                    Toast.makeText(this, "OK pressed!", Toast.LENGTH_SHORT).show()
                    dialog.cancel()

                }.setNegativeButton("Cancel") { dialog, _ ->
                    Toast.makeText(this, "Cancel pressed!", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }.create()

        alertDialog.show()

    }

}