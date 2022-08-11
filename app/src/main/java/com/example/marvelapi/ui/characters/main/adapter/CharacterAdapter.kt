package com.example.marvelapi.ui.characters.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.databinding.ItemCharacterBinding

import com.example.marvelapi.ui.characters.main.holder.CharacterViewHolder



class CharacterAdapter(private val callback: (MarvelCharacter) -> Unit) :
    PagingDataAdapter<MarvelCharacter, CharacterViewHolder>(
        CharactersClickCallback()
    ) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data!!,callback)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun getMovieId(position: Int):Int = getItem(position)?.id!!

    private class CharactersClickCallback : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
            return oldItem == newItem
        }
    }

}