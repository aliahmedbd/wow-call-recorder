package org.androix.wowcallrecorder.core.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.androix.wowcallrecorder.core.data.database.AppDatabase
import org.androix.wowcallrecorder.core.data.database.RecordingDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "wow_call_recorder.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecordingDao(appDatabase: AppDatabase): RecordingDao {
        return appDatabase.recordingDao()
    }
}
