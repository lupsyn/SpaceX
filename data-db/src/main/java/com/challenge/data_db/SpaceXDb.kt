package com.challenge.data_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.data_db.dao.CompanyInfoDao
import com.challenge.data_db.dao.LaunchModelEntitiesDao
import com.challenge.data_db.entities.CompanyInfoEntity
import com.challenge.data_db.entities.LaunchModelEntities

@Database(
    entities = [
        CompanyInfoEntity::class,
        LaunchModelEntities::class
    ],
    version = VERSION
)
abstract class SpaceXDb : RoomDatabase() {
    abstract fun companyInfoDao(): CompanyInfoDao
    abstract fun launchModelEntitiesDao(): LaunchModelEntitiesDao
}