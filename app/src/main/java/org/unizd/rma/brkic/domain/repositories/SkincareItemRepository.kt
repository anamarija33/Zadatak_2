package org.unizd.rma.brkic.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.unizd.rma.brkic.domain.models.SkincareItem

interface SkincareItemRepository {

    fun getAllProducts(): Flow<List<SkincareItem>>
    suspend fun getSingleProduct(id: Int): SkincareItem?
    suspend fun addNewProduct(skincareItem: SkincareItem): Long
    suspend fun updateProduct(skincareItem: SkincareItem)
    suspend fun deleteProduct(id:Int)
    fun searchProducts(query:String): Flow<List<SkincareItem>>
}