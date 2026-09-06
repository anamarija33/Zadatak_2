package org.unizd.rma.brkic.domain.usecases.product

import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import javax.inject.Inject

class DeleteProductUseCase  @Inject constructor(
    private val repository: SkincareItemRepository
){
    suspend operator fun invoke(id:Int): Result<Unit>{
        return try {
            repository.deleteProduct(id)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}