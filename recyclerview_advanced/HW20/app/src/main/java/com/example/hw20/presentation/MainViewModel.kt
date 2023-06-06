package com.example.hw20.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hw20.data.PhotoPagingSource
import com.example.hw20.data.PhotoRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel private constructor(private val repository: PhotoRepository) : ViewModel() {
    constructor() : this(PhotoRepository())

    val pagedPhoto: Flow<PagingData<com.example.hw20.api.Character>> = Pager(
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = { PhotoPagingSource() }).flow.cachedIn(viewModelScope)
}