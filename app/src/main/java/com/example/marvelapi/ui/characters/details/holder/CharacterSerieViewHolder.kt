package com.example.marvelapi.ui.characters.details.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapi.data.model.response.CharacterSerie
import com.example.marvelapi.databinding.ItemCharacterSerieBinding

class CharacterSerieViewHolder(val binding : ItemCharacterSerieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(characterSerie : CharacterSerie) {
        binding.item = characterSerie
        binding.executePendingBindings()
    }
}