package com.example.marvelapi.ui.characters.details.adapter



import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapi.R
import com.example.marvelapi.data.model.response.CharacterSerie
import com.example.marvelapi.databinding.ItemCharacterSerieBinding
import com.example.marvelapi.ui.characters.details.holder.CharacterSerieViewHolder

class CharacterSeriesAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var characterSeries: MutableList<CharacterSerie>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemCharacterSerieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return CharacterSerieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharacterSerieViewHolder -> {
                holder.bind(characterSeries[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_character_serie


    override fun getItemCount(): Int =
        if (::characterSeries.isInitialized) characterSeries.size else 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(characterSeries: MutableList<CharacterSerie>) {
        this.characterSeries = characterSeries
        notifyDataSetChanged()
    }
}