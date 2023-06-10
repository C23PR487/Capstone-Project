package io.github.c23pr487.lapakin.ui.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.c23pr487.lapakin.utils.toPx

class MyItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val PIXEL_15 = 15.toPx.toInt()
        val PIXEL_17 = 17.toPx.toInt()
        val PIXEL_10 = 10.toPx.toInt()
        if (parent.getChildAdapterPosition(view) < 2) {
            outRect.top = PIXEL_15
        }

        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = PIXEL_17
            outRect.right = PIXEL_10
        } else {
            outRect.left = PIXEL_10
            outRect.right = PIXEL_17
        }

        outRect.bottom = PIXEL_15
    }
}