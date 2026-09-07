package org.unizd.rma.brkic.presentation

import org.unizd.rma.brkic.domain.models.SkincareItem

sealed class ProductUiState {
    object Loading: ProductUiState()
    data class Success(val skincareItem: List<SkincareItem>): ProductUiState()
    data class Error (val message: String): ProductUiState()
    object Empty: ProductUiState()
}