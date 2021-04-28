package com.challenge.data_db

import android.content.Context
import androidx.room.Room

class SpaceXdbManager(context: Context) {
    private val DATABASE_NAME = "room_db"

    val database: SpaceXDb

    init {
        database = createDatabase(context)
    }

    companion object {

        lateinit var INSTANCE: SpaceXdbManager
            // private setter for the instance
            private set

        fun init(context: Context) {
            INSTANCE = SpaceXdbManager(context)
        }
    }

    private fun createDatabase(context: Context): SpaceXDb {
        return if (BuildConfig.DEBUG) {
            Room.databaseBuilder(context, SpaceXDb::class.java, DATABASE_NAME)
                .allowMainThreadQueries()

                .build()
        } else {
            return Room.databaseBuilder(context, SpaceXDb::class.java, DATABASE_NAME)
                .build()
        }
    }
}