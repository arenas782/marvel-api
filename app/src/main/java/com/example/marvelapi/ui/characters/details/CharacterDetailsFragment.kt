package com.example.marvelapi.ui.characters.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.marvelapi.R
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.databinding.FragmentCharacterDetailsBinding

import com.example.marvelapi.ui.base.BaseFragment
import com.example.marvelapi.ui.characters.main.viewmodel.CharactersViewModel
import com.example.marvelapi.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment() {
    private val args : CharacterDetailsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentCharacterDetailsBinding::bind)
    private val viewModel : CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.shimmerViewContainer.startShimmer()
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setupListener()
    }

    private fun setupListener(){
        val characterId = args.characterId
        if(characterId != null) {
            viewModel.getCharacterDetails(characterId = characterId)
            viewModel.characterDetails.observe(viewLifecycleOwner){
                bindCharacter(it)
            }
        }
    }

    private fun bindCharacter(character : MarvelCharacter?){
        if (character != null){
            binding.regularFrame.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
            binding.shimmerViewContainer.stopShimmer()
            binding.item = character
            binding.ivCharacterPoster.load(character.thumbnail.path.plus((".").plus(character.thumbnail.extension))){
                crossfade(true)
                placeholder(R.drawable.ic_100tb)
                scale(Scale.FILL)
            }
        }
    }
}