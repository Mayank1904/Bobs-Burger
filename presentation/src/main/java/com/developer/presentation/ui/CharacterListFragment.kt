package com.developer.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    private val viewModel: BBCharactersViewModel by viewModels()

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    companion object {
        private const val CHARACTER_ID_KEY = "character_id"
    }

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

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            showSnackBar(binding.root, result.error)
                        }

                        is CharacterUIModel.Loading -> {
                            binding.progressBarCharacters.makeVisible()
                        }
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
            val bundle = bundleOf(CHARACTER_ID_KEY to character.id)
            binding.root.findNavController()
                .navigate(R.id.action_characterListFragment_to_characterDetailFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}