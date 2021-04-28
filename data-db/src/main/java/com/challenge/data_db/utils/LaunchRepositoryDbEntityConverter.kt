package com.challenge.data_db.utils

import androidx.room.TypeConverter
import com.challenge.data_db.model.LaunchRepositoryDbEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class LaunchRepositoryDbEntityConverter {

    private var gson = Gson()

    @TypeConverter
    fun fromGson(data: String?): List<LaunchRepositoryDbEntity> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<LaunchRepositoryDbEntity>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toGson(someObjects: List<LaunchRepositoryDbEntity>): String {
        return gson.toJson(someObjects)
    }
}