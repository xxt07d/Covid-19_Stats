package com.zsami.covid_19stats.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily")
data class Daily (
    @PrimaryKey(autoGenerate = true) var dayId: Long?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "infections") var infections: Long,
    @ColumnInfo(name = "deaths") var deaths: Long,
    @ColumnInfo(name = "recoveries") var recoveries: Long,
    @ColumnInfo(name = "active") var actives: Long
)