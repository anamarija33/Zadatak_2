package org.unizd.rma.brkic.presentation.skincareItem.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.brkic.domain.models.ProductType
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import org.unizd.rma.brkic.domain.usecases.product.AddProductUseCase
import org.unizd.rma.brkic.domain.usecases.product.UpdateProductUseCase
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val repository: SkincareItemRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow<AddProductUiState>(AddProductUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _brand = MutableStateFlow("")
    val brand = _brand.asStateFlow()

    private val _openingDate = MutableStateFlow(Date())
    val openingDate = _openingDate.asStateFlow()

    private val _imageUri = MutableStateFlow("")
    val imageUri = _imageUri.asStateFlow()
    private val _typeOfProduct = MutableStateFlow<ProductType?>(null)
    val typeOfProduct = _typeOfProduct.asStateFlow()
    private var productId: Int? = null

    fun setName(name: String) {
        _name.value = name
    }

    fun setBrand(brand: String) {
        _brand.value = brand
    }

    fun setopeningDate(openingDate: Date) {
        _openingDate.value = openingDate
    }

    fun setImageUri(uri: String) {
        _imageUri.value = uri
    }
    fun setTypeOfProduct(typeOfProduct: ProductType?) {
        _typeOfProduct.value = typeOfProduct
    }

    fun loadProductForEdit(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val product = repository.getSingleProduct(id)
                if (product != null) {
                    _name.value = product.name
                    _brand.value = product.brand
                    _openingDate.value = product.openingDate
                    _imageUri.value = product.imageUri ?: ""
                    productId = product.id
                    _typeOfProduct.value=product.typeOfProduct
                }
            } catch (e: Exception) {
                _uiState.value = AddProductUiState.Error("Greška pri učitavanju kontakta: ${e.message}")
            }
        }
    }
    fun loadProductForEditDirect(product: SkincareItem) {
        _name.value = product.name
        _brand.value = product.brand
        _openingDate.value = product.openingDate
        _imageUri.value = product.imageUri ?: ""
        productId = product.id
        _typeOfProduct.value = product.typeOfProduct
    }

    fun saveProduct() {
        if (!validateForm()) {
            _uiState.value = AddProductUiState.Error("Popunite sva polja")
            return
        }

        viewModelScope.launch {
            _uiState.value = AddProductUiState.Loading

            val product = SkincareItem(
                id = productId ?: 0,
                name = _name.value,
                brand = _brand.value,
                openingDate = _openingDate.value,
                imageUri = _imageUri.value,
                typeOfProduct = _typeOfProduct.value
            )

            val result = if (productId != null) {
                updateProductUseCase(product)
            } else {
                addProductUseCase(product).map {
                    Unit
                }
            }

            result
                .onSuccess {
                    _uiState.value = AddProductUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = AddProductUiState.Error(error.message ?: "Greška")
                }
        }
    }

    private fun validateForm(): Boolean {
        return _name.value.isNotEmpty() &&
                _brand.value.isNotEmpty() &&
                _typeOfProduct.value!= null &&
                _openingDate.value != null
    }

    fun resetState() {
        _uiState.value = AddProductUiState.Idle
    }

}