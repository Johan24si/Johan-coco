package com.example.johan_coco.Home.Pertemuan4

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.johan_coco.databinding.ActivityBangunRuangBinding
import kotlin.math.PI
import kotlin.math.pow

class BangunRuangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBangunRuangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBangunRuangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // Spinner
        val listBangun = arrayOf("Kubus", "Bola", "Tabung")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, listBangun)
        binding.spinnerBangun.adapter = adapter

        // Klik tombol hitung
        binding.btnHitung.setOnClickListener {
            val sisi = binding.etSisi.text.toString().toDoubleOrNull()
            val tinggi = binding.etTinggi.text.toString().toDoubleOrNull()
            val pilihan = binding.spinnerBangun.selectedItem.toString()

            if (sisi == null) {
                Toast.makeText(this, "Masukkan angka yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hasil = when (pilihan) {
                "Kubus" -> {
                    binding.tvContent.text = "Rumus: V = s³"
                    sisi.pow(3)
                }
                "Bola" -> {
                    binding.tvContent.text = "Rumus: V = 4/3 π r³"
                    (4.0 / 3.0) * PI * sisi.pow(3)
                }
                "Tabung" -> {
                    if (tinggi == null) {
                        Toast.makeText(this, "Tinggi harus diisi", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    binding.tvContent.text = "Rumus: V = π r² t"
                    PI * sisi.pow(2) * tinggi
                }
                else -> 0.0
            }

            binding.tvHasil.text = "Hasil Volume:\n%.2f".format(hasil)
        }
    }
}