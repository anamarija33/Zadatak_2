package org.unizd.rma.brkic.domain.usecases.product

import kotlinx.coroutines.flow.Flow
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import javax.inject.Inject

class GetProductsUseCase@Inject constructor(
    private val repository: SkincareItemRepository
){
    operator fun invoke(): Flow<List<SkincareItem>> {
        return repository.getAllProducts()
    }
}