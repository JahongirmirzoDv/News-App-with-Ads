package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.InnerRvAdapter
import com.example.newsapp.adapters.ViewPagerAdapter
import com.example.newsapp.databinding.FragmentViewBinding
import com.example.newsapp.models.Article
import com.example.newsapp.retrofit.ApiClient
import com.example.newsapp.retrofit.ApiHelper
import com.example.newsapp.retrofit.ApiHelperImpl
import com.example.newsapp.utils.PaginationScrollListener
import com.example.newsapp.utils.ViewModelFactory
import com.example.newsapp.viewmodels.ApiControlViewmodel
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    lateinit var binding: FragmentViewBinding
    lateinit var viewModel: ApiControlViewmodel
    lateinit var paginationAdapter: InnerRvAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var apiHelperImpl: ApiHelper
    private val TAG = "ViewFragment"

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var TOTAL_PAGES = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewBinding.inflate(inflater, container, false)
        setupViewmodel()
        linearLayoutManager = LinearLayoutManager(requireContext())
        paginationAdapter = InnerRvAdapter(requireContext())
        apiHelperImpl = ApiHelperImpl(ApiClient.apiService)
        param1?.let {

            binding.innerRv.addOnScrollListener(object :
                PaginationScrollListener(linearLayoutManager) {
                override fun loadMoreItems() {
                    isLoading = true
                    currentPage++

                    param1?.let { loadNextPage(currentPage++, it) }
                }

                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }
            })
            binding.innerRv.layoutManager = linearLayoutManager
            binding.innerRv.adapter = paginationAdapter
            paginationAdapter.onpress = object : InnerRvAdapter.onPress {
                override fun onclick(photo: Article) {

                }
            }
            param1?.let { loadFirstPage(it) }
        }


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

    @OptIn(DelicateCoroutinesApi::class)
    fun loadFirstPage(type: String) {
        Log.e(TAG, "loadFirstPage: function_1")
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                try {
                    val page = apiHelperImpl.getNews(1, type)
                    binding.progress2.visibility = View.GONE
                    paginationAdapter.addAll(page.articles)

                    if (currentPage <= TOTAL_PAGES) {
                        paginationAdapter.editLoading()
                    } else {
                        isLastPage = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun loadNextPage(page: Int, type: String) {
        Log.e(TAG, "loadFirstPage: function_2")
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                try {
                    val page = apiHelperImpl.getNews(page, type)
                    paginationAdapter.removeLoading()
                    isLoading = false

                    paginationAdapter.addAll(page.articles)

                    if (currentPage <= TOTAL_PAGES) {
                        paginationAdapter.editLoading()
                    } else {
                        isLastPage = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}