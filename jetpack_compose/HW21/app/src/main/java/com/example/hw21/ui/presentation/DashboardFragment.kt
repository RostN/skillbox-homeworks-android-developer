package com.example.hw21.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.hw21.databinding.FragmentDashboardBinding
import com.example.hw21.ui.api.Locations
import com.example.hw21.ui.data.DashboardViewModel
import com.example.hw21.ui.theme.ComposeSampleTheme

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private val pageData by lazy {
        val source = object : PagingSource<Int, Locations>() {
            override fun getRefreshKey(state: PagingState<Int, Locations>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Locations> = kotlin
                .runCatching { viewModel.load(params.key ?: 0) }.fold(
                    onSuccess = { list ->
                        LoadResult.Page(
                            data = list,
                            prevKey = params.key?.let { it - 1 },
                            nextKey = (params.key ?: 0) + 1,
                        )
                    },
                    onFailure = { throwable -> LoadResult.Error(throwable) }
                )
        }
        return@lazy Pager(PagingConfig(pageSize = 20)) { source }.flow
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            ComposeSampleTheme {
                ListView()
            }
        }
    }

    @Composable
    fun ListView() {
        val items: LazyPagingItems<Locations> = pageData.collectAsLazyPagingItems()

        LazyColumn {
            items(items) {
                it?.let { CommentView(comment = it) } ?: Text(text = "Oops")
            }

            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier,
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = items.loadState.refresh as LoadState.Error
                        item {
                            Column(modifier = Modifier.fillParentMaxSize()) {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "RETRY")
                                }
                            }

                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = items.loadState.append as LoadState.Error
                        item {
                            Column(
                                verticalArrangement = Arrangement.Center,
                            ) {
                                e.error.localizedMessage?.let { Text(text = it, color = White) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CommentView(comment: Locations) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(6.dp)
                .background(color = White)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(start = 6.dp)) {
                Text(
                    text = comment.name,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = comment.type,
                    maxLines = 2,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = comment.dimension,
                    maxLines = 2,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



