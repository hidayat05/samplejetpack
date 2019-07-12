package id.maskipli.samplejetpack.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

/**
 * @author hidayat @on 12/07/19
 **/
@Parcelize
data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable {

    companion object {

        val diffCall = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }
}