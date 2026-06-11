package com.example.johan_coco.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.johan_coco.Home.Pertemuan2.CalculatorActivity
import com.example.johan_coco.Home.Pertemuan3.ThirdActivity
import com.example.johan_coco.Home.Pertemuan4.DashboardActivity
import com.example.johan_coco.Home.Pertemuan4.SettingsActivity
import com.example.johan_coco.Home.Pertemuan5.WebViewActivity
import com.example.johan_coco.Home.Petemuan6.SplashActivity
import com.example.johan_coco.Home.pertemuan_10.TenthActivity
import com.example.johan_coco.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupChips()
        setupMenuButtons()
        setupNewsList()
        fetchNews()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"
    }

    private fun setupChips() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.chipAll.id -> fetchNews()
                binding.chipRecent.id -> Toast.makeText(context, "Recent Filtered", Toast.LENGTH_SHORT).show()
                binding.chipFavorite.id -> Toast.makeText(context, "Favorite Filtered", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupMenuButtons() {
        binding.btnToSecond.setOnClickListener {
            startActivity(Intent(requireContext(), CalculatorActivity::class.java))
        }
        binding.btnToThird.setOnClickListener {
            startActivity(Intent(requireContext(), ThirdActivity::class.java))
        }
        binding.btnToFourth.setOnClickListener {
            startActivity(Intent(requireContext(), DashboardActivity::class.java))
        }
        binding.btnToFifth.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }
        binding.btnToSixth.setOnClickListener {
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }
        binding.btnToTenth.setOnClickListener {
            startActivity(Intent(requireContext(), TenthActivity::class.java))
        }
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
    }

    private fun setupNewsList() {
        newsAdapter = NewsAdapter(emptyList<Post>())
        binding.rvNews.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvNews.adapter = newsAdapter
        binding.rvNews.isNestedScrollingEnabled = false
    }

    private fun fetchNews() {
        binding.progressBarNews.visibility = View.VISIBLE
        binding.tvNewsError.visibility = View.GONE

        NewsService.create().getNews().enqueue(object : Callback<String> {

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (!isAdded) return

                binding.progressBarNews.visibility = View.GONE

                android.util.Log.d(
                    "NEWS_DEBUG",
                    "Response Code: ${response.code()}"
                )

                if (response.isSuccessful) {

                    val xml = response.body() ?: ""

                    android.util.Log.d(
                        "NEWS_DEBUG",
                        xml.take(500)
                    )

                    val posts = RssParser.parse(xml)

                    android.util.Log.d(
                        "NEWS_DEBUG",
                        "Jumlah berita: ${posts.size}"
                    )

                    if (posts.isEmpty()) {
                        binding.tvNewsError.visibility = View.VISIBLE
                        binding.tvNewsError.text =
                            "RSS berhasil dimuat tetapi tidak ada berita."
                    } else {
                        newsAdapter.updateData(posts)
                    }

                } else {

                    binding.tvNewsError.visibility = View.VISIBLE
                    binding.tvNewsError.text =
                        "Gagal memuat berita (${response.code()})"

                    android.util.Log.e(
                        "NEWS_DEBUG",
                        response.errorBody()?.string() ?: "Unknown Error"
                    )
                }
            }

            override fun onFailure(
                call: Call<String>,
                t: Throwable
            ) {
                if (!isAdded) return

                binding.progressBarNews.visibility = View.GONE
                binding.tvNewsError.visibility = View.VISIBLE

                binding.tvNewsError.text =
                    "Koneksi gagal: ${t.message}"

                android.util.Log.e(
                    "NEWS_DEBUG",
                    "Failure",
                    t
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}