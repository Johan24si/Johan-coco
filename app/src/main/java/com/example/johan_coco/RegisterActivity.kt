package com.example.johan_coco

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tilNama = findViewById<TextInputLayout>(R.id.tilNama)
        val etNama = findViewById<TextInputEditText>(R.id.etNama)
        val tilTanggalLahir = findViewById<TextInputLayout>(R.id.tilTanggalLahir)
        val etTanggalLahir = findViewById<TextInputEditText>(R.id.etTanggalLahir)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val tvErrorGender = findViewById<TextView>(R.id.tvErrorGender)
        val tilAgama = findViewById<TextInputLayout>(R.id.tilAgama)
        val actvAgama = findViewById<AutoCompleteTextView>(R.id.actvAgama)
        val tilUsername = findViewById<TextInputLayout>(R.id.tilUsername)
        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        // Setup Back Button
        btnBack.setOnClickListener {
            finish()
        }

        // Setup DatePicker
        etTanggalLahir.setOnClickListener {
            tilTanggalLahir.error = null
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etTanggalLahir.setText(date)
            }, year, month, day)
            datePickerDialog.show()
        }

        // Setup Dropdown Agama
        val daftarAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        val adapterAgama = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, daftarAgama)
        actvAgama.setAdapter(adapterAgama)
        actvAgama.setOnItemClickListener { _, _, _, _ ->
            tilAgama.error = null
        }

        rgGender.setOnCheckedChangeListener { _, _ ->
            tvErrorGender.visibility = View.GONE
        }

        btnRegister.setOnClickListener {
            // Reset Errors
            tilNama.error = null
            tilTanggalLahir.error = null
            tvErrorGender.visibility = View.GONE
            tilAgama.error = null
            tilUsername.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null

            val nama = etNama.text.toString().trim()
            val tanggalLahir = etTanggalLahir.text.toString().trim()
            val selectedGenderId = rgGender.checkedRadioButtonId
            val agama = actvAgama.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            var isValid = true

            if (nama.isEmpty()) {
                tilNama.error = "Nama tidak boleh kosong"
                isValid = false
            }

            if (tanggalLahir.isEmpty()) {
                tilTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
                isValid = false
            }

            if (selectedGenderId == -1) {
                tvErrorGender.visibility = View.VISIBLE
                isValid = false
            }

            if (agama.isEmpty()) {
                tilAgama.error = "Agama harus dipilih"
                isValid = false
            }

            if (username.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                isValid = false
            }

            if (password.isEmpty()) {
                tilPassword.error = "Password tidak boleh kosong"
                isValid = false
            }

            if (confirmPassword.isEmpty()) {
                tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                isValid = false
            } else if (password != confirmPassword) {
                tilConfirmPassword.error = "Password tidak cocok"
                isValid = false
            }

            if (isValid) {
                val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

                // Simpan ke SharedPreferences menggunakan KTX extension
                val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                sharedPref.edit {
                    putString("nama", nama)
                    putString("tanggal_lahir", tanggalLahir)
                    putString("gender", gender)
                    putString("agama", agama)
                    putString("username", username)
                    putString("password", password)
                }

                Toast.makeText(this, "Registrasi Berhasil untuk $username", Toast.LENGTH_LONG).show()
                finish() // Kembali ke Login
            }
        }
    }
}
