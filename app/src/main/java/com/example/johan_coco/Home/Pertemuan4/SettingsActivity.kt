package com.example.johan_coco.Home.Pertemuan4

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

        // Set Toolbar
        // Perbaikan: Menggunakan 'toolbarSettings' sesuai dengan ID di activity_settings.xml
        setSupportActionBar(binding.toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarSettings.setNavigationOnClickListener { finish() }

        // Data for ListView
        val menuItems = arrayOf(
            "Tentang Aplikasi",
            "Kebijakan Privasi",
            "Syarat dan Ketentuan",
            "Bantuan",
            "Versi Aplikasi v1.0"
        )

        // Adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuItems)
        binding.listViewSettings.adapter = adapter

        // Item Click Listener
        binding.listViewSettings.setOnItemClickListener { _, _, position, _ ->
            val item = menuItems[position]
            Toast.makeText(this, "Membuka: $item", Toast.LENGTH_SHORT).show()
        }
    }
}