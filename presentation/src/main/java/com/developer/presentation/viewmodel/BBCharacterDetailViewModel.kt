package com.developer.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.developer.domain.models.CharacterEntityItem
import com.developer.domain.use_cases.GetCharacterByIdUseCase
import com.developer.presentation.utils.CoroutineContextProvider
import com.developer.presentation.utils.UiAwareLiveData
import com.developer.presentation.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "CharacterDetailVM"

sealed class CharacterDetailUIModel : UiAwareModel() {
    object Loading : CharacterDetailUIModel()
    data class Error(var error: String = "") : CharacterDetailUIModel()
    data class Success(val data: CharacterEntityItem) : CharacterDetailUIModel()
}


@HiltViewModel
class BBCharacterDetailViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : BaseViewModel(contextProvider) {

    private val _character = UiAwareLiveData<CharacterDetailUIModel>()
    private var character: LiveData<CharacterDetailUIModel> = _character

    fun getCharacter(): LiveData<CharacterDetailUIModel> {
        return character
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, exception.message ?: "Error ")
        _character.postValue(CharacterDetailUIModel.Error(exception.message ?: "Error"))
    }

    fun getCharacterDetail(characterId: Int) {
        _character.postValue(CharacterDetailUIModel.Loading)
        launchCoroutineIO {
            loadCharacter(characterId)
        }
    }

    private suspend fun loadCharacter(characterId: Int) {
        getCharacterByIdUseCase(characterId).collectLatest {
            Log.d(TAG, it.toString())
            _character.postValue(CharacterDetailUIModel.Success(it))
        }
    }


}