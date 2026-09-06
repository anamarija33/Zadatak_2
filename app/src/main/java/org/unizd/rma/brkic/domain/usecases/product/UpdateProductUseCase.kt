package org.unizd.rma.brkic.domain.usecases.product

import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: SkincareItemRepository
) {
    suspend operator fun invoke(skincareItem: SkincareItem): Result<Unit> {
        return try {
            repository.updateProduct(skincareItem)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}