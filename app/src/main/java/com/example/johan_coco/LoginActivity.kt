package com.example.johan_coco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.johan_coco.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- NAVIGASI KE REGISTER (Sign Up) ---
        binding.tabSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.textDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // --- TOMBOL SIGN IN ---
        binding.buttonSignIn.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            // Validasi Sederhana
            if (email.isEmpty()) {
                binding.layoutEmail.error = "Masukkan email Anda"
                return@setOnClickListener
            }
            binding.layoutEmail.error = null

            if (password.isEmpty()) {
                binding.layoutPassword.error = "Masukkan password Anda"
                return@setOnClickListener
            }
            binding.layoutPassword.error = null

            // Simpan status login di SharedPreferences
            val sharedPref = getSharedPreferences("BinaDesaPref", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isLogin", true)
            editor.putString("USER_EMAIL", email)
            editor.putBoolean("rememberMe", binding.checkRemember.isChecked)
            editor.apply()

            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

            // Pindah ke Halaman Utama (BaseActivity)
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        // --- FORGOT PASSWORD ---
        binding.textForgotPassword.setOnClickListener {
            Toast.makeText(this, "Fitur Lupa Password akan segera hadir", Toast.LENGTH_SHORT).show()
        }

        // --- TOMBOL GOOGLE & FACEBOOK ---
        binding.buttonGoogle.setOnClickListener {
            Toast.makeText(this, "Menghubungkan ke Google...", Toast.LENGTH_SHORT).show()
        }

        binding.buttonFacebook.setOnClickListener {
            Toast.makeText(this, "Menghubungkan ke Facebook...", Toast.LENGTH_SHORT).show()
        }

        // --- LOAD DATA REMEMBER ME ---
        val sharedPref = getSharedPreferences("BinaDesaPref", MODE_PRIVATE)
        if (sharedPref.getBoolean("rememberMe", false)) {
            val savedEmail = sharedPref.getString("USER_EMAIL", "")
            binding.inputEmail.setText(savedEmail)
            binding.checkRemember.isChecked = true
        }
    }
}