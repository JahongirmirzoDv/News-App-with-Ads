package com.example.newsapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemLoadBinding
import com.example.newsapp.databinding.PagingRvItemBinding
import com.example.newsapp.models.Article
import com.squareup.picasso.Picasso

class InnerRvAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 0
    private val DATA = 1
    private var isLoadingAdded = false

    private val list = ArrayList<Article>()
    lateinit var onpress: onPress


    inner class LoadingVh(var itemLoadBinding: ItemLoadBinding) :
        RecyclerView.ViewHolder(itemLoadBinding.root) {

        fun onBind() {
            itemLoadBinding.progress.visibility = View.VISIBLE
        }
    }

    inner class DataVh(var itemDataBinding: PagingRvItemBinding) :
        RecyclerView.ViewHolder(itemDataBinding.root) {

        fun onBind(data: Article) {
            itemDataBinding.apply {
                Picasso.get().load(data.urlToImage).into(itemDataBinding.image)
                itemDataBinding.avtor.text = data.title
                itemDataBinding.content.text = data.content
                itemDataBinding.content2.text = data.publishedAt.substring(0, 10)
            }
            itemDataBinding.container.setOnClickListener {
                onpress.onclick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING) {
            return LoadingVh(
                ItemLoadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return DataVh(
                PagingRvItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == LOADING) {
            val loadingVh = holder as LoadingVh
            loadingVh.onBind()
        } else {
            val dataVh = holder as DataVh
            dataVh.onBind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) LOADING else DATA
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(otherList: List<Article>) {
        list.addAll(otherList)
        notifyDataSetChanged()
    }

    fun editLoading() {
        isLoadingAdded = true
    }

    fun removeLoading() {
        isLoadingAdded = false
    }

    interface onPress {
        fun onclick(photo: Article)
    }
}