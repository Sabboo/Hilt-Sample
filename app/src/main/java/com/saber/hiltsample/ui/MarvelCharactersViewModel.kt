package com.saber.hiltsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.saber.hiltsample.repository.MarvelCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(repository: MarvelCharactersRepository) :
    ViewModel() {

    val marvelCharacters = repository.marvelCharacters(20, 0)
        .cachedIn(viewModelScope)

}