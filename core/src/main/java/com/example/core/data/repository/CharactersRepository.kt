package com.example.core.data.repository

import androidx.paging.PagingSource

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}