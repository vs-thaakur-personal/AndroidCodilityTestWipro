package com.example.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dashboard.ProductPagingSource
import com.example.data.models.Product
import com.example.domain.usecases.GetCategoryUseCases
import com.example.domain.usecases.GetProductsUseCase
import com.example.domain.usecases.SearchProductUseCase
import com.example.utils.di.DispatcherOptions
import com.example.utils.di.DispatcherQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @DispatcherQualifier(DispatcherOptions.IO) private val dispatcher: CoroutineDispatcher,
    private val getCategoryUseCase: GetCategoryUseCases,
    private val searchProductUseCase: SearchProductUseCase,
    private val productPagingSource: ProductPagingSource
) : ViewModel() {
    private val _loginFlow = MutableStateFlow(DashBoardUiState())
    val loginStateFlow: StateFlow<DashBoardUiState> = _loginFlow.asStateFlow()

    fun getAllCategories() {
        _loginFlow.update { it.copy(isLoading = true, error = null, searchList = null) }
        viewModelScope.launch(dispatcher) {
            getCategoryUseCase.execute(Unit)
                .onFailure { e ->
                    _loginFlow.update {
                        it.copy(isLoading = false, error = e, allCategories = null)
                    }
                }
                .onSuccess { result ->
                    _loginFlow.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            allCategories = result
                        )
                    }
                }
        }
    }

    fun getAllProducts() {
        val pagedData =
            Pager(PagingConfig(pageSize = 10, prefetchDistance = 10)) { productPagingSource }
                .flow.cachedIn(viewModelScope)
                .catch { e ->
                    _loginFlow.update {
                        it.copy(isLoading = false, error = e, allProductFlow = null)
                    }
                }

        _loginFlow.update {
            it.copy(
                isLoading = false, error = null, allProductFlow = pagedData
            )
        }
//            .catch {e->
//                _loginFlow.update {
//                    it.copy(isLoading = false, error = e, allProducts = null)
//                }
//            }
//            .onEach {pagingData->
//                _loginFlow.update {
//                    it.allProducts.flatMap { pagingData. }
//                    it.copy(
//                        isLoading = false, error = null, allProducts = it.allProducts?.
//                    )
//                }
//            }.launchIn(viewModelScope)

        _loginFlow.update { it.copy(isLoading = true, error = null, searchList = null) }
//        viewModelScope.launch(dispatcher) {
//            getProductUseCase.execute(Unit)
//                .onFailure { e ->
//                    _loginFlow.update {
//                        it.copy(isLoading = false, error = e, allProducts = null)
//                    }
//                }
//                .onSuccess { result ->
//                    _loginFlow.update {
//                        it.copy(
//                            isLoading = false,
//                            error = null,
//                            allProducts = result?.products
//                        )
//                    }
//                }
//        }
    }

    fun searchProducts(query: String) {
        _loginFlow.update { it.copy(isLoading = true, error = null, searchList = null) }
        viewModelScope.launch(dispatcher) {
            searchProductUseCase.execute(query)
                .onFailure { e ->
                    _loginFlow.update {
                        it.copy(isLoading = false, error = e, searchList = null)
                    }
                }
                .onSuccess { searchResult ->
                    _loginFlow.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            searchList = searchResult?.products
                        )
                    }
                }
        }
    }
}


data class DashBoardUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val searchList: List<Product>? = null,
    val allProductFlow: Flow<PagingData<Product>>? = null,
    val filteredProducts: List<Product>? = null,
    val allCategories: List<String>? = null
)