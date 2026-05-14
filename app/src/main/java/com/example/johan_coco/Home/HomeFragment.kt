package com.example.johan_coco.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.johan_coco.Home.Pertemuan2.CalculatorActivity
import com.example.johan_coco.Home.Pertemuan3.ThirdActivity
import com.example.johan_coco.Home.Pertemuan4.DashboardActivity
import com.example.johan_coco.Home.Pertemuan5.WebViewActivity
import com.example.johan_coco.Home.Petemuan6.SplashActivity
import com.example.johan_coco.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"

        // Pertemuan 2 - Calculator
        binding.btnToSecond.setOnClickListener {
            startActivity(Intent(requireContext(), CalculatorActivity::class.java))
        }

        // Pertemuan 3 - Third Activity
        binding.btnToThird.setOnClickListener {
            startActivity(Intent(requireContext(), ThirdActivity::class.java))
        }

        // Pertemuan 4 - Dashboard
        binding.btnToFourth.setOnClickListener {
            startActivity(Intent(requireContext(), DashboardActivity::class.java))
        }

        // Pertemuan 5 - Web View
        binding.btnToFifth.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        // Pertemuan 6 - Splash/Main6
        binding.btnToSixth.setOnClickListener {
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}