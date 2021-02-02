package com.mhand.android.study.pokedex.app.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhand.android.study.pokedex.model.PokemonDetail
import com.mhand.android.study.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val scope = viewModelScope

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _pokemonList: MutableLiveData<List<PokemonDetail>> = MutableLiveData()
    val pokemonList: LiveData<List<PokemonDetail>> = _pokemonList

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        if (!_isLoading.value) {
            _isLoading.value = true

            scope.launch {
                val updatedPokemonList = _pokemonList.value?.toMutableList() ?: mutableListOf()

                pokemonRepository.getAllPokemon(
                    limit = POKEMON_LIST_DEFAULT_LIMIT,
                    offset = updatedPokemonList.lastOrNull()?.id ?: 0
                ).results.map {
                    pokemonRepository.getPokemonDetail(it.name)
                }.let {
                    updatedPokemonList.addAll(it)
                }

                _pokemonList.postValue(updatedPokemonList)
                _isLoading.value = false
            }
        }
    }

    fun onNavigationClick() {
        // TODO :: Handling event (Ex. Open Drawer..)
    }

    fun onAppBarActionClick(type: AppBarActionType) {
        // TODO :: Handling Event (Ex. Show Dialog..)
    }

    fun onPokemonClick(pokemonDetail: PokemonDetail) {
        // TODO :: Open pokemon detail page
    }

    companion object {
        private const val POKEMON_LIST_DEFAULT_LIMIT = 30
    }
}