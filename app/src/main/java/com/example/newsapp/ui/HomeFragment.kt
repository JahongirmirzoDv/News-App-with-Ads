package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.adapters.HorizontalRvAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.retrofit.ApiClient
import com.example.newsapp.retrofit.ApiHelperImpl
import com.example.newsapp.utils.ViewModelFactory
import com.example.newsapp.viewmodels.ApiControlViewmodel
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

        viewModel.getNews(1, "").observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: ${it.articles.size}")
            binding.topRvProgress.visibility = View.GONE
            horizontalRvAdapter = HorizontalRvAdapter(it.articles)
            binding.rv.adapter = horizontalRvAdapter

        }
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
                tab?.customView?.findViewById<TextView>(R.id.tab_n)?.setTextColor(Color.GRAY)
                tab?.customView?.findViewById<LinearLayout>(R.id.bg)?.setBackgroundColor(R.color.white)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })



        return binding.root
    }

    private fun setupViewmodel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(ApiClient.apiService)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}