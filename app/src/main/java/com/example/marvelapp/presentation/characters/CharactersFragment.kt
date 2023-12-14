package com.example.marvelapp.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding

    private val charactersAdapter = CharactersAdapter()

    //private var _binding: FragmentCharactersBinding? = null
    //private val binding: FragmentCharactersBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharactersBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()

        charactersAdapter.submitList(
            listOf(
                Character("3D-Man", "https://pbs.twimg.com/media/EoQClRBXYAECWxN.jpg"),
                Character("3D-Man", "https://pbs.twimg.com/media/EoQClRBXYAECWxN.jpg"),
                Character("3D-Man", "https://pbs.twimg.com/media/EoQClRBXYAECWxN.jpg"),
                Character("3D-Man", "https://pbs.twimg.com/media/EoQClRBXYAECWxN.jpg")
            )
        )
    }

    private fun initCharactersAdapter() {
        with(binding.recyclerCharacters) {
            setHasFixedSize(true)
            adapter = charactersAdapter
        }
    }
}