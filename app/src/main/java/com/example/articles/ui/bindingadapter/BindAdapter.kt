package com.example.articles.ui.bindingadapter

import android.util.Log.d
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bitcoinapp.R

@BindingAdapter("bindImage")
fun bindImage(imageView:ImageView,url:String?){
    d("bindImage",url.toString())
        Glide.with(imageView.context)
            .load(url)
            .apply(
                RequestOptions()
                .error(R.drawable.logo)
            )
            .into(imageView)
}

//@BindingAdapter("articleList")
//fun getAllArticleList(recyclerView: RecyclerView, data: PagingData<ArticlesEntity>) {
//    val adapter = recyclerView.adapter as ArticleAdapter
//    adapter.apply {
//        submitData(data)
//        notifyDataSetChanged()
//    }
//}
