package org.unizd.rma.brkic.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.unizd.rma.brkic.data.database.dao.SkincareItemDao
import org.unizd.rma.brkic.data.mappers.toDomain
import org.unizd.rma.brkic.domain.models.SkincareItem
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import org.unizd.rma.brkic.data.mappers.toEntity
class SkincareItemRepositoryImpl(
    private val skincareItemDao: SkincareItemDao
) : SkincareItemRepository{
    override fun getAllProducts(): Flow<List<SkincareItem>> =
        skincareItemDao.getAllProducts().map {  skincareItemEntities ->
            skincareItemEntities.map { it.toDomain() }
    }


    override suspend fun getSingleProduct(id: Int): SkincareItem? =
        skincareItemDao.getProductById(id)?.toDomain()


    override suspend fun addNewProduct(skincareItem: SkincareItem): Long =
        skincareItemDao.insertProduct(skincareItem.toEntity())


    override suspend fun updateProduct(skincareItem: SkincareItem) =
        skincareItemDao.updateProduct(skincareItem.toEntity())


    override suspend fun deleteProduct(id: Int) =
        skincareItemDao.deleteProductById(id)

    override fun searchProducts(query: String): Flow<List<SkincareItem>> =
        skincareItemDao.searchProduct("%$query%").map {
                skincareItemEntity ->
            skincareItemEntity.map { it.toDomain() }
        }


}