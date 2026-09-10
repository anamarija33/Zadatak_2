package org.unizd.rma.brkic

import android.content.Context
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.unizd.rma.brkic.data.database.SkincareItemDatabase
import org.unizd.rma.brkic.data.database.dao.SkincareItemDao
import org.unizd.rma.brkic.data.repository.SkincareItemRepositoryImpl
import org.unizd.rma.brkic.domain.repositories.SkincareItemRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSkincareItemDatabase(
        @ApplicationContext context: Context
    ): SkincareItemDatabase{
        return Room.databaseBuilder(
            context,
            SkincareItemDatabase::class.java,
            SkincareItemDatabase.DATABASE_NAME
        ).build()
    }
    @Singleton
    @Provides
    fun provideSkincareItemDao(
        database: SkincareItemDatabase
    ): SkincareItemDao{
        return database.skincareItemDao()
    }

    @Singleton
    @Provides
    fun provideSkincareItemRepository(
        dao: SkincareItemDao
    ): SkincareItemRepository{
        return SkincareItemRepositoryImpl(dao)
    }
}
