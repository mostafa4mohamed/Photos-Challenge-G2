package com.group.koinandcoroutiens.utils

interface RecyclerViewOnClickListener {

    fun <UI, D> onRootClickListener(dataBinding: UI, data: D)

    fun <UI, D> onRootClickListener(dataBinding: UI, data: D, position: Int) {}

}