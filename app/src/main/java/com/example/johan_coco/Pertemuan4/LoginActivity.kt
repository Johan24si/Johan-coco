package com.example.johan_coco.Pertemuan4

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

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonSubmit.setOnClickListener {

            val nama = binding.inputNama.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            // VALIDASI
            if (nama.isEmpty()) {
                binding.inputNama.error = "Username tidak boleh kosong"
                binding.inputNama.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.inputPassword.error = "Password tidak boleh kosong"
                binding.inputPassword.requestFocus()
                return@setOnClickListener
            }

            // LOGIN BERHASIL
            Toast.makeText(this, "Welcome, $nama!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("USER_NAME", nama)
            startActivity(intent)

            // 🔥 penting supaya tidak balik lagi ke login saat tekan back
            finish()
        }
    }
}