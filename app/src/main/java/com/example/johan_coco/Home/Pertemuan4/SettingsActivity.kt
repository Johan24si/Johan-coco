package com.example.johan_coco.Home.Pertemuan4

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.johan_coco.ReminderReceiver
import com.example.johan_coco.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    // Launcher for Notification Permission (Android 13+)
    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin notifikasi ditolak. Pengingat mungkin tidak muncul.", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar
        setSupportActionBar(binding.toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarSettings.setNavigationOnClickListener { finish() }

        checkNotificationPermission()
        setupReminder()
        setupOtherMenus()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupReminder() {
        binding.btnSetReminder.setOnClickListener {
            val minutesStr = binding.etReminderMinutes.text.toString()
            if (minutesStr.isNotEmpty()) {
                val minutes = minutesStr.toLong()
                setReminder(minutes)
            } else {
                Toast.makeText(this, "Silakan masukkan jumlah menit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setReminder(minutes: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        // Check for exact alarm permission on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Harap izinkan alarm tepat di pengaturan", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
                return
            }
        }

        val intent = Intent(this, ReminderReceiver::class.java).apply {
            putExtra("TITLE", "Waktunya Bina Desa!")
            putExtra("MESSAGE", "Ayo cek perkembangan program bina desa sekarang. Masyarakat menunggumu!")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            100,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val triggerTime = System.currentTimeMillis() + (minutes * 60 * 1000)

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
            Toast.makeText(this, "Pengingat dipasang untuk $minutes menit lagi", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            Toast.makeText(this, "Gagal memasang pengingat: Izin ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupOtherMenus() {
        val menuItems = arrayOf(
            "Tentang Aplikasi",
            "Kebijakan Privasi",
            "Syarat dan Ketentuan",
            "Bantuan",
            "Versi Aplikasi v1.0"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuItems)
        binding.listViewSettings.adapter = adapter

        binding.listViewSettings.setOnItemClickListener { _, _, position, _ ->
            val item = menuItems[position]
            Toast.makeText(this, "Membuka: $item", Toast.LENGTH_SHORT).show()
        }
    }
}