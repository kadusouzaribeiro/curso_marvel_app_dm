package com.example.marvelapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val args by navArgs<DetailFragmentArgs>()

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArg = args.detailViewArg
        binding.imageCharacter.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl)
        }

        setSharedElementTransitionOnEnter()

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild = when (uiState) {
                DetailViewModel.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                is DetailViewModel.UiState.Success -> {
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                    }

                    FLIPPER_CHILD_POSITION_DETAIL
                }
                DetailViewModel.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.getCharactersCategories(detailViewArg.characterId)
                    }

                    FLIPPER_CHILD_POSITION_ERROR
                }
                DetailViewModel.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
            }
        }

        viewModel.getCharactersCategories(detailViewArg.characterId)
    }

    // Define a animação da transição como "move"
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3
    }
}