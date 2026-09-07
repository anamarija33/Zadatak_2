package org.unizd.rma.brkic.presentation.skincareItem.detail

import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: SkincareItemRepository
): ViewModel(){
    private val _skincareItem = MutableStateFlow<SkincareItem?>(null)
    val skincareItem=_skincareItem.asStateFlow()

    fun loadProduct(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            _skincareItem.value = repository.getSingleProduct(id)
        }
    }

    fun updateProductImage(imageUri: String){
        _skincareItem.value?.let { skincareItem->
            val updated = skincareItem.copy(imageUri=imageUri)
            viewModelScope.launch ( Dispatchers.IO ){
                repository.updateProduct(updated)
                _skincareItem.value=updated
            }
        }
    }
}
