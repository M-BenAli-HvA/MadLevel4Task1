package com.example.madlevel4task1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
class Product(


    @ColumnInfo()
    var name: String,

    @ColumnInfo()
    var amount: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)