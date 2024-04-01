package com.example.productdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.Product
import com.example.domain.usecases.GetProductByIdUseCase
import com.example.utils.di.DispatcherOptions
import com.example.utils.di.DispatcherQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    @DispatcherQualifier(DispatcherOptions.IO) private val dispatcher: CoroutineDispatcher,
    private val useCase: GetProductByIdUseCase
) : ViewModel() {
    fun getProductDetails(productId: Int) {

        viewModelScope.launch(dispatcher) {
            _productDetailFlow.update {
                it.copy(isLoading = true, errorMessage = null, product = null)
            }
            useCase.execute(productId)
                .onFailure { error ->
                    _productDetailFlow.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage,
                            product = null
                        )
                    }
                }.onSuccess { product ->
                    _productDetailFlow.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            product = product
                        )
                    }
                }
        }
    }

    private val _productDetailFlow = MutableStateFlow(ProductDetailUiState())
    val productDetailStateFlow: StateFlow<ProductDetailUiState> = _productDetailFlow.asStateFlow()

}

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val errorMessage: String? = null
)