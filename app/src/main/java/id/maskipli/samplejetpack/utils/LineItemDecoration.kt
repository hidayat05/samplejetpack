package id.maskipli.samplejetpack.utils

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.maskipli.samplejetpack.R


/**
 * @author hidayat @on 12/07/19
 **/
class LineItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mDivider ?: return
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}