package org.androix.wowcallrecorder.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.androix.wowcallrecorder.core.data.model.RecordingEntity

@Dao
interface RecordingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recording: RecordingEntity): Long

    @Query("SELECT * FROM recordings ORDER BY timestamp DESC")
    fun getAll(): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM recordings WHERE id = :id")
    fun getById(id: Long): Flow<RecordingEntity?>

    @Query("DELETE FROM recordings WHERE id = :id")
    suspend fun delete(id: Long)
}
