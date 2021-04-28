package com.challenge.data_db.entities

import androidx.room.*
import com.challenge.data_db.entities.LaunchModelEntities.Companion.LAUNCH_MODEL_ENTITIES_TABLE
import com.challenge.data_db.model.LaunchRepositoryDbEntity
import com.challenge.data_db.utils.LaunchRepositoryDbEntityConverter


@Entity(tableName = LAUNCH_MODEL_ENTITIES_TABLE)
@TypeConverters(LaunchRepositoryDbEntityConverter::class)
data class LaunchModelEntities(
    @ColumnInfo(name = TIME_STAMP)
    var timeStamp: Long = 0,

    @ColumnInfo(name = LAUNCHES)
    var launches: List<LaunchRepositoryDbEntity>
) {

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    constructor() : this(0, listOf())

    companion object {
        const val ID = "_id"
        const val LAUNCH_MODEL_ENTITIES_TABLE = "launch_model_entities_table"
        const val TIME_STAMP = "timeStamp"
        const val LAUNCHES = "launches"
    }
}



