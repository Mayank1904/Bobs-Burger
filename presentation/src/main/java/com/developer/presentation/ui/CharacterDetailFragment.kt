package com.developer.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.developer.presentation.databinding.FragmentCharacterDetailBinding
import com.developer.presentation.extension.makeGone
import com.developer.presentation.extension.makeVisible
import com.developer.presentation.extension.showSnackBar
import com.developer.presentation.viewmodel.BBCharacterDetailViewModel
import com.developer.presentation.viewmodel.CharacterDetailUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
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
        viewModel.getCharacterDetail(characterId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characterDetailFlow.collect { result ->
                when (result) {
                    is CharacterDetailUIModel.Success -> {
                        result.data.let { character ->
                            binding.apply {
                                binding.progressBarCharacterDetail.makeGone()
                                cardViewImage.makeVisible()
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
                        binding.progressBarCharacterDetail.makeGone()
                        showSnackBar(binding.root, getString(result.error))

                    }

                    is CharacterDetailUIModel.Loading -> {
                        binding.progressBarCharacterDetail.makeVisible()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var characterId: Int = 0
    }
}