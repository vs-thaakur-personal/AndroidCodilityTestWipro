package com.example.dashboard

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.models.Product
import com.example.domain.usecases.GetProductsUseCase
import javax.inject.Inject

class ProductPagingSource @Inject constructor(private val getProductUseCase: GetProductsUseCase) :
    PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val nextPage = params.key ?: 1
            val response = getProductUseCase.execute(nextPage)
            val list = response.getOrThrow()?.products ?: listOf()
            LoadResult.Page(
                data = list,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (list.isEmpty()) null else nextPage + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
