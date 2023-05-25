package com.developer.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.presentation.R
import com.developer.presentation.databinding.FragmentCharacterListBinding
import com.developer.presentation.extension.makeGone
import com.developer.presentation.extension.makeVisible
import com.developer.presentation.extension.showSnackBar
import com.developer.presentation.ui.adapter.CharacterAdapter
import com.developer.presentation.viewmodel.BBCharactersViewModel
import com.developer.presentation.viewmodel.CharacterUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    lateinit var viewModel: BBCharactersViewModel

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            requireActivity().defaultViewModelProviderFactory
        )[BBCharactersViewModel::class.java]

        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadCharacters()
            viewModel.characterListFlow.collect { result ->
                when (result) {
                    is CharacterUIModel.Success -> {
                        binding.progressBarCharacters.makeGone()
                        result.data.let {
                            characterAdapter.list = it
                        }
                    }

                    is CharacterUIModel.Error -> {
                        binding.progressBarCharacters.makeGone()
                        showSnackBar(binding.root, result.error!!)
                    }

                    is CharacterUIModel.Loading -> {
                        binding.progressBarCharacters.makeVisible()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        characterAdapter.setItemClickListener { character ->
            val bundle = bundleOf("character_id" to character.id)
            binding.root.findNavController()
                .navigate(R.id.action_characterListFragment_to_characterDetailFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}