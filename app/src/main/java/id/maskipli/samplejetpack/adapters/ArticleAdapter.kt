package id.maskipli.samplejetpack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.maskipli.samplejetpack.databinding.ItemArticleBinding
import id.maskipli.samplejetpack.databinding.ItemProgressBinding
import id.maskipli.samplejetpack.models.Article
import id.maskipli.samplejetpack.models.RequestState

/**
 * @author hidayat @on 12/07/19
 **/
class ArticleAdapter(val listener: (Article) -> Unit)
    : PagedListAdapter<Article, RecyclerView.ViewHolder>(Article.diffCall) {

    companion object {
        private const val ITEM_TYPE_LOAD = 0
        private const val ITEM_TYPE_ARTICLE = 1
    }

    private var requestState: RequestState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_TYPE_ARTICLE) {
            val binding = ItemArticleBinding
                    .inflate(layoutInflater, parent, false)
            ArticleViewHolder(binding)
        } else {
            val binding = ItemProgressBinding
                    .inflate(layoutInflater, parent, false)
            ProgressViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            val article = getItem(position) as Article
            holder.bind(article)
            holder.itemView.setOnClickListener {
                listener(article)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading() && position == itemCount - 1) ITEM_TYPE_LOAD else ITEM_TYPE_ARTICLE
    }

    private fun isLoading(): Boolean {
        return (requestState != null && requestState == RequestState.RUNNING)
    }

    fun setRequestState(requestState: RequestState) {
        val wasLoading = isLoading()
        this.requestState = requestState
        val willLoading = isLoading()
        if (wasLoading != willLoading) {
            if (wasLoading) notifyItemRemoved(itemCount) else notifyItemInserted(itemCount)
        }
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                this.article = article
                executePendingBindings()
            }
        }
    }

    class ProgressViewHolder(binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root)
}