package com.example.connectify.domain.models

import androidx.compose.ui.graphics.Color
import java.util.UUID
import kotlin.random.Random

data class Contact(
    val id : String = UUID.randomUUID().toString(),
    val name : String = "",
    val phoneNumber : Int = 0,
    val email : String? = "",
    val imageUrl : String? = "",
    val description : String? = "",
    val isFavorite : Boolean = false,
    val colorDefault: Color = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )
)
