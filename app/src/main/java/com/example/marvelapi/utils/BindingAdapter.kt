package com.example.marvelapi.utils

import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapi.ui.characters.main.adapter.CharacterAdapter


@BindingAdapter(value= ["bind:adapter"],requireAll = true)
fun setAdapter(rv: RecyclerView, mAdapter: PagingDataAdapter<*,*>) {
    rv.apply {
        setHasFixedSize(true)
        mAdapter as CharacterAdapter
        adapter = mAdapter
    }
}



