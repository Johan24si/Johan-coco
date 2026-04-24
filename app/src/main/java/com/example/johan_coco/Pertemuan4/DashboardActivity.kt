package com.example.johan_coco.Pertemuan4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.johan_coco.databinding.ActivityDashboardBinding
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val namaUser = intent.getStringExtra("USER_NAME") ?: "Mahasiswa"
        binding.tvStudentName.text = "$namaUser (Student ID: 123456)"

        val title = "Dashboard Mahasiswa"
        val desc = "Halaman utama aplikasi mahasiswa"

        // Tombol 1
        binding.btnBangunRuang.setOnClickListener {
            val intent = Intent(this, BangunRuangActivity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }

        // Tombol 2
        binding.btnCustom1.setOnClickListener {
            val intent = Intent(this, Custom1Activity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }

        // Tombol 3
        binding.btnCustom2.setOnClickListener {
            val intent = Intent(this, Custom2Activity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah kamu yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}