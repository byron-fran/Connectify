package com.example.connectify.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(

    @PrimaryKey
    val id : String = "",

    @ColumnInfo(name = "name")
    val name : String = "",

    @ColumnInfo(name = "phone_number")
    val phoneNumber : Int = 0,

    @ColumnInfo(name = "email")
    val email : String? = "",

    @ColumnInfo(name = "image_url")
    val imageUrl : String? = ""
)