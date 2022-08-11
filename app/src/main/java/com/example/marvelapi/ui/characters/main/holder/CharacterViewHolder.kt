package com.example.marvelapi.ui.characters.main.holder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.marvelapi.R
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.databinding.ItemCharacterBinding


class CharacterViewHolder(private val binding : ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(marvelCharacter : MarvelCharacter, callback :(MarvelCharacter) -> Unit) {
        Log.e("tag","binding")

        binding.container.setOnClickListener {
            callback.invoke(marvelCharacter)
        }
        binding.item = marvelCharacter

        val image = marvelCharacter.thumbnail.path.plus(".".plus(marvelCharacter.thumbnail.extension))
        Log.e("image",image)
        binding.ivCharacterPoster.load(marvelCharacter.thumbnail.path.plus(".".plus(marvelCharacter.thumbnail.extension))){
            crossfade(true)
            placeholder(R.drawable.ic_100tb)
            scale(Scale.FILL)
        }
        binding.executePendingBindings()
    }
}