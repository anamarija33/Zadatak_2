package org.unizd.rma.brkic.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.unizd.rma.brkic.data.database.entity.SkincareItemEntity

@Dao
interface SkincareItemDao {

    @Query("SELECT * FROM products ORDER BY openingDate DESC")
    fun getAllProducts(): Flow<List<SkincareItemEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Int): SkincareItemEntity?

    @Insert
    suspend fun insertProduct(skincareItemEntity: SkincareItemEntity): Long

    @Update
    suspend fun updateProduct(skincareItemEntity: SkincareItemEntity)

    @Delete
    suspend fun deleteProduct(skincareItemEntity: SkincareItemEntity)

    @Query("DELETE FROM products WHERE id=:id")
    suspend fun deleteProductById(id: Int)

    @Query("SELECT * FROM products WHERE name LIKE :query")
    fun searchProduct(query: String): Flow<List<SkincareItemEntity>>
}