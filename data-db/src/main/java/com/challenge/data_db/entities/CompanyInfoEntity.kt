package com.challenge.data_db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.challenge.data_db.entities.CompanyInfoEntity.Companion.COMPANY_INFO_TABLE

@Entity(tableName = COMPANY_INFO_TABLE)
data class CompanyInfoEntity(
    @ColumnInfo(name = TIME_STAMP)
    var timeStamp: Long = 0,

    @ColumnInfo(name = VALUTATION)
    var valuation: Long = 0,

    @ColumnInfo(name = LAUNCH_SITES)
    var launchSites: Int = 0,

    @ColumnInfo(name = EMPLOYEES)
    var employees: String = "",

    @ColumnInfo(name = FOUNDED)
    var founded: String = "",

    @ColumnInfo(name = FOUNDER)
    var founder: String = "",

    @ColumnInfo(name = NAME)
    var name: String = ""
) {

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    @Ignore
    constructor() : this(0, 0, 0, "", "", "")

    companion object {
        const val ID = "_id"
        const val COMPANY_INFO_TABLE = "company_info_table"
        const val TIME_STAMP = "timeStamp"
        const val VALUTATION = "valutation"
        const val LAUNCH_SITES = "launchSites"
        const val EMPLOYEES = "employees"
        const val FOUNDED = "founded"
        const val FOUNDER = "founder"
        const val NAME = "name"
    }
}