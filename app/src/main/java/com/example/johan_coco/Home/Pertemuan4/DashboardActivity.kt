package com.example.johan_coco.Home.Pertemuan4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.johan_coco.Home.Pertemuan5.WebViewActivity
import com.example.johan_coco.LoginActivity
import com.example.johan_coco.databinding.ActivityDashboardBinding
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar with Back Button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Ambil nama dari SharedPreferences
        val sharedPref = getSharedPreferences("BinaDesaPref", MODE_PRIVATE)
        val namaUser = sharedPref.getString("USER_NAME", "User")
        binding.tvStudentName.text = namaUser

        // Navigasi ke Bangun Ruang
        binding.btnBangunRuang.setOnClickListener {
            startActivity(Intent(this, BangunRuangActivity::class.java))
        }

        // Navigasi ke Custom 1
        binding.btnCustom1.setOnClickListener {
            startActivity(Intent(this, Custom1Activity::class.java))
        }

        // Navigasi ke Custom 2
        binding.btnCustom2.setOnClickListener {
            startActivity(Intent(this, Custom2Activity::class.java))
        }

        // Navigasi ke WebView
        binding.btnWebView.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah kamu yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}