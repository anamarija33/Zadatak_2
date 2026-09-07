package org.unizd.rma.brkic.presentation.skincareItem.create

sealed class AddProductUiState {
    object Idle: AddProductUiState()
    object Loading: AddProductUiState()
    object Success: AddProductUiState()
    data class Error(val message: String): AddProductUiState()
}