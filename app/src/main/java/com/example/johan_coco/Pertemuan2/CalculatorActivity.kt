package com.example.johan_coco.Pertemuan2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.johan_coco.R

class CalculatorActivity : AppCompatActivity() {

    lateinit var etAlas: EditText
    lateinit var etTinggi: EditText
    lateinit var etSisi: EditText
    lateinit var btnSegitiga: Button
    lateinit var btnKubus: Button
    lateinit var tvHasil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)

        // Handle padding (edge to edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi komponen
        etAlas = findViewById(R.id.etAlas)
        etTinggi = findViewById(R.id.etTinggi)
        etSisi = findViewById(R.id.etSisi)
        btnSegitiga = findViewById(R.id.btnSegitiga)
        btnKubus = findViewById(R.id.btnKubus)
        tvHasil = findViewById(R.id.tvHasil)

        // 🔺 Hitung Luas Segitiga
        btnSegitiga.setOnClickListener {

            if (etAlas.text.isEmpty()) {
                etAlas.error = "Isi alas dulu!"
                return@setOnClickListener
            }

            if (etTinggi.text.isEmpty()) {
                etTinggi.error = "Isi tinggi dulu!"
                return@setOnClickListener
            }

            val alas = etAlas.text.toString().toDouble()
            val tinggi = etTinggi.text.toString().toDouble()

            val hasil = 0.5 * alas * tinggi

            tvHasil.text = "Luas Segitiga: $hasil"

            Log.d("HASIL", "Segitiga: $hasil")
        }

        // 🧊 Hitung Volume Kubus
        btnKubus.setOnClickListener {

            if (etSisi.text.isEmpty()) {
                etSisi.error = "Isi sisi dulu!"
                return@setOnClickListener
            }

            val sisi = etSisi.text.toString().toDouble()

            val hasil = sisi * sisi * sisi

            tvHasil.text = "Volume Kubus: $hasil"

            Log.d("HASIL", "Kubus: $hasil")
        }
    }
}