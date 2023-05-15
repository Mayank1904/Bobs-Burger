package com.developer.mayank.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.mayank.R
import com.developer.mayank.databinding.FragmentCharacterListBinding
import com.developer.mayank.extension.makeGone
import com.developer.mayank.extension.makeVisible
import com.developer.mayank.extension.observe
import com.developer.mayank.ui.adapter.CharacterAdapter
import com.developer.presentation.viewmodel.BBCharactersViewModel
import com.developer.presentation.viewmodel.CharacterUIModel
import dagger.hilt.android.AndroidEntryPoint
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

        observe(viewModel.getCharacterList(), ::onViewStateChange)
        viewModel.getCharacters()
        setupRecyclerView()
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

    private fun onViewStateChange(result: CharacterUIModel) {
        when (result) {
            is CharacterUIModel.Success -> {
                _binding?.progressBarCharacters?.makeGone()
                result.data.let {
                    characterAdapter.list = it
                }
            }
            is CharacterUIModel.Error -> {
                _binding?.progressBarCharacters?.makeGone()
            }
            is CharacterUIModel.Loading -> {
                _binding?.progressBarCharacters?.makeVisible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "CharacterListFragment"
    }
}