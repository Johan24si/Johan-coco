package com.example.johan_coco

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.johan_coco.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarSettings.setNavigationOnClickListener { onBackPressed() }

        val settingsOptions = arrayOf("Privacy Policy", "About Us", "Contact Support", "Terms of Service")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, settingsOptions)
        
        binding.listViewSettings.adapter = adapter

        binding.listViewSettings.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = settingsOptions[position]
            Toast.makeText(this, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }
}