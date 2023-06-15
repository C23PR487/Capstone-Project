package io.github.c23pr487.lapakin.ui.marketresearch

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.github.c23pr487.lapakin.databinding.NewsItemBinding
import io.github.c23pr487.lapakin.model.ArticlesItem

class NewsAdapter(diffCallback: DiffUtil.ItemCallback<ArticlesItem>) :
    PagingDataAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item)
    }

    class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem?) {
            binding.textViewArticleAuthor.text = news?.author
            binding.textViewArticleTitle.text = news?.title
            binding.textViewArticleAuthor.text = news?.author
            binding.textViewArticleDescription.text = news?.description
            Glide.with(binding.root.context).asDrawable().load(news?.urlToImage)
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            binding.constraintLayout.background = it
                        }
                        return true
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return true
                    }
                }).submit()
            binding.root.setOnClickListener {
                news?.url?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }
}