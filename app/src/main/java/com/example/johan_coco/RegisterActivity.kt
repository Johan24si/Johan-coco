package com.example.johan_coco

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.johan_coco.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Back Button
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Setup DatePicker
        binding.etTanggalLahir.setOnClickListener {
            binding.tilTanggalLahir.error = null
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etTanggalLahir.setText(date)
            }, year, month, day)
            datePickerDialog.show()
        }

        // Setup Dropdown Agama
        val daftarAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        val adapterAgama = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, daftarAgama)
        binding.actvAgama.setAdapter(adapterAgama)
        binding.actvAgama.setOnItemClickListener { _, _, _, _ ->
            binding.tilAgama.error = null
        }

        binding.rgGender.setOnCheckedChangeListener { _, _ ->
            binding.tvErrorGender.visibility = View.GONE
        }

        binding.btnRegister.setOnClickListener {
            // Reset Errors
            binding.tilNama.error = null
            binding.tilTanggalLahir.error = null
            binding.tvErrorGender.visibility = View.GONE
            binding.tilAgama.error = null
            binding.tilUsername.error = null
            binding.tilPassword.error = null
            binding.tilConfirmPassword.error = null

            val nama = binding.etNama.text.toString().trim()
            val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
            val selectedGenderId = binding.rgGender.checkedRadioButtonId
            val agama = binding.actvAgama.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            var isValid = true

            if (nama.isEmpty()) {
                binding.tilNama.error = "Nama tidak boleh kosong"
                isValid = false
            }

            if (tanggalLahir.isEmpty()) {
                binding.tilTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
                isValid = false
            }

            if (selectedGenderId == -1) {
                binding.tvErrorGender.visibility = View.VISIBLE
                isValid = false
            }

            if (agama.isEmpty()) {
                binding.tilAgama.error = "Agama harus dipilih"
                isValid = false
            }

            if (username.isEmpty()) {
                binding.tilUsername.error = "Username tidak boleh kosong"
                isValid = false
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                isValid = false
            }

            if (confirmPassword.isEmpty()) {
                binding.tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                isValid = false
            } else if (password != confirmPassword) {
                binding.tilConfirmPassword.error = "Password tidak cocok"
                isValid = false
            }

            if (isValid) {
                val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

                // Simpan ke SharedPreferences
                val sharedPref = getSharedPreferences("BinaDesaPref", MODE_PRIVATE)
                sharedPref.edit {
                    putString("USER_NAME", nama)
                    putString("USER_EMAIL", username)
                    putString("USER_PASSWORD", password)
                }

                Toast.makeText(this, "Registrasi Berhasil untuk $username", Toast.LENGTH_LONG).show()
                finish() // Kembali ke Login
            }
        }
    }
}