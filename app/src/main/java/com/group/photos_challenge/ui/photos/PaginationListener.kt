package com.group.photos_challenge.ui.photos

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener
    (private val layoutManager: LinearLayoutManager, private val pageSize: Int) :
    RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val currentFirstVisible = layoutManager.findFirstVisibleItemPosition()

        if (dy > 0) {
            //Scrolling down
            Log.e("Scrolling", "onScrolled: 1")
            if (!isLoading && !isLastPage) {
                if (visibleItemCount + currentFirstVisible >= totalItemCount && currentFirstVisible >= 0 && totalItemCount >= pageSize) {
                    loadMoreItems()
                }
            }
        } else
            Log.e("Scrolling", "onScrolled: 2")

    }


    protected abstract fun loadMoreItems()

    abstract var isLastPage: Boolean

    abstract var isLoading: Boolean

}