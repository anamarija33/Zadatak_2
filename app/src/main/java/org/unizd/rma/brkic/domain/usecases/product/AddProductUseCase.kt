package org.unizd.rma.brkic.domain.usecases.product

import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: SkincareItemRepository
) {
    suspend operator fun invoke(skincareItem: SkincareItem): Result<Long>{
        return try{
            val id= repository.addNewProduct(skincareItem)
            Result.success(id)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }
}