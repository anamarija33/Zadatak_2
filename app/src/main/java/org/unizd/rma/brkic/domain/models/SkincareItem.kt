package org.unizd.rma.brkic.domain.models

import android.net.Uri
import java.util.Date

data class SkincareItem(
    val id: Int = 0,
    val name: String,
    val brand: String,
    val openingDate: Date,
    val imageUri: String?= null,
    val typeOfProduct:String

)
