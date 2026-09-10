package org.unizd.rma.brkic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.unizd.rma.brkic.data.database.dao.SkincareItemDao
import org.unizd.rma.brkic.data.database.entity.SkincareItemEntity

@Database(
    entities = [SkincareItemEntity::class],
            version = 1
)
abstract class SkincareItemDatabase:RoomDatabase() {
    abstract fun skincareItemDao(): SkincareItemDao

    companion object{
        const val DATABASE_NAME="products_db"
    }

}