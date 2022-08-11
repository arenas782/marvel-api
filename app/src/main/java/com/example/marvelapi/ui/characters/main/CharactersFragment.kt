package com.example.marvelapi.ui.characters.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.databinding.FragmentCharactersBinding

import com.example.marvelapi.ui.base.BaseFragment
import com.example.marvelapi.ui.characters.main.adapter.CharacterAdapter
import com.example.marvelapi.ui.characters.main.adapter.MovieLoadingStateAdapter
import com.example.marvelapi.ui.characters.main.adapter.SwipeGesture
import com.example.marvelapi.ui.characters.main.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment  : BaseFragment(){
    lateinit var binding : FragmentCharactersBinding
    private val viewModel : CharactersViewModel by viewModels()
    private val adapter =
        CharacterAdapter { marvelCharacter : MarvelCharacter -> goToDetails(marvelCharacter) }
    private var searchJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCharactersBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root

    }


    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  setupSwipeLayout()

        setUpAdapter()
        subscribe()

    }

    private fun goToDetails(marvelCharacter : MarvelCharacter){
        findNavController().navigate(CharactersFragmentDirections.actionMainFragmentToDetailsFragment(
                marvelCharacter.id.toString()
            )
        )
    }

    @ExperimentalPagingApi
    private fun subscribe(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getCharacters()
                .collectLatest {
                    adapter.submitData(it)

                }
        }
    }
    companion object {
        const val TAG =  "CharactersFragment"
    }

    private fun setUpAdapter() {

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }

        val swipeGesture = object : SwipeGesture(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.position
                viewModel.deleteMovie(adapter.getMovieId(itemPosition))
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.recycler)

        binding.recycler.adapter = adapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter { retry() }
        )


        adapter.addLoadStateListener { loadState ->

            if (loadState.mediator?.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.progress.isVisible = true
                }
                binding.errorTxt.isVisible = false

            } else {
                binding.progress.isVisible = false
                

                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
                        binding.errorTxt.isVisible = true
                        binding.errorTxt.text = it.error.localizedMessage
                    }

                }

            }
        }

    }
    private fun retry() {
        adapter.retry()
    }
}