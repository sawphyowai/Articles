package com.example.articles.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.articles.data.remote.db.ArticlesEntity
import com.example.bitcoinapp.databinding.OverviewItemLayoutBinding

class ArticleAdapter(private val onCLick: (ArticlesEntity) -> Unit) :
    PagingDataAdapter<ArticlesEntity, ArticleAdapter.ArticleViewHolder>(
        ArticleCallBack()
    ) {
    class ArticleViewHolder(private val binding: OverviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticlesEntity?, onCLick: (ArticlesEntity) -> Unit) {
            binding.viewModel = item
            binding.root.setOnClickListener {
                if (item != null) {
                    onCLick(item)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ArticleViewHolder {
                val inflate = LayoutInflater.from(parent.context)
                val binding = OverviewItemLayoutBinding.inflate(inflate, parent, false)
                return ArticleViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onCLick)
    }
}

class ArticleCallBack : DiffUtil.ItemCallback<ArticlesEntity>() {
    override fun areItemsTheSame(
        oldItem: ArticlesEntity,
        newItem: ArticlesEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ArticlesEntity,
        newItem: ArticlesEntity
    ): Boolean {
        return oldItem == newItem
    }
}
