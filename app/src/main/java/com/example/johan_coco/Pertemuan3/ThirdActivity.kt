package com.example.johan_coco.Pertemuan3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.johan_coco.R
import com.example.johan_coco.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi View Binding
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonSubmit.setOnClickListener {
            // Mengambil value dari inputNama (NIM/Username)
            val nama = binding.inputNama.text.toString()
            val password = binding.inputPassword.text.toString()

            if (nama.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Welcome, $nama!", Toast.LENGTH_SHORT).show()

                // Berpindah ke activity hasil
                val intent = Intent(this, ThirdResultActivity::class.java)
                intent.putExtra("USER_NAME", nama) // Opsional: kirim data ke halaman berikutnya
                startActivity(intent)
            } else {
                Toast.makeText(this, "Harap isi username dan password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}