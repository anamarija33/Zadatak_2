package org.unizd.rma.brkic.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.unizd.rma.brkic.domain.models.ProductType
import kotlin.time.Instant

@Entity("products")
data class SkincareItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val brand: String,
    val openingDate: Long?,
    val imageUri: String?= null,
    val typeOfProduct: ProductType? = null
)