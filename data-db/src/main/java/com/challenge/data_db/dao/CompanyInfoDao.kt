package com.challenge.data_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.data_db.entities.CompanyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyInfoDao {

    @Query("SELECT * FROM  ${CompanyInfoEntity.COMPANY_INFO_TABLE}")
    fun getCachedResponse(): Flow<CompanyInfoEntity?>

    @Query("DELETE FROM ${CompanyInfoEntity.COMPANY_INFO_TABLE}")
    suspend fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedResponse(companyInfoEntity: CompanyInfoEntity)
}