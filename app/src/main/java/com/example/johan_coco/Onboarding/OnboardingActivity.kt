package com.example.johan_coco.Onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.johan_coco.LoginActivity
import com.example.johan_coco.R
import com.example.johan_coco.databinding.ActivityOnboardingBinding
import kotlin.math.abs

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Fullscreen & Transparent Status Bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        
        // Mengatur ikon Status Bar menjadi gelap
        WindowInsetsControllerCompat(window, binding.root).isAppearanceLightStatusBars = true

        // Menangani Window Insets agar layout tidak tertutup sistem navigasi
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)

        // Penanganan tombol Back perangkat
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.viewPagerOnboarding.currentItem > 0) {
                    binding.viewPagerOnboarding.setCurrentItem(binding.viewPagerOnboarding.currentItem - 1, true)
                } else {
                    finish()
                }
            }
        })

        // Animasi transisi halaman
        binding.viewPagerOnboarding.setPageTransformer { page, position ->
            page.apply {
                val absPos = abs(position)
                alpha = 1 - absPos
                scaleY = 0.85f + (1 - absPos) * 0.15f
                scaleX = 0.85f + (1 - absPos) * 0.15f
            }
        }

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (position == onboardingAdapter.itemCount - 1) {
                    binding.btnNext.text = getString(R.string.btn_get_started)
                    binding.btnSkip.visibility = View.INVISIBLE
                } else {
                    binding.btnNext.text = getString(R.string.btn_next)
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.viewPagerOnboarding.currentItem + 1 < onboardingAdapter.itemCount) {
                binding.viewPagerOnboarding.setCurrentItem(binding.viewPagerOnboarding.currentItem + 1, true)
            } else {
                navigateToLogin()
            }
        }

        binding.btnSkip.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun setOnboardingItems() {
        val onboardingItems = listOf(
            OnboardingItem(
                getString(R.string.onboarding_title_1),
                getString(R.string.onboarding_desc_1),
                R.drawable.logoo
            ),
            OnboardingItem(
                getString(R.string.onboarding_title_2),
                getString(R.string.onboarding_desc_2),
                R.drawable.suitcase
            ),
            OnboardingItem(
                getString(R.string.onboarding_title_3),
                getString(R.string.onboarding_desc_3),
                R.drawable.global
            )
        )

        onboardingAdapter = OnboardingAdapter(onboardingItems)
        binding.viewPagerOnboarding.adapter = onboardingAdapter
    }

    private fun setupIndicators() {
        binding.layoutIndicators.removeAllViews()
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(12, 0, 12, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.apply {
                setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.tab_unselected_indicator))
                this.layoutParams = layoutParams
                binding.layoutIndicators.addView(this)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = binding.layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.get(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.tab_selected_indicator))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.tab_unselected_indicator))
            }
        }
    }

    private fun navigateToLogin() {
        val sharedPref = getSharedPreferences("BinaDesaPref", MODE_PRIVATE)
        sharedPref.edit().putBoolean("onboardingFinished", true).apply()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }
}