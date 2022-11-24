package com.yusril.storyapp.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: String,

    @field:ColumnInfo(name = "prevPage")
    val prevKey: Int?,

    @field:ColumnInfo(name = "nextPage")
    val nextKey: Int?
)
