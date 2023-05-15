package com.developer.mayank.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.developer.mayank.databinding.FragmentCharacterDetailBinding
import com.developer.mayank.extension.observe
import com.developer.presentation.viewmodel.BBCharacterDetailViewModel
import com.developer.presentation.viewmodel.CharacterDetailUIModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private var characterId: Int = 0
    lateinit var viewModel: BBCharacterDetailViewModel
    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            requireActivity().defaultViewModelProviderFactory
        )[BBCharacterDetailViewModel::class.java]

        characterId = requireArguments().getInt("character_id")
        observe(viewModel.character, ::onViewStateChange)
        viewModel.getCharacterDetail(characterId)
    }

    private fun onViewStateChange(result: CharacterDetailUIModel) {
        when (result) {
            is CharacterDetailUIModel.Success -> {
                result.data.let { character ->
                    _binding?.apply {
                        textViewCharacterName.text = character.name
                        glide.load(character.image).into(imageViewCharacter)
                        textViewOccupation.text = character.occupation
                        textViewGender.text = character.gender
                        textViewVoicedBy.text = character.voicedBy
                        textViewHairColor.text = character.hairColor
                    }
                }
            }
            is CharacterDetailUIModel.Error -> {
               }
            is CharacterDetailUIModel.Loading -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}