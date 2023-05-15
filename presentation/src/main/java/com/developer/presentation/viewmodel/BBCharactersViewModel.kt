package com.developer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.use_cases.GetCharactersUseCase
import com.developer.presentation.utils.CoroutineContextProvider
import com.developer.presentation.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

sealed class CharacterUIModel : UiAwareModel() {
    object Loading : CharacterUIModel()
    data class Error(var error: String = "") : CharacterUIModel()
    data class Success(val data: List<CharacterEntityItem>) : CharacterUIModel()
}

@HiltViewModel
 class BBCharactersViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharactersUseCase,
    coroutineContextProvider: CoroutineContextProvider) : BaseViewModel(coroutineContextProvider) {

    private val _characterList = MutableLiveData<CharacterUIModel>()
    private var characterList : LiveData<CharacterUIModel> = _characterList

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _characterList.postValue(CharacterUIModel.Error(exception.message ?: "Error"))

        }

    fun getCharacterList(): LiveData<CharacterUIModel> {
        return characterList
    }

     fun getCharacters() {
         _characterList.postValue(CharacterUIModel.Loading)
         launchCoroutineIO {
            loadCharacters()
        }
    }

    private suspend fun loadCharacters() {
        getCharacterUseCase(Unit).collectLatest {
            _characterList.postValue(CharacterUIModel.Success(it))
        }
    }

}