package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.HrItemBinding
import com.example.newsapp.models.Article
import com.squareup.picasso.Picasso

class HorizontalRvAdapter(var list: List<Article>) :
    RecyclerView.Adapter<HorizontalRvAdapter.Vh>() {
    inner class Vh(var itemview: HrItemBinding) : RecyclerView.ViewHolder(itemview.root) {
        fun bind(article: Article) {
            Picasso.get().load(article.urlToImage).into(itemview.image)
            itemview.avtor.text = article.title
            itemview.content.text = article.description
            itemview.content2.text = article.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HrItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}