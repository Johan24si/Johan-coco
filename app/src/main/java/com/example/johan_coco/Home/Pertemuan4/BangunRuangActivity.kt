package com.example.johan_coco.Home.Pertemuan4

import android.os.Bundle
import android.view.View
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

        // Chip selection listener to toggle visibility of fields and update rumus
        binding.chipGroupBangun.setOnCheckedStateChangeListener { _, checkedIds ->
            when (checkedIds.firstOrNull()) {
                binding.chipKubus.id -> {
                    binding.tilTinggi.visibility = View.GONE
                    binding.tvContent.text = "Rumus: V = s³"
                    binding.tilSisi.hint = "Masukkan sisi"
                }
                binding.chipBola.id -> {
                    binding.tilTinggi.visibility = View.GONE
                    binding.tvContent.text = "Rumus: V = 4/3 π r³"
                    binding.tilSisi.hint = "Masukkan jari-jari"
                }
                binding.chipTabung.id -> {
                    binding.tilTinggi.visibility = View.VISIBLE
                    binding.tvContent.text = "Rumus: V = π r² t"
                    binding.tilSisi.hint = "Masukkan jari-jari"
                }
            }
        }

        // Klik tombol hitung
        binding.btnHitung.setOnClickListener {
            val sisi = binding.etSisi.text.toString().toDoubleOrNull()
            val tinggi = binding.etTinggi.text.toString().toDoubleOrNull()
            
            val selectedChipId = binding.chipGroupBangun.checkedChipId

            if (sisi == null) {
                binding.tilSisi.error = "Masukkan angka yang valid"
                return@setOnClickListener
            } else {
                binding.tilSisi.error = null
            }

            val hasil = when (selectedChipId) {
                binding.chipKubus.id -> {
                    sisi.pow(3)
                }
                binding.chipBola.id -> {
                    (4.0 / 3.0) * PI * sisi.pow(3)
                }
                binding.chipTabung.id -> {
                    if (tinggi == null) {
                        binding.tilTinggi.error = "Tinggi harus diisi"
                        return@setOnClickListener
                    } else {
                        binding.tilTinggi.error = null
                    }
                    PI * sisi.pow(2) * tinggi
                }
                else -> 0.0
            }

            binding.tvHasil.text = "Hasil Volume:\n%.2f".format(hasil)
        }
    }
}