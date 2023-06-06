package com.example.hw20.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PhotoPagingSource() : PagingSource<Int, com.example.hw20.api.Character>() {
    private val first_page: Int = 1
    private val repository = PhotoRepository()

    override fun getRefreshKey(state: PagingState<Int, com.example.hw20.api.Character>): Int? =
        first_page

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.hw20.api.Character> {
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


