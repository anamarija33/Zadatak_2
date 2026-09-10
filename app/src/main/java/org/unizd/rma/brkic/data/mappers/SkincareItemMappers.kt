package org.unizd.rma.brkic.data.mappers

import org.unizd.rma.brkic.data.database.entity.SkincareItemEntity
import org.unizd.rma.brkic.domain.models.ProductType
import org.unizd.rma.brkic.domain.models.SkincareItem
import java.util.Date

fun SkincareItemEntity.toDomain(): SkincareItem = SkincareItem(
    id = id,
    name = name,
    brand= brand,
    openingDate= openingDate,
 imageUri=imageUri,
 typeOfProduct=typeOfProduct
)

fun SkincareItem.toEntity(): SkincareItemEntity = SkincareItemEntity(
    id = id,
    name = name,
    brand= brand,
    openingDate= openingDate,
    imageUri=imageUri,
    typeOfProduct=typeOfProduct
)