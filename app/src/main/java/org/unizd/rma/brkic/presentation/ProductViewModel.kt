package org.unizd.rma.brkic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.usecases.product.DeleteProductUseCase
import org.unizd.rma.brkic.domain.usecases.product.GetProductsUseCase
import org.unizd.rma.brkic.domain.usecases.product.SearchProductUseCase
import javax.inject.Inject
@HiltViewModel
class ProductViewModel  @Inject constructor
   (
    private val getProductsUseCase: GetProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val searchProductUseCase: SearchProductUseCase
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow<ProductUiState>(ProductUiState.Loading)
        val uiState = _uiState.asStateFlow()

        private val _selectedProduct = MutableStateFlow<SkincareItem?>(null)
        val selectedProduct = _selectedProduct.asStateFlow()

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        init {
            // učitavanje kontakata
            loadProducts()
        }

        private fun loadProducts() {
            viewModelScope.launch {
                _uiState.value = ProductUiState.Loading
                try {
                    getProductsUseCase()
                        .collect { skincareItems ->
                            _uiState.value = if (skincareItems.isEmpty()) {
                                ProductUiState.Empty
                            } else {
                                ProductUiState.Success(skincareItems)
                            }
                        }
                } catch (e: Exception) {
                    _uiState.value = ProductUiState.Error(e.message ?: "Greška")
                }
            }
        }

        fun deleteProduct(id: Int) {
            viewModelScope.launch {
                deleteProductUseCase(id)
                    .onSuccess {  }
                    .onFailure { error ->
                        _uiState.value = ProductUiState.Error(
                            error.message ?: "Greška pri brisanju"
                        )
                    }
            }
        }

        fun selectProduct(skincareItem: SkincareItem) {
            _selectedProduct.value = skincareItem
        }

        fun updateSearchQuery(query: String) {
            _searchQuery.value = query

            if (query.isEmpty()) {
                loadProducts()
            } else {
                viewModelScope.launch {
                    try {
                        searchProductUseCase(query)
                            .collect {skincareItems ->
                                _uiState.value = if (skincareItems.isEmpty()) {
                                    ProductUiState.Empty
                                } else {
                                    ProductUiState.Success(skincareItems)
                                }
                            }
                    } catch (e: Exception) {
                        _uiState.value = ProductUiState.Error(
                            e.message ?: "Greška pri pretrazi"
                        )
                    }
                }
            }
        }

        fun clearError() {
            loadProducts()
        }

    }
