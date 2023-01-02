package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.HorizontalRvAdapter
import com.example.newsapp.adapters.SnapHelperOneByOne
import com.example.newsapp.adapters.ViewPagerAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.retrofit.ApiClient
import com.example.newsapp.retrofit.ApiClient2
import com.example.newsapp.retrofit.ApiHelperImpl
import com.example.newsapp.utils.ViewModelFactory
import com.example.newsapp.viewmodels.ApiControlViewmodel
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: ApiControlViewmodel
    lateinit var horizontalRvAdapter: HorizontalRvAdapter
    private val TAG = "HomeFragment"
    private var titleList = listOf(
        "World",
        "Health",
        "Sport",
        "Finance",
        "Art",
        "Technology"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        setupViewmodel()
        viewModel.getname().observe(viewLifecycleOwner){
            Log.e(TAG, "onCreateView: ${it.results.size}")
        }

        viewModel.getNews(1, "").observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: ${it.articles.size}")
            binding.topRvProgress.visibility = View.GONE
            var n= PagerSnapHelper()
            horizontalRvAdapter = HorizontalRvAdapter(it.articles)
            binding.rv.adapter = horizontalRvAdapter
            n.attachToRecyclerView(binding.rv)
            var mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            val findSnapView = n.findSnapView(mLayoutManager)

            findSnapView?.alpha = 0.2f
        }
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, titleList)
        binding.viewpager2.adapter = viewPagerAdapter
        binding.tablayout.setupWithViewPager(binding.viewpager2)

        setTabs()
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tab_n)?.setTextColor(Color.WHITE)
                tab?.customView?.findViewById<LinearLayout>(R.id.bg)
                    ?.setBackgroundResource(R.drawable.gradient)
            }

            @SuppressLint("ResourceAsColor")
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tab_n)?.setTextColor(Color.BLACK)
                tab?.customView?.findViewById<LinearLayout>(R.id.bg)
                    ?.setBackgroundResource(R.drawable.gradient2)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.notification.setOnClickListener {
            findNavController().navigate(R.id.blankFragment)
        }




        return binding.root
    }

        private fun setupViewmodel() {
            viewModel = ViewModelProvider(
                this,
                ViewModelFactory(
                    ApiHelperImpl(ApiClient.apiService,ApiClient2.apiService)
                )
            )[ApiControlViewmodel::class.java]
        }

    @SuppressLint("ResourceAsColor")
    private fun setTabs() {
        val tabCount = binding.tablayout.tabCount
        for (i in 0 until tabCount) {
            val tabview =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_item, null)
            val tab = binding.tablayout.getTabAt(i)
            tab?.customView = tabview
            binding.tablayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tab_n)
                ?.setTextColor(Color.WHITE)
            binding.tablayout.getTabAt(0)?.customView?.findViewById<LinearLayout>(R.id.bg)
                ?.setBackgroundResource(R.drawable.gradient)

            tabview.findViewById<TextView>(R.id.tab_n).text = titleList[i]
        }
    }
}