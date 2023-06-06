package com.example.hw21.ui.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hw21.ui.api.MovieCharacters

class PhotoPagingSource() : PagingSource<Int, MovieCharacters>() {
    private val first_page: Int = 1
    private val repository = PhotoRepository()

    override fun getRefreshKey(state: PagingState<Int, MovieCharacters>): Int? =
        first_page

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCharacters> {
        val page = params.key ?: first_page
        return kotlin.runCatching {
            repository.getPhoto(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
}


