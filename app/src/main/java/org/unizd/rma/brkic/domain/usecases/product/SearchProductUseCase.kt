package org.unizd.rma.brkic.domain.usecases.product

import kotlinx.coroutines.flow.Flow
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val repository: SkincareItemRepository
){
    operator fun invoke(query: String): Flow<List<SkincareItem>> {
        return repository.searchProducts(query)
    }
}