package com.example.connectify.domain.models

import java.util.UUID

data class Contact(
    val id : String = UUID.randomUUID().toString(),
    val name : String = "",
    val phoneNumber : Int = 0,
    val email : String? = "",
    val imageUrl : String? = "",
    val description : String? = ""

)
