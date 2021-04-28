package com.challenge.data_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.data_db.entities.LaunchModelEntities
import com.challenge.data_db.entities.LaunchModelEntities.Companion.LAUNCH_MODEL_ENTITIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchModelEntitiesDao {

    @Query("SELECT * FROM  $LAUNCH_MODEL_ENTITIES_TABLE")
    fun getCachedResponses(): Flow<LaunchModelEntities?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(entity: LaunchModelEntities)

    @Query("DELETE FROM $LAUNCH_MODEL_ENTITIES_TABLE")
    suspend fun clearTable()
}