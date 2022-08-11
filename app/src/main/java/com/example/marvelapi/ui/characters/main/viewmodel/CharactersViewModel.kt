package com.example.marvelapi.ui.characters.main.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.paging.*
import com.example.marvelapi.data.model.response.CharacterSerie
import com.example.marvelapi.data.model.response.MarvelCharacter
import com.example.marvelapi.data.model.response.MovieDetailsResponse
import com.example.marvelapi.data.repository.CharactersRepository
import com.example.marvelapi.ui.characters.details.adapter.CharacterSeriesAdapter
import com.example.marvelapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository : CharactersRepository) : ViewModel() {



    private var _characterDetails = MutableLiveData<MarvelCharacter>()
    var characterDetails : LiveData<MarvelCharacter> = _characterDetails


    private var currentResult: Flow<PagingData<MarvelCharacter>>? = null
    private val characterSeriesList : MutableList <CharacterSerie> = arrayListOf()






    @ExperimentalPagingApi
    fun getCharacters(): Flow<PagingData<MarvelCharacter>> {
        val newResult: Flow<PagingData<MarvelCharacter>> =
            repository.getCharacters().cachedIn(viewModelScope)
        currentResult = newResult

        return newResult
    }

    fun getCharacterDetails (characterId : String) {
        viewModelScope.launch {
            val character = repository.getCharacterDetails(characterId).data.results[0]
            _characterDetails.value = character
            setupCharactersSeriesList(character.series.items)
        }
    }
//    fun getCharacterDetails(characterId : String) = liveData(Dispatchers.Main) {
//        viewModelScope.launch {
//            repository.getNewsByDate().onEach {
//                Log.e("TAG",it.data.toString())
//                _posts.value = it
//                it.data?.let { postList -> setupPostsList(postList) }
//
//            }.launchIn(viewModelScope)
//            _swipedToRefresh.value = EventLiveData(false)
//            MainActivity.mutableMainProgress.value = View.GONE
//        }
//            emit(Resource.loading(data = null))
//            try {
//                val characterDetails = repository.getCharacterDetails(characterId = characterId)
//                Log.e("movie",characterDetails.toString())
//                emit(Resource.success(data = characterDetails))
//            } catch (exception: Exception) {
//                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//            }
//    }


    private fun setupCharactersSeriesList(list : List<CharacterSerie>){
        characterSeriesList.clear()
        for (item in list)
            characterSeriesList.add(item)
        characterSeriesAdapter.updateData(characterSeriesList)
}

     fun deleteMovie(id : Int){
         CoroutineScope(Dispatchers.IO).launch {
             Log.e("TAG","deleting post")
             repository.deleteMovieById(id)
         }
    }

    val characterSeriesAdapter = CharacterSeriesAdapter ()

}